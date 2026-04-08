package com.fuel.project.config;
 
import com.fuel.project.security.JwtFilter;
import com.fuel.project.security.CustomUserDetailsService;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 
import org.springframework.security.config.http.SessionCreationPolicy;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
@Configuration
public class SecurityConfig {
 
    @Autowired
    private JwtFilter jwtFilter;
 
    @Autowired
    private CustomUserDetailsService userDetailsService;
 
    // ✅ Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    // ✅ Authentication Provider (IMPORTANT FIX)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
     
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
 
    // ✅ Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
 
    // ✅ Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authenticationProvider(authenticationProvider());
 
        http
            .csrf(csrf -> csrf.disable())
 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()   // ✅ allow login & register
                .anyRequest().authenticated()
            )
 
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
 
            // 🔥 ADD THIS (VERY IMPORTANT)
            .authenticationProvider(authenticationProvider())
 
            // ✅ Add JWT filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
}