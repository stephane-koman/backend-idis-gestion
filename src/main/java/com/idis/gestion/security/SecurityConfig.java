package com.idis.gestion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/index.html", "/ajax/**", "/angtrans/**", "/bower_components/**", "/css/**", "/fonts/**", "/images/**", "/img/**", "/js/**", "/less/**", "/rtl/**", "/scripts/**", "/styles/**", "/views/**").permitAll();
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/admin/**").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers("/api/user/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/profile/**").hasAnyAuthority("ADMIN", "USER", "CLIENT");
        http.authorizeRequests().antMatchers("/api/user/**").hasAnyAuthority("ADMIN", "USER");
        //http.authorizeRequests().antMatchers("/api/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/api/client/**").hasAuthority("CLIENT");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(filter);
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
