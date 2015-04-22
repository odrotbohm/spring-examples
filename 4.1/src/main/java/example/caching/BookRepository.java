/*
 * Copyright 2014-2015 the original author or authors.
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
package example.caching;

/**
 * @author Stephane Nicoll
 */
public interface BookRepository {

	Book findBook(Long id);

	// This is essentially a showcase to demonstrate how a cache
	// can be resolved at runtime. storeName is the name of the
	// cache to use
	Book findBook(Long id, String storeName);

	Book updateBook(Long id, Book book);

	boolean removeBook(Long id);

	void removeAll();
}
