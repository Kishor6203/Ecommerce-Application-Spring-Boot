package com.ecommerce.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailSender;

	public void sendVerificationOtpEmail(String userEmail,
	                                     String otp, String subject,
	                                     String text) throws MessagingException {
	    try {
	        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(
	                mimeMessage, "utf-8");

	        mimeMessageHelper.setSubject(subject);
	        mimeMessageHelper.setText(text);
	        mimeMessageHelper.setTo(userEmail);
	        javaMailSender.send(mimeMessage);
	    } catch (MailException e){
	    	System.out.println("error--"+e);
	        throw new MailSendException("failed to send email");
	    }
	}

}
