package com.ccscripts.panopticonfive.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HiscoreClientConfig {
    @Bean
    public RestClient osrsRestClient() {
        return RestClient.builder()
            .baseUrl("https://secure.runescape.com")
            .build();
    }
}