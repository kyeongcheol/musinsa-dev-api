package com.musinsa.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 등록 DTO
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductCategoryReq {
	
    private String name;
    private Long rootId;

}
