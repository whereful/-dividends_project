package com.zerobase.dividends.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * data transfer object(dto)
 */

/**
 * @NoArgsConstructor
 * @AllArgsConstructor
 *
 * 두 문구가 있어야 redis와 연동 시 오류가 발생하지 않음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private String ticker;
    private String name;
}
