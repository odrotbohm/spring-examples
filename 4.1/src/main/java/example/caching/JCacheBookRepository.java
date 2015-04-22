package example.caching;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Stephane Nicoll
 */
@RequiredArgsConstructor
@CacheDefaults(cacheName = "default")
@CacheConfig(cacheNames = "default")
class JCacheBookRepository implements BookRepository {

	private final @NonNull BookRepository delegate;

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long)
	 */
	@Override
	@CacheResult
	public Book findBook(Long id) {
		return delegate.findBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#findBook(java.lang.Long, java.lang.String)
	 */
	@Override
	// Example of mixed operations, can be tricky with keys
	@Cacheable(cacheResolver = "runtimeCacheResolver", // Don't want to play with JSR-107 cache
			key = "#p0")
	public Book findBook(Long id, String storeName) {
		return delegate.findBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#updateBook(java.lang.Long, example.caching.Book)
	 */
	@Override
	@CachePut
	public Book updateBook(Long id, @CacheValue Book book) {
		return delegate.updateBook(id, book);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeBook(java.lang.Long)
	 */
	@Override
	@CacheRemove
	public boolean removeBook(Long id) {
		return delegate.removeBook(id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.caching.BookRepository#removeAll()
	 */
	@Override
	@CacheRemoveAll
	public void removeAll() {
		delegate.removeAll();
	}
}
