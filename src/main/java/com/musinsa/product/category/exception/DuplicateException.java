package com.musinsa.product.category.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends CategoryRuntimeException{

    public DuplicateException() {
        super("등록된 상품 카테고리가 존재합니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
