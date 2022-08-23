package com.musinsa.product.category.dto;

import lombok.Getter;
import java.util.List;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 전체 조회 DTO
 * @version 1.0
 */
@Getter
public class AllProductCategoryListRes<T> {

	private List<T> allProductCategoryRes;

	public AllProductCategoryListRes(List<T> categoryList) {
        this.allProductCategoryRes = categoryList;
    }
}
