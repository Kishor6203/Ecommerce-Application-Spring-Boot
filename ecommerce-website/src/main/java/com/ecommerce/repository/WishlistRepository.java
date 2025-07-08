package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    Wishlist findByUserId(Long userId);
}
