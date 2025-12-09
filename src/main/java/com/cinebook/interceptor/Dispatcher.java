package com.cinebook.interceptor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Dispatcher extends HttpServlet {
    private final InterceptorChain chain;

    public Dispatcher(InterceptorChain chain) {
        this.chain = chain;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Context context = new Context(req, resp);

        // URL whitelist
        String path = req.getRequestURI();
        if (path.startsWith("/user/login") || path.startsWith("/user/register")) {
            super.service(req, resp);
            return; // released
        }

        // into the chain of interceptors
        boolean ok = chain.before(context);
        if (!ok) {
            resp.setStatus(context.getResponseStatus());
            resp.getWriter().write("Unauthorized");
            return;
        }

        try {
            // trigger the DispatcherServlet of SpringMVC, goto the controller
            super.service(req, resp);
        } finally {
            chain.after(context);
        }
    }
}
