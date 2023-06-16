package com.codeup.pawspursuit;

import com.codeup.pawspursuit.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/", "/posts", "/pets", "/register", "/profile/{id}", "/posts/{id}", "/pets/{id}", "/error")
                // anyone can see home, the post pages, and sign up
                .permitAll()
                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/create", "/pets/create", "/posts/create", // only authenticated users can create posts
                        "/posts/{id}/edit", "/pets/{id}/edit", // only authenticated users can edit posts
                        "/posts/{id}/delete", "/pets/{id}/delete", // only authenticated users can delete posts
                        "/profile","/profile/{id}/edit", "/chat/{r_id}", "/comment/pet", "/comment/post", "/comment/edit", "/comment/delete", "profile/delete", "/chat"

                )
                .authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/favicon.ico")
                .permitAll();
        return http.build();
    }

}
