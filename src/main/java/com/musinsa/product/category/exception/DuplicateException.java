package com.musinsa.product.category.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends CategoryRuntimeException{

    public AlreadyExistException() {
        super("동일한 카테고리명이 존재합니다.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
