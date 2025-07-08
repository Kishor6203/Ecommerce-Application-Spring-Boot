package com.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.Wishlist;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.service.WishlistService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }
    
    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());
        if (wishlist == null) {
            wishlist = createWishlist(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = getWishlistByUserId(user);

        if (wishlist.getProduct().contains(product)) {
            wishlist.getProduct().remove(product);
        } else {
            wishlist.getProduct().add(product);
        }

        return wishlistRepository.save(wishlist);
    }
}

