package com.ecommerce.response;

public class SignupRequest {
	
	private String email;
	
	private String fullName;
	
	private String otp;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "SignupRequest [email=" + email + ", fullName=" + fullName + ", otp=" + otp + "]";
	}
	
	
	
}
