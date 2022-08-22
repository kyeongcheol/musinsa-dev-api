package com.musinsa.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author kcyang
 * @apiNote 상품 카테고리 등록 DTO
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductCategoryReq {
	
	@ApiModelProperty(example = "이름")
    private String name;
	
	@ApiModelProperty(example = "상위 상품 카테고리 ID")
    private Long rootId;

}
