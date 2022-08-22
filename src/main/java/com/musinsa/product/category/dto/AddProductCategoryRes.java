package com.musinsa.product.category.dto;

import com.musinsa.product.category.entity.ProductCategory;

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
public class AddProductCategoryRes {

    private Long id;
    private String name;


    public static AddProductCategoryRes from(ProductCategory productCategory) {
        return new AddProductCategoryRes(
    		productCategory.getId(),
    		productCategory.getName());
    }

}
