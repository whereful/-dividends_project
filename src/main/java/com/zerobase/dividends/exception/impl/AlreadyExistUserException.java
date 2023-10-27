package com.zerobase.dividends.exception.impl;

import com.zerobase.dividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class AlreadyExistUserException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 등록된 사용자입니다.";
    }
}
