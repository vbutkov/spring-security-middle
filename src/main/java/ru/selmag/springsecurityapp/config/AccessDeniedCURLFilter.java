package ru.selmag.springsecurityapp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AccessDeniedCURLFilter extends OncePerRequestFilter {

    private Logger LOGGER = LoggerFactory.getLogger(AccessDeniedCURLFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        LOGGER.info(userAgent);
        if (userAgent != null && userAgent.startsWith("curl")) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
