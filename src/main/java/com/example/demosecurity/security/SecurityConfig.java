package com.example.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails juan = User.builder()
                .username("juan")
                .password("{noop}123")
                .roles("PROFESOR")
                .build();
            UserDetails pedro = User.builder()
                .username("pedro")
                 .password("{noop}123")
                 .roles("DIRECTOR")
                .build();
        UserDetails pepe = User.builder()
                .username("pepe")
                .password("{noop}123")
                .roles("ESTUDIANTE")
                .build();

        return new InMemoryUserDetailsManager(juan, pedro,pepe);
    }*/
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

 http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/students").hasAnyRole("PROFESOR","DIRECTOR")
.requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("PROFESOR")
 .requestMatchers(HttpMethod.POST, "/api/students").hasRole("DIRECTOR")
 .requestMatchers(HttpMethod.PUT, "/api/students").hasRole("DIRECTOR")
 //.requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("DIRECTOR")
  .requestMatchers(HttpMethod.GET, "api/students").hasRole("ESTUDIANTE")

);

 // use HTTP Basic authentication
 http.httpBasic(Customizer.withDefaults());

// disable Cross Site Request Forgery (CSRF)
// in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
http.csrf(csrf -> csrf.disable());

 return http.build();

 }
}
