package com.musinsa.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;

@Getter
public class AddProductCategoryRequest {
	
	@ApiModelProperty(example = "이름")
    private String name;
	
	@ApiModelProperty(example = "상위 상품 카테고리 ID")
    private Long rootId;

}
