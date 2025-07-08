package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.domain.AccountStatus;
import com.ecommerce.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{
	
	Seller findByEmail(String email);
	List<Seller> findByAccountStatus(AccountStatus status);

}
