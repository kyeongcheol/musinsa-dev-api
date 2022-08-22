package com.musinsa.product.category.service;

import org.springframework.stereotype.Service;

import com.musinsa.product.category.dto.AddProductCategoryRequest;
import com.musinsa.product.category.dto.AddProductCategoryResponse;
import com.musinsa.product.category.dto.AllProductCategoryListRes;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 서비스
 * @version 1.0
 *
 */
@Service
public interface ProductCategoryService {

	AllProductCategoryListRes findAllProductCategoryList() throws Exception;
	AllProductCategoryListRes findOneProductCategoryList(Long id) throws Exception;
	AddProductCategoryResponse addProdcutCategory(AddProductCategoryRequest request) throws Exception;
}
