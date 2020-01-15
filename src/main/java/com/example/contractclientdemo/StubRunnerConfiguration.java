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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

/**
 * Configuration that initializes a {@link BatchStubRunner} that runs
 * {@link org.springframework.cloud.contract.stubrunner.StubRunner} instance for
 * each stub.
 *
 * @author Marcin Grzejszczak
 */
@Configuration(proxyBeanMethods = false)
public class StubRunnerConfiguration {

	static final String STUBRUNNER_PREFIX = "stubrunner.runningstubs";

	@Autowired
	private ConfigurableEnvironment environment;

	/**
	 * Bean that initializes stub runners, runs them and on shutdown closes them.
	 * Upon its instantiation JAR with stubs is downloaded and unpacked to a
	 * temporary folder and WireMock server are started for each of those stubs
	 * 
	 * @param beanFactory bean factory
	 * @return the batch stub runner bean
	 */
	@Bean
	public BatchStubRunner batchStubRunner(BeanFactory beanFactory) {
		BatchStubRunner batchStubRunner = new BatchStubRunner();
		registerPort(batchStubRunner);
		return batchStubRunner;
	}

	@Bean
	public BeanPostProcessor batchStubRunnerBeanPostProcessor(BatchStubRunner runner) {
		return new BeanPostProcessor() {
		};
	}

	private void registerPort(BatchStubRunner batchStubRunner) {
		MutablePropertySources propertySources = this.environment.getPropertySources();
		if (!propertySources.contains(STUBRUNNER_PREFIX)) {
			propertySources.addFirst(new MapPropertySource(STUBRUNNER_PREFIX, new HashMap<>()));
		}
		Map<String, Object> source = ((MapPropertySource) propertySources.get(STUBRUNNER_PREFIX)).getSource();
		source.put(STUBRUNNER_PREFIX + ".test-webapp.port", batchStubRunner.getPort());
	}

}
