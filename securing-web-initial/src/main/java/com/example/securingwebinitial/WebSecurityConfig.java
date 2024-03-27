package com.example.securingwebinitial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // enable Spring Security’s web security support and provide the Spring MVC integration.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * defines which URL paths should be secured and which should not. For now, all other paths are required to be authenticated except for the home page.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests() // restrict access based on the HttpServletRequest
                .antMatchers("/", "/home").permitAll() // allow all users to access the home and / path
                .anyRequest().authenticated() // any other request that does not match the previous rules should be authenticated.
                .and()
            .formLogin() // returns login configurer which allows us to customize the login process
                .loginPage("/login") // specify the URL to show the login page
                .permitAll() // allow all users to access the login page
                .and()
            .logout() // returns logout configurer
                .permitAll(); // allow all users to logout
    }


    /**
     * UserDetailsService is responsible for loading user-specific data during the authentication process.
     * This method sets up an in-memory user store with a single user. That user is given a user name of user, a password of password, and a role of USER.
     */
    @SuppressWarnings("deprecation")
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
    
}
