package com.example.contractclientdemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties =
        { "test.url=http://localhost:${stubrunner.runningstubs.test-webapp.port:8082}" }
)
class DemoFeignClientTest {

    @Autowired
    DemoFeignClient feignClient;

    @Value("${stubrunner.runningstubs.test-webapp.port}")
    String webappPort;

    @Test
    void callPosturl() {
        assertThat( webappPort ).isNotEqualTo( "8082" );

        feignClient.callUrl();
    }
}