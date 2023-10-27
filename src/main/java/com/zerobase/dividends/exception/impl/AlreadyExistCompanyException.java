package com.zerobase.dividends.exception.impl;

import com.zerobase.dividends.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class AlreadyExistCompanyException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 저장되어 있는 회사입니다.";
    }
}
