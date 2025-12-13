package com.cinebook.interceptor;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;

    public JwtAuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true); // Authenticated
    }

    @Override
    public Object getCredentials() {
        return null; // JWT is verified. no need for password
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}