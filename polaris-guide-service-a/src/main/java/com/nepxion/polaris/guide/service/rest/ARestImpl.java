package com.nepxion.polaris.guide.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.polaris.guide.service.core.CoreImpl;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "polaris-service-a")
public class ARestImpl extends CoreImpl {
    private static final Logger LOG = LoggerFactory.getLogger(ARestImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/rest")
    public String rest(@RequestBody String value) {
        value = getPluginInfo(value);
        value = restTemplate.postForEntity("http://polaris-service-b/rest", value, String.class).getBody();

        LOG.info("调用路径：{}", value);

        return value;
    }
}