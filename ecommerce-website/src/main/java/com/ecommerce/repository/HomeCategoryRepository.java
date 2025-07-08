package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.HomeCategory;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {

}
