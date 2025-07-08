package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.Order;
import com.ecommerce.model.Seller;
import com.ecommerce.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
