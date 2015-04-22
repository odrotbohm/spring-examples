package example.caching;

import static example.caching.BookRepositoryTestUtils.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Stephane Nicoll
 */
@ContextConfiguration
public class JCacheBookRepositoryTest extends AbstractBookRepositoryTest {

	@SpringBootApplication
	static class Config extends JCacheConfigurerSupport {

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
			return new JCacheBookRepository(createSampleRepository());
		}
	}
}
