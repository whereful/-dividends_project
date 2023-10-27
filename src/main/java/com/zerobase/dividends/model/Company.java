package com.zerobase.dividends.model;

import lombok.Builder;
import lombok.Data;


/**
 * data transfer object(dto)
 */
@Data
@Builder
public class Company {
    private String ticker;
    private String name;
}
