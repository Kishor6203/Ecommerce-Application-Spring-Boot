package com.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.exceptions.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Seller;
import com.ecommerce.request.CreateProductRequest;

public interface ProductService {

    public Product createProduct(CreateProductRequest req, Seller seller);

    public void deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product product) throws ProductException;

    Product findProductById(Long productId) throws ProductException;

    List<Product> searchProducts(String query);

    public Page<Product> getAllProducts(
    		
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
            
    );

    List<Product> getProductBySellerId(Long sellerId);
}