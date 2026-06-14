package com.ccscripts.panopticonfive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tools.jackson.databind.DeserializationFeature;

@SpringBootApplication
public class PanopticonApp {
    static void main(String[] args) {
        SpringApplication.run(PanopticonApp.class, args);
    }
}
