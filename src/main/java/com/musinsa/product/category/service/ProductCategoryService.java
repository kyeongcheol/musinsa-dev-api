package com.musinsa.product.category.service;

import org.springframework.stereotype.Service;

import com.musinsa.product.category.dto.AddProductCategoryReq;
import com.musinsa.product.category.dto.AddProductCategoryRes;
import com.musinsa.product.category.dto.AllProductCategoryListRes;
import com.musinsa.product.category.dto.UpdateProductCategoryRes;

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
	AddProductCategoryRes addProdcutCategory(AddProductCategoryReq request) throws Exception;
	UpdateProductCategoryRes updateProdcutCategory(Long id, String name);
}
