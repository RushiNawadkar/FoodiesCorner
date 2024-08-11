package com.app;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ConfigurationProperties("paytm.payment")
public class PaytmDetails {

	private String merchantId;
	
    private String merchantKey;
    
    private String website;
    
    private String channelId;
    
    private String industryTypeId;
    
    private String paytmUrl;
    
    private Map<String,String>details;
}
