package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Order;
import com.ecommerce.model.PaymentOrder;
import com.ecommerce.model.Seller;
import com.ecommerce.model.SellerReport;
import com.ecommerce.model.User;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.response.PaymentLinkResponse;
import com.ecommerce.service.PaymentService;
import com.ecommerce.service.SellerReportService;
import com.ecommerce.service.SellerService;
import com.ecommerce.service.TransactionService;
import com.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        
        PaymentLinkResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService
                .getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(
                paymentOrder,
                paymentId,
                paymentLinkId
        );
        if (paymentSuccess) {
            for (Order order : paymentOrder.getOrder()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings(report.getTotalEarnings() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }
        }
        
        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
