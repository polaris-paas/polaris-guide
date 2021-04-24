package com.nepxion.polaris.guide.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nepxion.discovery.plugin.test.automation.application.TestApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { TestApplication.class, PolarisTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class PolarisTest {
    private static final Logger LOG = LoggerFactory.getLogger(PolarisTest.class);

    @Value("${gateway.group}")
    private String gatewayGroup;

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Autowired
    private PolarisTestCases polarisTestCases;

    private static long startTime;

    @BeforeAll
    public static void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    @AfterAll
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testANoGray() throws Exception {
        polarisTestCases.testNoGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        polarisTestCases.testNoGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
    }

    @Test
    public void testVersionStrategyGray() throws Exception {
        polarisTestCases.testVersionStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
    }

    @Test
    public void testRegionStrategyGray() throws Exception {
        polarisTestCases.testRegionStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
    }

    @Test
    public void testSentinelAuthority1() throws Exception {
        polarisTestCases.testSentinelAuthority1(gatewayTestUrl);
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        polarisTestCases.testSentinelAuthority2(gatewayTestUrl);
    }
}