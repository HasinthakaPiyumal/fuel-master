//package com.uokse.fuelmaster.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.uokse.fuelmaster.response.AuthErrorResponse;
//import com.uokse.fuelmaster.response.ErrorResponse;
//import com.uokse.fuelmaster.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Component
//public class AuthTokenFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
//
//    @Autowired
//    private JwtUtil jwtUtils;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        String requestURI = request.getRequestURI();
//
//        System.out.println("Request URI first: " + requestURI);
//        if (isPermittedEndpoint(requestURI)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        System.out.println("Request URI: " + requestURI);
//        try {
//            String jwt = parseJwt(request);
//            if (jwt != null && jwtUtils.validateToken(jwt)) {
//                String username = jwtUtils.extractUsername(jwt);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }else {
//                System.out.println("Cannot set the Security Context");
//                clearContextAndSetUnauthorized(request, response, filterChain, "Invalid JWT token");
//                return;
//            }
//        } catch (SignatureException e) {
//            System.out.println("Invalid JWT signature: {}"+ e.getMessage());
//        } catch (ExpiredJwtException e) {
//            System.out.println("JWT token is expired: {}"+ e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            System.out.println("JWT token is unsupported: {}"+ e.getMessage());
//        } catch (IllegalArgumentException e) {
//            System.out.println("JWT claims string is empty: {}"+ e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error processing JWT token:"+ e.getMessage());
//        }
//        System.out.println("Request URI At end: " + requestURI);
//        filterChain.doFilter(request, response);
//    }
//
//    private String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7);
//        }
//        return null;
//    }
//
//    private void clearContextAndSetUnauthorized(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String message) throws IOException, ServletException {
//        SecurityContextHolder.clearContext(); // CRITICAL: Clear the context
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType("application/json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        ErrorResponse response1 = new ErrorResponse(401, "Unauthorized");
//        objectMapper.writeValue(response.getOutputStream(), response1);
//    }
//
//    private boolean isPermittedEndpoint(String uri) {
//        return uri.startsWith("/api/v1/user/save")
//                || uri.startsWith("/api/v1/user/login")
//                || uri.startsWith("/api/v1/employee/login");
//    }
//}