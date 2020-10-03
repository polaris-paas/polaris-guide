package com.nepxion.polaris.guide.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.polaris.guide.service.core.CoreImpl;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "polaris-service-b")
public class BRestImpl extends CoreImpl {
    private static final Logger LOG = LoggerFactory.getLogger(BRestImpl.class);

    @PostMapping(path = "/rest")
    public String rest(@RequestBody String value) {
        value = getPluginInfo(value);

        LOG.info("调用路径：{}", value);

        return value;
    }
}