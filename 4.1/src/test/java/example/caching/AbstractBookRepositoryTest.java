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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Stephane Nicoll
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractBookRepositoryTest {

	@Autowired BookRepository bookRepository;
	@Autowired CacheManager cacheManager;

	protected Cache defaultCache;

	@Before
	public void setUp() {
		this.defaultCache = cacheManager.getCache("default");
	}

	protected Object generateKey(Long id) {
		return id;
	}

	@Test
	public void get() {

		Object key = generateKey(0L);

		assertDefaultCacheMiss(key);
		Book book = bookRepository.findBook(0L);
		assertDefaultCacheHit(key, book);
	}

	@Test
	public void getWithCustomCacheResolver() {

		Cache anotherCache = cacheManager.getCache("another");
		Object key = generateKey(0L);
		assertCacheMiss(key, defaultCache, anotherCache);

		Book book = bookRepository.findBook(0L, "default");
		assertCacheHit(key, book, defaultCache);
		assertCacheMiss(key, anotherCache);

		Object key2 = generateKey(1L);
		assertCacheMiss(key2, defaultCache, anotherCache);

		Book book2 = bookRepository.findBook(1L, "another");
		assertCacheHit(key2, book2, anotherCache);
		assertCacheMiss(key2, defaultCache);
	}

	@Test
	public void put() {
		Object key = generateKey(1L);

		Book book = bookRepository.findBook(1L); // initialize cache
		assertDefaultCacheHit(key, book);

		Book updatedBook = new Book(1L, "Another title");
		bookRepository.updateBook(1L, updatedBook);
		assertDefaultCacheHit(key, updatedBook);
	}

	@Test
	public void evict() {
		Object key = generateKey(2L);

		Book book = bookRepository.findBook(2L); // initialize cache
		assertDefaultCacheHit(key, book);

		assertThat(bookRepository.removeBook(2L), is(true));
		assertDefaultCacheMiss(key);
	}

	@Test
	public void evictAll() {
		bookRepository.findBook(3L);
		bookRepository.findBook(4L);

		assertThat("Cache is not empty", isEmpty(defaultCache), is(false));
		bookRepository.removeAll();
		assertThat("Cache should be empty", isEmpty(defaultCache), is(true));
	}

	private static boolean isEmpty(Cache cache) { // assuming simple implementation
		return ((ConcurrentMapCache) cache).getNativeCache().isEmpty();
	}

	private static void assertCacheMiss(Object key, Cache... caches) {
		for (Cache cache : caches) {
			assertThat("No entry should have been found in " + cache + " with key " + key, cache.get(key), is(nullValue()));
		}
	}

	private static void assertCacheHit(Object key, Book book, Cache... caches) {

		for (Cache cache : caches) {

			Cache.ValueWrapper wrapper = cache.get(key);

			assertThat("An entry should have been found in " + cache + " with key " + key, wrapper, is(notNullValue()));
			assertThat("Wrong value for entry in " + cache + " with key " + key, (Book) wrapper.get(), is(book));
		}
	}

	private void assertDefaultCacheMiss(Object key) {
		assertCacheMiss(key, defaultCache);
	}

	private void assertDefaultCacheHit(Object key, Book book) {
		assertCacheHit(key, book, defaultCache);
	}
}
