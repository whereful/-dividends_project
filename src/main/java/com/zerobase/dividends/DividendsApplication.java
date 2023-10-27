package com.zerobase.dividends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 컨트롤러는 DividendsApplication이 있는 패키지와 계층이 같거나 밑에 있어야 함
 */
@SpringBootApplication
public class DividendsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DividendsApplication.class, args);
    }
}