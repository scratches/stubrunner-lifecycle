/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.contractclientdemo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

class ClientFactoryBean implements FactoryBean<DemoFeignClient>, EnvironmentAware {

    private String url;
    private Environment environment;

    @Override
    public DemoFeignClient getObject() throws Exception {
        url = resolve(url);
        System.err.println("Create URL: " + url);
        return () -> {
            System.err.println("Use URL: " + url);
            if (url.contains("8082") || !url.contains("9000")) {
                throw new IllegalStateException("Wrong URL: " + url);
            }
        };
    }

    private String resolve(String value) {
        return environment.resolvePlaceholders(value);
    }

    @Override
    public Class<?> getObjectType() {
        return DemoFeignClient.class;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}