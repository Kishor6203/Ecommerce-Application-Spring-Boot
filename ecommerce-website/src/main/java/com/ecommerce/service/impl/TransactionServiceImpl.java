package com.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Order;
import com.ecommerce.model.Seller;
import com.ecommerce.model.Transaction;
import com.ecommerce.repository.SellerRepository;
import com.ecommerce.repository.TransactionRepository;
import com.ecommerce.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
