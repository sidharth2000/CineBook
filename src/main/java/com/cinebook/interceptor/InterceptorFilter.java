/**
 * @author Jun Lai
 * 
 * 
 */

package com.cinebook.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class InterceptorFilter implements Filter {

    private final JwtAuthInterceptor jwtAuthInterceptor;

    public InterceptorFilter(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        Context context = new Context(req, resp);

        // UserController：pass
        if (path.equals("/user/login") || path.equals("/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // others need Authentication
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(jwtAuthInterceptor);

        // add interceptor by identity
        if (path.startsWith("/theatre")) {
            chain.addInterceptor(new RoleInterceptor("THEATRE_OWNER"));
        } else if (path.startsWith("/booking")) {
            chain.addInterceptor(new RoleInterceptor("CUSTOMER"));
        } else if (path.startsWith("/admin")) {
            chain.addInterceptor(new RoleInterceptor("ADMIN"));
        } else if (path.startsWith("/loyalty")) {
            chain.addInterceptor(new RoleInterceptor("CUSTOMER"));
        }

        // before
        if (!chain.before(context)) {
            resp.setStatus(context.getResponseStatus());
            if (context.getErrorMessage() != null) {
                resp.getWriter().write(context.getErrorMessage());
            }
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            chain.after(context);
        }
    }
}