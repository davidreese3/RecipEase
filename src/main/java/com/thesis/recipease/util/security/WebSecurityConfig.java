package com.thesis.recipease.util.security;

/*
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                // mapped by position to (username, password, active)
                .usersByUsernameQuery("SELECT email, password, active FROM account "
                        + " WHERE email=?")
                // mapped by position to (username, role)
                .authoritiesByUsernameQuery("select email, role FROM authority "
                        + " WHERE email=?");
        ;
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> {
            try {
                authz
                        .requestMatchers("/", "/login", "/account/register", "/account/activate","/403", "/logout", "/resources/**", "/static/**", "/css/**").permitAll()
                        // Protected URLs
                        .requestMatchers("/user/list/**", "/job/**, /landing").authenticated()
                        // Any other request must be authenticated
                        .requestMatchers("/user/db/**").hasRole("FACULTY")
                        .anyRequest().authenticated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // when access denied, show "/403" page
        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedPage("/403"));
        // "/login" allow custom login page
        // "/login?error" displays login.html with error message
        // "/userHome" default page when /login was the path.
        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/landing")
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")                   // The URL to handle logout
                .logoutSuccessUrl("/login?logout")      // Redirect URL after successful logout
                .invalidateHttpSession(true)            // Invalidate session on logout
                .deleteCookies("JSESSIONID")            // Remove session cookie
                .permitAll()
        );


        // needed for auto-login
        http.securityContext((securityContext) -> securityContext.requireExplicitSave(true));

        return http.build();
    }

    // needed for auto login
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }


    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return (request, response, exception) -> {
            if (exception.getCause() instanceof CustomUserDetailsService.InactiveAccountException) {
                response.sendRedirect("/login?inactive");
            } else {
                response.sendRedirect("/login?authenticationError");
            }
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> {
            try {
                authz
                        .requestMatchers("/", "/login", "/account/register", "/account/activate", "/account/reset/**", "/403", "/logout", "/resources/**", "/static/**", "/css/**").permitAll()
                        // Protected URLs
                        .requestMatchers("/landing","/profile/**","/glossary/**").authenticated()
                        .requestMatchers("/user/db/**").hasRole("FACULTY")
                        .anyRequest().authenticated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        http.exceptionHandling(exceptionHandling -> exceptionHandling
//                .accessDeniedPage("/403"));
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint())
        );

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureHandler(customFailureHandler())
                .defaultSuccessUrl("/landing")
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        http.securityContext(securityContext -> securityContext.requireExplicitSave(true));

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendRedirect("/login?loginRequiredError");
        };
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }
}
