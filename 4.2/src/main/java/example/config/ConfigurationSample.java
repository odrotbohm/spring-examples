/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sample showing Spring 4.2 supprt for interface based JavaConfig which allows
 * 
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Relations in the unseen (Live at Alte Feuerwache Mannheim)
 */
class ConfigurationSample {

	@Configuration
	interface ResourceConfiguration {

		default @Bean SomeResource resource() {
			return new SomeResource();
		}
	}

	@Configuration
	interface SomeOtherResourceConfiguration {

		default @Bean SomeOtherResource otherResource() {
			return new SomeOtherResource();
		}
	}

	@Configuration
	interface ApplicationConfiguration extends ResourceConfiguration, SomeOtherResourceConfiguration {

		default @Bean Client client() {
			return new Client(resource(), otherResource());
		}
	}

	static class SomeResource {}

	static class SomeOtherResource {}

	@RequiredArgsConstructor
	static class Client {

		private final @NonNull SomeResource resource;
		private final @NonNull SomeOtherResource otherResource;
	}
}
