package com.example.contractclientdemo;

// @FeignClient(name = "demo", url = "${test.url}")
public interface DemoFeignClient {

        // @GetMapping(value = "/ping")
        void callUrl();
}
