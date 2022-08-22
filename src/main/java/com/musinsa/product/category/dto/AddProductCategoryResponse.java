package com.musinsa.product.category.dto;

import com.musinsa.product.category.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductCategoryResponse {

    private Long id;
    private String name;


    public static AddProductCategoryResponse from(ProductCategory productCategory) {
        return new AddProductCategoryResponse(
    		productCategory.getId(),
    		productCategory.getName());
    }

}
