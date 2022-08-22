package com.musinsa.product.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.product.category.dto.AddProductCategoryRequest;
import com.musinsa.product.category.service.ProductCategoryService;

import org.springframework.http.HttpStatus;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 Controller
 * @version 1.0
 */

@Slf4j
@RequestMapping("/api/musinsa/category")
@RequiredArgsConstructor
@RestController
public class ProductCategoryController {
	
	@Autowired
	private final ProductCategoryService categoryService;
	
	/**
	 * @apiNote 상품 카테고리 전체 조회 API
	 * @return 전체 상품 카테고리 반환
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> findAllProductCategoryList() throws Exception {
		return ResponseEntity.ok(categoryService.findAllProductCategoryList());
	}
	
	/**
	 * @apiNote 상품 카테고리 특정 조회 API
	 * @param id
	 * @return 특정 상품 카테고리 반환
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> findOneProductCategoryList(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(categoryService.findOneProductCategoryList(id));
	}
	
	/**
	 * @apiNote 상품 카테고리 등록 API
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> AddProductCategory(@RequestBody AddProductCategoryRequest request) throws Exception {
		return ResponseEntity.ok(categoryService.addProdcutCategory(request));
	}

}

