package com.nepxion.polaris.guide.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nepxion.discovery.plugin.test.automation.application.TestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, PolarisTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PolarisTest {
    private static final Logger LOG = LoggerFactory.getLogger(PolarisTest.class);

    @Value("${gateway.group}")
    private String gatewayGroup;

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Value("${gateway.test.url}")
    private String gatewayTestUrl;

    @Value("${zuul.group}")
    private String zuulGroup;

    @Value("${zuul.service.id}")
    private String zuulServiceId;

    @Value("${zuul.test.url}")
    private String zuulTestUrl;

    @Autowired
    private PolarisTestCases polarisTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testANoGray() throws Exception {
        polarisTestCases.testNoGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        polarisTestCases.testNoGray(zuulGroup, zuulServiceId, zuulTestUrl);
        polarisTestCases.testNoGray(gatewayGroup, gatewayGroup, gatewayTestUrl);
        polarisTestCases.testNoGray(zuulGroup, zuulGroup, zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray() throws Exception {
        polarisTestCases.testVersionStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        polarisTestCases.testVersionStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testRegionStrategyGray() throws Exception {
        polarisTestCases.testRegionStrategyGray(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        polarisTestCases.testRegionStrategyGray(zuulGroup, zuulServiceId, zuulTestUrl);
    }

    @Test
    public void testSentinelAuthority1() throws Exception {
        polarisTestCases.testSentinelAuthority1(gatewayTestUrl);
        polarisTestCases.testSentinelAuthority1(zuulTestUrl);
    }

    @Test
    public void testSentinelAuthority2() throws Exception {
        polarisTestCases.testSentinelAuthority2(gatewayTestUrl);
        polarisTestCases.testSentinelAuthority2(zuulTestUrl);
    }
}