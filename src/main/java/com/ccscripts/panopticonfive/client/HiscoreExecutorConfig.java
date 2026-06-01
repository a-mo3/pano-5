package com.ccscripts.panopticonfive.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class HiscoreExecutorConfig {
    @Bean(destroyMethod = "shutdown")
    public ExecutorService hiscoreExecutorService() {
        return Executors.newFixedThreadPool(28);
    }
}