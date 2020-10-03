package com.nepxion.polaris.guide.zuul.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PolarisZuulRouteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String token = context.getRequest().getHeader("token");
        if (StringUtils.equals(token, "abc")) {
            context.addZuulRequestHeader("userid", "zhangsan");
        } else {
            context.addZuulRequestHeader("userid", "lisi");
        }

        return null;
    }
}