package com.nepxion.polaris.guide.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.nepxion.discovery.plugin.test.automation.annotation.DTestConfig;

public class PolarisTestCases {
    private static final Logger LOG = LoggerFactory.getLogger(PolarisTestCases.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-default.xml", resetPath = "gray-default.xml")
    public void testNoGray(String group, String serviceId, String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray(String group, String serviceId, String testUrl) {
        testVersionStrategyGray(testUrl);
    }

    private void testVersionStrategyGray(String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=1.0]");
            int lastIndex = result.lastIndexOf("[V=1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-region.xml", resetPath = "gray-default.xml")
    public void testRegionStrategyGray(String group, String serviceId, String testUrl) {
        testRegionStrategyGray(testUrl);
    }

    private void testRegionStrategyGray(String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[R=dev]");
            int lastIndex = result.lastIndexOf("[R=dev]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }

    @DTestConfig(group = "DEFAULT_GROUP", serviceId = "sentinel-authority-polaris-service-b", executePath = "sentinel-authority-2.json", resetPath = "sentinel-authority-1.json")
    public void testSentinelAuthority1(String testUrl) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }

    @DTestConfig(group = "DEFAULT_GROUP", serviceId = "sentinel-authority-polaris-service-b", executePath = "sentinel-default.json", resetPath = "sentinel-authority-1.json")
    public void testSentinelAuthority2(String testUrl) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }
}