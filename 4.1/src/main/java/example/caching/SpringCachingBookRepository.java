package example.caching;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;

/**
 * @author Stephane Nicoll
 */
@Profile("spring")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "default")
public class SpringCachingBookRepository implements BookRepository {

	private final @NonNull BookRepository delegate;

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long)
	 */
	@Override
	@Cacheable
	public Book findBook(Long id) {
		return delegate.findBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long, java.lang.String)
	 */
	@Override
	@Cacheable(cacheResolver = "runtimeCacheResolver", key = "#p0")
	public Book findBook(Long id, String storeName) {
		return findBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#updateBook(java.lang.Long, example.caching.Book)
	 */
	@Override
	@CachePut(key = "#p0")
	// JSR-107 requires to specify the object to update
	public Book updateBook(Long id, Book book) {
		return delegate.updateBook(id, book);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeBook(java.lang.Long)
	 */
	@Override
	@CacheEvict
	public boolean removeBook(Long id) {
		return delegate.removeBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeAll()
	 */
	@Override
	@CacheEvict(allEntries = true)
	public void removeAll() {
		delegate.removeAll();
	}
}
