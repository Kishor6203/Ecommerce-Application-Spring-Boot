package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long>{

}
