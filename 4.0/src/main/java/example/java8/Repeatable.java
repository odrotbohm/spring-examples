/*
 * Copyright 2013 the original author or authors.
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
package example.java8;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

/**
 * @author Oliver Gierke
 */
public class Repeatable {

	@Scheduled(cron = "0 0 12 * * ?")
	@Scheduled(cron = "0 0 18 * * ?")
	public void performTempFileCleanup() {}

	@Schedules({ @Scheduled(cron = "0 0 12 * * ?"), @Scheduled(cron = "0 0 18 * * ?") })
	public void performTempFileCleanupJdk7() {}
}
