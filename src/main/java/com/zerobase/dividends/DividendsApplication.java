package com.zerobase.dividends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 컨트롤러는 DividendsApplication이 있는 패키지와 계층이 같거나 밑에 있어야 함
 */
@SpringBootApplication
@EnableScheduling // 스케줄러 사용하기 위해 작성되어야 함
@EnableCaching // 캐시 기능 사용하기 위해 작성되어야 함
public class DividendsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DividendsApplication.class, args);
    }
}