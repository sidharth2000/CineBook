package com.cinebook.config;

import com.cinebook.interceptor.Dispatcher;
import com.cinebook.interceptor.InterceptorChain;
import com.cinebook.interceptor.JwtAuthInterceptor;
import com.cinebook.interceptor.TheatreOwnerInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DispatcherConfig {
    private final JwtAuthInterceptor jwtAuthInterceptor;
    private final TheatreOwnerInterceptor theatreOwnerInterceptor;

    public DispatcherConfig(JwtAuthInterceptor jwtAuthInterceptor, TheatreOwnerInterceptor theatreOwnerInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
        this.theatreOwnerInterceptor = theatreOwnerInterceptor;
    }

    @Bean
    public ServletRegistrationBean<Dispatcher> dispatcherServlet() {
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(jwtAuthInterceptor);
        chain.addInterceptor(theatreOwnerInterceptor);

        Dispatcher dispatcher = new Dispatcher(chain);
        return new ServletRegistrationBean<>(dispatcher, "/*");
    }
}
