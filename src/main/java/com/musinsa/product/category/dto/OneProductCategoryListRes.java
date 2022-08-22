package com.musinsa.product.category.dto;

import com.musinsa.product.category.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author kcyang
 * @apiNote 특정 상품 카테고리 목록 DTO
 * @version 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OneProductCategoryListRes {

	private Long id;
	private String name;

	public static OneProductCategoryListRes from(ProductCategory productCategory) {

		return new OneProductCategoryListRes(
			productCategory.getId(),
			productCategory.getName()		
		);
				
	}
}
