package com.zerobase.dividends.exception.impl;

import com.zerobase.dividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NotFoundCompanyException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 회수입니다.";
    }
}
