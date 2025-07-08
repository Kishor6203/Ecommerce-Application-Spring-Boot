package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.AccountStatus;
import com.ecommerce.model.Seller;
import com.ecommerce.model.SellerReport;
import com.ecommerce.model.VerificationCode;
import com.ecommerce.repository.VerificationCodeRepository;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.response.AuthResponse;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.EmailService;
import com.ecommerce.service.SellerReportService;
import com.ecommerce.service.SellerService;
import com.ecommerce.utils.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final SellerReportService sellerReportService;
    
    
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req
    ) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();
        
        req.setEmail("seller_"+email);
        System.out.println(otp+" - "+email);
        AuthResponse authResponse=authService.signing(req);

        return ResponseEntity.ok(authResponse); 
       
    }
    
    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySeller(@PathVariable String otp) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp...");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Seller> createSeller(
            @RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Ecommerce Email Verification Code";
        String text = "Welcome to Ecommerce, verify your account using this link ";
        String frontend_url = "http://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws Exception {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    
    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(
            @RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(
            @RequestParam(required = false) AccountStatus status) throws Exception {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }
    
    @PatchMapping
    public ResponseEntity<Seller> updateSeller(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Seller seller) throws Exception {

        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}
