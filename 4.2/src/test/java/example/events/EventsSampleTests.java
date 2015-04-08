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
package example.events;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.events.EventsSample.EventProducer;

/**
 * Simple example class to showcase Spring 4.2 transactional events.
 * 
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Sleeping God (Live at Alte Feuerwache Mannheim)
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventsSample.class)
public class EventsSampleTests {

	@Autowired EventProducer producer;

	@Test(expected = RuntimeException.class)
	public void publishEvents() {

		log.info("");
		log.info("First event");
		log.info("");

		producer.publishEvent();

		log.info("");
		log.info("Second event");
		log.info("");

		producer.publishEventWithException();
	}
}
