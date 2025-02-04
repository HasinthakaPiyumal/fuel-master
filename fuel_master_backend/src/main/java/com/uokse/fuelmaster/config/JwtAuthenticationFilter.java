package com.uokse.fuelmaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uokse.fuelmaster.exception.AccountNotVerifiedException;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.service.JwtService;
import com.uokse.fuelmaster.service.impl.UserIMPL;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.*;

@Component
@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private final JwtService jwtService;

    private final EmployeeService employeeService;
    private final UserIMPL userService;
    private final AdminService adminService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver,
            EmployeeService employeeService,
            UserIMPL userService, AdminService adminService
    ) {
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.employeeService = employeeService;
        this.userService = userService;
        this.adminService = adminService;
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
            if (isPermittedEndpoint(request.getRequestURI())) {
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
            final Object role = jwtService.extractRole(jwt);
            if (userId != null && authentication == null && role != null) {
                UserDetails userDetails = null;
                if (role.toString().equals("ROLE_USER")) {
                    User user = userService.findUser(Long.parseLong(userId));
                    userDetails = user;
                    if (user != null && !request.getRequestURI().startsWith("/api/v1/verification/verify") && !request.getRequestURI().startsWith("/api/v1/verification/resend")) {
                        if (!user.getVerified())
                            throw new AccountNotVerifiedException("Your account is not verified");
                    }
                }

                if (userDetails == null && role.toString().equals("ROLE_EMPLOYEE")) {
                    System.out.println("Employee:"+userId);
                    Optional<Employee> employeeDetails = employeeService.getEmployee(userId);
                    if (employeeDetails.isPresent()) {
                        userDetails = employeeDetails.get();
                    }
                }

                System.out.println("Role:"+role);

                if (userDetails == null && (role.toString().equals("ROLE_STATION_MANAGER") || role.toString().equals("ROLE_SUPER_ADMIN"))) {
                    Optional<Admin> employeeDetails = adminService.getAdmin(userId);
                    if (employeeDetails.isPresent()) {
                        userDetails = employeeDetails.get();
                    }
                }

                if (userDetails == null) {
                    clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                    return;
                }
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(role.toString()))
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                    return;
                }
            } else {
                clearContextAndSetUnauthorized(request, response, filterChain, "Provided token is invalid or expired");
                return;
            }
            System.out.println(request.getHeader("Authorization"));
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            clearContextAndSetUnauthorized(request, response, filterChain, "Invalid or Malformed token");
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (MissingServletRequestParameterException e) {
            System.out.println(e);
            clearContextAndSetInternalServerError(request, response, filterChain, "Bad Request");
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (AccountNotVerifiedException e) {
            System.out.println(e);
            clearContextAndSetUnauthorized(request, response, filterChain, e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println("Error processing JWT token:KK" + exception.getMessage());
            clearContextAndSetInternalServerError(request, response, filterChain, "Internal server error");
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private void clearContextAndSetUnauthorized(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(401, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }

    private void clearContextAndSetInternalServerError(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(500, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }

    private void clearContextAndSetBadRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse response1 = new ErrorResponse(400, message);
        objectMapper.writeValue(response.getOutputStream(), response1);
    }

    private boolean isPermittedEndpoint(String uri) {
        return uri.startsWith("/api/v1/user/save")
                || uri.startsWith("/api/v1/user/login")
                || uri.startsWith("/api/v1/employee/login"
                ) || uri.startsWith("/api/v1/admin/login");
    }
}