package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.HomeCategory;

public interface HomeCategoryService {
	
    HomeCategory createHomeCategory(HomeCategory homeCategory);
    
    List<HomeCategory> createCategories(List<HomeCategory> homeCategories);
    
    HomeCategory updateHomeCategory(HomeCategory homeCategory,Long id) throws Exception;
    
    List<HomeCategory> getAllHomeCategories();
    
}
