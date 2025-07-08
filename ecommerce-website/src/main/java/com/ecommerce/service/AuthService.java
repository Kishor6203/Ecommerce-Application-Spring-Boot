package com.ecommerce.service;

import com.ecommerce.domain.USER_ROLE;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.response.AuthResponse;
import com.ecommerce.response.SignupRequest;

public interface AuthService {
	
	void sentLoginOtp(String email, USER_ROLE role) throws Exception;
	String createUser(SignupRequest req) throws Exception;
	AuthResponse signing(LoginRequest req) throws Exception;
	

}
