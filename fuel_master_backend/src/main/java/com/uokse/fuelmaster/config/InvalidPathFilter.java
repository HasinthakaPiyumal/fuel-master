package com.uokse.fuelmaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uokse.fuelmaster.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.Map;

@Component
public class InvalidPathFilter extends OncePerRequestFilter {

    private final Map<String, HandlerMapping> handlerMappings;

    public InvalidPathFilter(Map<String, HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!isPathValid(request)) {
            clearContextAndSetNotFount(request, response, filterChain, request.getRequestURI()+" not found");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPathValid(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings.values()) {
            try {
                HandlerExecutionChain handler = handlerMapping.getHandler(request);
                if (handler != null) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private void clearContextAndSetNotFount(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(404, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }
}
