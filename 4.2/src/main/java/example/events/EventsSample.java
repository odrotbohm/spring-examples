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

import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Simple example class to showcase Spring 4.2 transactional events.
 * 
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Prodigy Composers (Live at Alte Feuerwache Mannheim)
 */
@Slf4j
@SpringBootApplication
class EventsSample {

	/**
	 * The event.
	 */
	@Value
	static class Event {
		private final Object payload = UUID.randomUUID();
	}

	/**
	 * The producer.
	 */
	@Component
	@RequiredArgsConstructor(onConstructor = @__(@Autowired))
	static class EventProducer {

		private final @NonNull ApplicationEventPublisher publisher;

		@Transactional
		public void publishEvent() {
			publisher.publishEvent(new Event());
		}

		@Transactional(rollbackFor = RuntimeException.class)
		public void publishEventWithException() {

			publisher.publishEvent(new Event());
			throw new RuntimeException();
		}
	}

	/**
	 * The listeners.
	 *
	 * @author Oliver Gierke
	 */
	@Component
	static class EventListeners {

		@EventListener
		public void onMulticast(Event event) {
			log.info("Received event on multicast: {}", event.getPayload());
		}

		@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
		public void beforeCommit(Event event) {
			log.info("Received event before commit: {}", event.getPayload());
		}

		@TransactionalEventListener
		public void afterCommit(Event event) {
			log.info("Received event after commit: {}", event.getPayload());
		}

		@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
		public void afterRollback(Event event) {
			log.info("Received event afterRollback: {}", event.getPayload());
		}

		@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
		public void afterCompletion(Event event) {
			log.info("Received event after completion: {}", event.getPayload());
		}
	}
}
