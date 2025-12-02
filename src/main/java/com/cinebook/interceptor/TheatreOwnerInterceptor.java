package com.cinebook.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TheatreOwnerInterceptor implements Interceptor {

    @Override
    public boolean before(Context context) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            context.setResponseStatus(HttpStatus.FORBIDDEN.value());
            context.setErrorMessage("Access denied: Theatre Owners only");
            return false;
        }

        boolean isTheatreOwner = userDetails.getAuthorities().stream()
                .anyMatch(auth -> "THEATRE_OWNER".equalsIgnoreCase(auth.getAuthority()));

        if (!isTheatreOwner) {
            context.setResponseStatus(HttpStatus.FORBIDDEN.value());
            context.setErrorMessage("Access denied: Theatre Owners only");
            return false;
        }

        return true;
    }

    @Override
    public void after(Context context) {
    }
}
