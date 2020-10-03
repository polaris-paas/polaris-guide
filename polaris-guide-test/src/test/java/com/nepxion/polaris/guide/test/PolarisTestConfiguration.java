package com.nepxion.polaris.guide.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolarisTestConfiguration {
    @Bean
    public PolarisTestCases polarisTestCases() {
        return new PolarisTestCases();
    }
}