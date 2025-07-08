package com.ecommerce.service;

import java.util.Set;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.ecommerce.model.Order;
import com.ecommerce.model.PaymentOrder;
import com.ecommerce.model.User;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    Boolean ProceedPaymentOrder (PaymentOrder paymentOrder,
                                 String paymentId,
                                 String paymentLinkId) throws RazorpayException;
    PaymentLink createRazorpayPaymentLink(User user, Long amount,
                                          Long orderId) throws RazorpayException;
    String createStripePaymentLink(User user,
                                   Long amount, Long orderId) throws StripeException;
}
