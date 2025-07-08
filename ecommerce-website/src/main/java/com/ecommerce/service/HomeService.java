package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.Home;
import com.ecommerce.model.HomeCategory;

public interface HomeService {
	
	public Home createHomePageData(List<HomeCategory> allCategories);

}
