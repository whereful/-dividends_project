package com.zerobase.dividends.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

        // 코어 개수 구하기
        int n = Runtime.getRuntime().availableProcessors();

        // 스레드 개수 설정
        threadPool.setPoolSize(n + 1);

        threadPool.initialize();

        // 스케줄러에서 해당 스레드 풀을 사용하게 됨
        taskRegistrar.setTaskScheduler(threadPool);
    }
}
