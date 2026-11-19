package com.gcu.videogamestorefront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration for the Video Game Storefront.
 * Defines public and protected routes, form-based login,
 * logout behavior, password encoding, and the administrator account.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures HTTP security for the application.
     * Customer storefront routes are publicly accessible,
     * while administrator routes require the ADMIN role.
     *
     * @param http Spring Security HTTP configuration object
     * @return configured security filter chain
     * @throws Exception if the security configuration cannot be created
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/games",
                    "/games/**",
                    "/cart/**",
                    "/checkout/**",
                    "/css/**",
                    "/js/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/admin", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/games")
                .permitAll()
            );

        return http.build();
    }

    /**
     * Creates the password encoder used to securely encode
     * administrator passwords.
     *
     * @return BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates the in-memory administrator user account
     * used to access protected administrator pages.
     *
     * @param passwordEncoder password encoder used to secure the password
     * @return in-memory user details manager containing the administrator account
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}