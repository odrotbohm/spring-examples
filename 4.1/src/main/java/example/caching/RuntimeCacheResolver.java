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

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

/**
 * A sample {@link org.springframework.cache.interceptor.CacheResolver} that demonstrates the runtime resolution of the
 * cache(s) to use. This is a rather simple case that assumes the second parameter of the method invocation is the name
 * of the cache to use
 *
 * @author Stephane Nicoll
 * @author Oliver Gierke
 */
public class RuntimeCacheResolver extends AbstractCacheResolver {

	public RuntimeCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.cache.interceptor.AbstractCacheResolver#getCacheNames(org.springframework.cache.interceptor.CacheOperationInvocationContext)
	 */
	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		return Collections.singleton((String) context.getArgs()[1]);
	}
}
