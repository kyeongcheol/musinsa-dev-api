package com.musinsa.product.category.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CategoryRuntimeException{

    public NotFoundException() {
        super("상품 카테고리가 존재하지 않습니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
