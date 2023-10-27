package com.zerobase.dividends.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * ------------------------------------------------------------------------------
 * difference between spring boot 2.x.x and spring boot 3.xx in spring security
 * <p>
 * https://velog.io/@hwsa1004/SpringBoot-3.1.0-SpringSecurity-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0
 * <p>
 * https://devhan.tistory.com/310
 * ------------------------------------------------------------------------------
 * <p>
 * ------------------------------------------------------------------------------
 * Failed to instantiate [org.springframework.security.web.SecurityFilterChain]:
 * Factory method 'filterChain' threw exception with message: This method cannot decide whether these patterns are Spring MVC patterns or not.
 * If this endpoint is a Spring MVC endpoint, please use requestMatchers(MvcRequestMatcher);
 * otherwise, please use requestMatchers(AntPathRequestMatcher).
 * <p>
 * https://stackoverflow.com/questions/76809698/spring-security-method-cannot-decide-pattern-is-mvc-or-not-spring-boot-applicati
 * ------------------------------------------------------------------------------
 * <p>
 * ------------------------------------------------------------------------------
 * If Spring MVC is used, then by default auth.requestMatchers() will use the MvcRequestMatcher.
 * Since H2 console is not controlled by Spring MVC, we must use AntPathRequestMatcher.
 * <p>
 * https://github.com/spring-projects/spring-security/issues/12546
 * <p>
 * https://www.inflearn.com/questions/836032/h2-console-403-%EC%97%90%EB%9F%AC
 * <p>
 * ------------------------------------------------------------------------------
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests(request -> request
                        .requestMatchers(
                                mvc.pattern("/auth"))
                        .permitAll()
                )
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

}
