package com.musinsa.product.category.dto;

import com.musinsa.product.category.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCategoryRes {

    private Long id;
    private String name;

    public static UpdateProductCategoryRes from(ProductCategory productCategory) {
        return new UpdateProductCategoryRes(
        		productCategory.getId(),
        		productCategory.getName());
    }
}
