package kg.academia.academia_2_0.configurations;

import kg.academia.academia_2_0.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(handler -> handler
                        .accessDeniedHandler(myAccessDeniedHandler()))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
//                        .requestMatchers("**").permitAll()
//                        .requestMatchers(SecurityConstants.ERROR_PAGES).permitAll()
//                        .requestMatchers(SecurityConstants.STATIC_RESOURCES_URL).permitAll()
//                        .requestMatchers(SecurityConstants.AUTH_URLS).permitAll()
//                        .requestMatchers(SecurityConstants.SUPER_ADMIN_URLS).hasRole("SUPER_ADMIN")
//                        .requestMatchers(SecurityConstants.GENERAL_SUPER_ADMIN_AND_OWNER_URLS).hasAnyRole("SUPER_ADMIN", "BRANCH_OWNER")
//                        .requestMatchers(SecurityConstants.GENERAL_OWNER_AND_ADMIN_URLS).hasAnyRole("ADMIN", "BRANCH_OWNER")
//                        .requestMatchers(SecurityConstants.OWNER_URLS).hasRole("BRANCH_OWNER")
//                        .requestMatchers(SecurityConstants.ADMIN_URLS).hasRole("ADMIN")
//                        .requestMatchers(SecurityConstants.TEACHER_URLS).hasRole("TEACHER")
//                        .requestMatchers(SecurityConstants.PUPIL_URLS).hasRole("PUPIL")
                        )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll()
                        .successHandler(myAuthenticationSuccessHandler())
                        .failureHandler(myAuthenticationFailureHandler()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
