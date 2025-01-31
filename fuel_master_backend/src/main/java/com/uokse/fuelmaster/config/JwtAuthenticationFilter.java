package com.uokse.fuelmaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.service.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final EmployeeService employeeService;
    private UserDetails userDetails;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver, EmployeeService employeeService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.employeeService = employeeService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(isPermittedEndpoint(request.getRequestURI())){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        null,
                        null,
                        null
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                doFilter(request, response, filterChain);
                return;
            }
            final String jwt = authHeader.substring(7);
            final String userId = jwtService.extractUsername(jwt);
            if (userId != null && authentication == null) {
                System.out.println("Checking user registry" );
                UserDetails userDetails = null;
                try {
                    userDetails = this.userDetailsService.loadUserByUsername(userId);
                } catch (UsernameNotFoundException e) {
//                    throw new RuntimeException(e);
                    System.out.println("User not found: " + e.getMessage());
                }

                try {
                    if(userDetails == null){
                        Optional<Employee> employeeDetails = employeeService.getEmployee(userId);
                        if(employeeDetails.isPresent()){
                           userDetails = employeeDetails.get();
                        }
                    }
                } catch (UsernameNotFoundException e) {
                    System.out.println("Employee not found: " + e.getMessage());
                    throw new RuntimeException(e);
                }

                if (userDetails == null) {
                    clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                    return;
                }
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("Token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    System.out.println("AuthToken: " + authToken);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Security Context set");
                }else{
                    clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                    return;
                }
            }else {
                clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                return;
            }
            System.out.println(request.getHeader("Authorization"));
            filterChain.doFilter(request, response);
        }catch (MalformedJwtException e){
            System.out.println("Invalid JWT token: " + e.getMessage());
            clearContextAndSetUnauthorized(request, response, filterChain, "Invalid or Malformed token");
            handlerExceptionResolver.resolveException(request, response, null, e);
        }catch (MissingServletRequestParameterException e){
            System.out.println(e);
            clearContextAndSetInternalServerError(request, response, filterChain, "Bad Request");
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
        catch (Exception exception) {
            System.out.println(exception);
            System.out.println("Error processing JWT token:KK" + exception.getMessage());
            clearContextAndSetInternalServerError(request, response, filterChain, "Internal server error");
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
    private void clearContextAndSetUnauthorized(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext(); // CRITICAL: Clear the context
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(401, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }
    private void clearContextAndSetInternalServerError(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext(); // CRITICAL: Clear the context
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(500, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }
    private void clearContextAndSetBadRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext(); // CRITICAL: Clear the context
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(400, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }

    private boolean isPermittedEndpoint(String uri) {
        return uri.startsWith("/api/v1/user/save")
                || uri.startsWith("/api/v1/user/login")
                || uri.startsWith("/api/v1/employee/login");
    }
}