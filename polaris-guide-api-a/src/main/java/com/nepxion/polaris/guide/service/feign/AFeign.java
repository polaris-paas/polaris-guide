package com.nepxion.polaris.guide.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "polaris-service-a")
public interface AFeign {
    @PostMapping(path = "/invoke")
    String invoke(@RequestBody String value);
}