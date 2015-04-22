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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

/**
 * @author Stephane Nicoll
 * @author Oliver Gierke
 */
@Slf4j
class SimpleBookRepository implements BookRepository {

	private final ConcurrentHashMap<Long, Book> content;

	public SimpleBookRepository(Map<Long, Book> content) {

		Assert.notNull(content, "Source map must not be null!");
		this.content = new ConcurrentHashMap<Long, Book>(content);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long)
	 */
	@Override
	public Book findBook(Long id) {

		log.debug("looking up book with id {}", id);
		return content.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long, java.lang.String)
	 */
	@Override
	public Book findBook(Long id, String storeName) {

		log.debug("looking up book with id {} and cache it in {}", id, storeName);
		return content.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#updateBook(java.lang.Long, example.caching.Book)
	 */
	@Override
	public Book updateBook(Long id, Book book) {

		log.debug("Updating book with id {} to {}", id, book);
		content.put(id, book);

		return book; // no transformation
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeBook(java.lang.Long)
	 */
	@Override
	public boolean removeBook(Long id) {

		log.debug("Removing book with id {}", id);
		return content.remove(id) != null;
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeAll()
	 */
	@Override
	public void removeAll() {

		log.debug("Removing all books");
		content.clear();
	}
}
