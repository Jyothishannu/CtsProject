package com.fuel.project.security;
 
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import java.io.IOException;
 
@Component
public class JwtFilter extends OncePerRequestFilter {
 
    @Autowired
    private JwtUtil jwtUtil;
 
    @Autowired
    private CustomUserDetailsService userDetailsService;
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
 
        String path = request.getServletPath();
 
        // 🔥 Skip auth endpoints
        if (path.startsWith("/auth/")) {
            chain.doFilter(request, response);
            return;
        }
 
        String header = request.getHeader("Authorization");
 
        if (header != null && header.startsWith("Bearer ")) {
 
            String token = header.substring(7);
 
            try {
                String username = jwtUtil.extractUsername(token);
 
                if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
 
                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(username);
 
                    if (jwtUtil.validateToken(token, username)) {
 
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
 
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
 
            } catch (Exception e) {
                System.out.println("JWT Error: " + e.getMessage());
            }
        }
 
        chain.doFilter(request, response);
    }
}