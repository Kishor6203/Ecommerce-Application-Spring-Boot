package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.domain.USER_ROLE;
import com.ecommerce.request.LoginOtpRequest;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.response.AuthResponse;
import com.ecommerce.response.SignupRequest;
import com.ecommerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(
    		@RequestBody LoginOtpRequest req) throws Exception {
            
    	authService.sentLoginOtp(req.getEmail(), req.getRole());

        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");

        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(
                                        @RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }
}
