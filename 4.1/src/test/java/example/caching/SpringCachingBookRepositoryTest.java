package example.caching;

import static example.caching.BookRepositoryTestUtils.*;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Stephane Nicoll
 */
@ContextConfiguration
public class SpringCachingBookRepositoryTest extends AbstractBookRepositoryTest {

	@Configuration
	@EnableCaching
	static class Config extends CachingConfigurerSupport {

		@Bean
		@Override
		public CacheManager cacheManager() {
			return new ConcurrentMapCacheManager("default", "another");
		}

		@Bean
		public CacheResolver runtimeCacheResolver() {
			return new RuntimeCacheResolver(cacheManager());
		}

		@Bean
		public BookRepository bookRepository() {
			return new SpringCachingBookRepository(createSampleRepository());
		}
	}
}
