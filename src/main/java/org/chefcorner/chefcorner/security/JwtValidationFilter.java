package org.chefcorner.chefcorner.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.services.implementation.JpaUserDetailsService;
import org.chefcorner.chefcorner.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JpaUserDetailsService userDetailsService;
    private final JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final WebUserDetails userDetails = (WebUserDetails) userDetailsService.loadUserByUsername(username);
            String path = request.getRequestURI();
            boolean isRefreshToken = path.contains("/api/auth/refresh");
            boolean isValidToken = isRefreshToken ?
                    jwtUtil.validateRefreshToken(jwt, userDetails) :
                    jwtUtil.validateAccessToken(jwt, userDetails);

            if(isValidToken){
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            }
        }

        filterChain.doFilter(request, response);
    }
}
