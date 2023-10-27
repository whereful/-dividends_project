package com.zerobase.dividends.exception.impl;

import com.zerobase.dividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NoCompanyException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "등록되어 있지 않은 회사입니다.";
    }
}
