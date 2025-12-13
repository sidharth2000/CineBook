package com.cinebook.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class RoleInterceptor implements Interceptor {

    private final String requiredRole;

    public RoleInterceptor(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    @Override
    public boolean before(Context context) {

        System.out.println(">>>>>>>>>>>> RoleInterceptor.before for " + requiredRole);

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            context.setResponseStatus(403);
            context.setErrorMessage("Access denied");
            return false;
        }

        boolean hasRole = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase(requiredRole));

        if (!hasRole) {
            context.setResponseStatus(403);
            context.setErrorMessage("Requires role: " + requiredRole);
            return false;
        }

        return true;
    }

    @Override
    public void after(Context context) {
        // nothing
    }
}