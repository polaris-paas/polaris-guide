package com.nepxion.polaris.guide.gateway;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.nepxion.polaris.framework.gateway.annotation.EnablePolarisGateway;
import com.nepxion.polaris.guide.gateway.impl.PolarisGatewayRouteFilter;

@EnablePolarisGateway
public class PolarisGateway {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PolarisGateway.class).run(args);
    }

    @Bean
    public PolarisGatewayRouteFilter polarisGatewayRouteFilter() {
        return new PolarisGatewayRouteFilter();
    }
}