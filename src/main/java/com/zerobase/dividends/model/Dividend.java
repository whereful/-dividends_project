package com.zerobase.dividends.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @NoArgsConstructor
 * @AllArgsConstructor 두 문구가 있어야 redis와 연동 시 오류가 발생하지 않음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dividend {

    // LocalDateTime은 redis에 전달하기 위해 serializable 방식을 정해야 한다
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private String dividend;
}
