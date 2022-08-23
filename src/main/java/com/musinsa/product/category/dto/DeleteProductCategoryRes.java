package com.musinsa.product.category.dto;

import com.musinsa.product.category.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductCategoryRes {

    private Long id;

    public static DeleteProductCategoryRes from(ProductCategory productCategory) {
        return new DeleteProductCategoryRes(productCategory.getId());
    }
}
