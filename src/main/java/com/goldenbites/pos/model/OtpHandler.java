package com.goldenbites.pos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "OtpHandler")
public class OtpHandler {

	@Id
	private String otpHandlerId;

	@Field(name = "user_email")
	private String userEmail;

	@Field(name = "otp")
	private int otp;

	public String getOtpHandlerId() {
		return otpHandlerId;
	}

	public void setOtpHandlerId(String otpHandlerId) {
		this.otpHandlerId = otpHandlerId;
	}

	public String getUserName() {
		return userEmail;
	}

	public void setUserName(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

}
