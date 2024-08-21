package com.app.custom_exceptions;

@SuppressWarnings("serial")
public class ReaserchNotFound extends RuntimeException {
	
	public ReaserchNotFound(String msg) {
		super(msg);
	}

}
