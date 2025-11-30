package com.cinebook.config;

import com.cinebook.interceptor.Dispatcher;
import com.cinebook.interceptor.InterceptorChain;
import com.cinebook.interceptor.JwtAuthInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DispatcherConfig {
    private final JwtAuthInterceptor jwtAuthInterceptor;

    public DispatcherConfig(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }

    @Bean
    public ServletRegistrationBean<Dispatcher> dispatcherServlet() {
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(jwtAuthInterceptor);

        Dispatcher dispatcher = new Dispatcher(chain);
        return new ServletRegistrationBean<>(dispatcher, "/*");
    }
}
