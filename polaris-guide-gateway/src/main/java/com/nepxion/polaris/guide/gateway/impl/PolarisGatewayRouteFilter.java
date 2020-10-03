package com.nepxion.polaris.guide.gateway.impl;

import reactor.core.publisher.Mono;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyFilterResolver;

public class PolarisGatewayRouteFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();

        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (StringUtils.equals(token, "abc")) {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, "userid", "zhangsan", false);
        } else {
            GatewayStrategyFilterResolver.setHeader(requestBuilder, "userid", "lisi", false);
        }

        ServerHttpRequest newRequest = requestBuilder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }
}