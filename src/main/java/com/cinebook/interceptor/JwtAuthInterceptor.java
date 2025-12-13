/**
 * @author Jun Lai
 * 
 * 
 */

package com.cinebook.interceptor;

import com.cinebook.service.JwtService;
import com.cinebook.service.UserDetailsServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthInterceptor implements Interceptor {

	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;

	public JwtAuthInterceptor(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public boolean before(Context context) {

		System.out.println(">>>>>>>>>>>> JwtAuthInterceptor.before");

		String token = context.getRequest().getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			context.setResponseStatus(401);
			return false;
		}
		token = token.substring(7); // remove Bearer

		String username = jwtService.extractUsername(token);

		// use JwtService to verify token
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (username == null || !jwtService.isTokenValid(token, userDetails)) {
			context.setResponseStatus(401);
			return false;
		}

		SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails));
		return true;
	}

	@Override
	public void after(Context context) {
		// clear SecurityContext
		SecurityContextHolder.clearContext();
	}
}