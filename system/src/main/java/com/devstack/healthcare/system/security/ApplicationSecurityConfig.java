package com.devstack.healthcare.system.security;

import com.devstack.healthcare.system.jwt.JwtConfig;
import com.devstack.healthcare.system.jwt.JwtTokenVerifier;
import com.devstack.healthcare.system.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.devstack.healthcare.system.service.impl.ApplicationUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserServiceImpl applicationUserService;
    //private final UserService;
    private  final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserServiceImpl applicationUserService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setExposedHeaders(List.of("Authorization"));

        http.csrf().disable()
                .cors().configurationSource(request -> configuration)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(
                        new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),
                                jwtConfig,secretKey)
                )
                .addFilterAfter(new JwtTokenVerifier(jwtConfig,secretKey),
                        JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/users/visitor/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
        return daoAuthenticationProvider;
    }
}
