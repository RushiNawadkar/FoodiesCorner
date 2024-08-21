package com.app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.AppConfig;
import com.app.entities.Order;
import com.app.entities.User;
import com.app.repository.OrderRepository;
import com.paytm.pg.merchant.PaytmChecksum;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderRepository orderRepository;

    private final Random random = new Random();

    @PostMapping("/start")
    public Map<String, Object> startPayment(@RequestBody Map<String, Object> data) {
        Long orderId;
        String amount;

        try {
            orderId = Long.parseLong(data.get("orderId").toString());
            amount = data.get("amount").toString();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input data: orderId and amount must be valid numbers");
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        Order order = optionalOrder.get();
        User user = order.getUser();

        JSONObject body = new JSONObject();
        body.put("requestType", "Payment");
        body.put("mid", AppConfig.MID);
//        System.out.println(AppConfig.MID);
        body.put("websiteName", AppConfig.WEBSITE);
        body.put("orderId", order.getOrderId());
        body.put("callbackUrl", "https://localhost:8080/payment-success");

        JSONObject txnAmount = new JSONObject();
        txnAmount.put("value", String.format("%.2f", Double.parseDouble(amount)));
        txnAmount.put("currency", "INR");

        JSONObject userInfo = new JSONObject();
        userInfo.put("custId", user.getUserId().toString());
        body.put("txnAmount", txnAmount);
        body.put("userInfo", userInfo);

        JSONObject paytmParams = new JSONObject();
        paytmParams.put("body", body);

        String checksum;
        try {
            checksum = PaytmChecksum.generateSignature(body.toString(), AppConfig.MKEY);
            JSONObject head = new JSONObject();
            head.put("signature", checksum);
            paytmParams.put("head", head);
        } catch (Exception e) {
            throw new RuntimeException("Error generating checksum: " + e.getMessage());
        }

        String post_data = paytmParams.toString();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction")
                .queryParam("mid", AppConfig.MID)
                .queryParam("orderId", order.getOrderId());

        URI url;
        try {
            url = uriBuilder.build().toUri();
        } catch (Exception e) {
            throw new RuntimeException("Error constructing URL: " + e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(post_data, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Payment initiation failed: " + e.getMessage());
        }

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) {
            responseBody = new HashMap<>();
        }

        // Log the response for debugging
        System.out.println("Paytm Response: " + responseBody);

        responseBody.put("orderId", order.getOrderId());
        responseBody.put("userId", user.getUserId());
        responseBody.put("amount", amount);

        return responseBody;
    }

}
