/**
 * @author Jun Lai
 * 
 * Description:
 * 
 * Config to replace spring security default with custom RoleBased interceptor
 * 
 */

package com.cinebook.config;

import com.cinebook.interceptor.InterceptorFilter;
import com.cinebook.interceptor.JwtAuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class InterceptorFilterConfig {

	private final JwtAuthInterceptor jwtAuthInterceptor;

	public InterceptorFilterConfig(JwtAuthInterceptor jwtAuthInterceptor) {
		this.jwtAuthInterceptor = jwtAuthInterceptor;
	}

	@Bean
	public FilterRegistrationBean<InterceptorFilter> interceptorFilter() {
		FilterRegistrationBean<InterceptorFilter> bean = new FilterRegistrationBean<>();

		bean.setFilter(new InterceptorFilter(jwtAuthInterceptor));
		bean.addUrlPatterns("/*");
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
}