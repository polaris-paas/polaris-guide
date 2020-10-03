package com.nepxion.polaris.guide.zuul;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.nepxion.polaris.framework.zuul.annotation.EnablePolarisZuul;
import com.nepxion.polaris.guide.zuul.impl.PolarisZuulRouteFilter;

@EnablePolarisZuul
public class PolarisZuul {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PolarisZuul.class).run(args);
    }

    @Bean
    public PolarisZuulRouteFilter polarisZuulRouteFilter() {
        return new PolarisZuulRouteFilter();
    }
}