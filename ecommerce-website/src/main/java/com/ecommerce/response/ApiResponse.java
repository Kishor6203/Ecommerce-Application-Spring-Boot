package com.ecommerce.response;

import lombok.Data;

@Data
public class ApiResponse {
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiResponse [message=" + message + "]";
	}
	
	

}
