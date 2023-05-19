package com.etraveli.cardcost.config;

import static com.etraveli.cardcost.enums.Permission.BO_CREATE;
import static com.etraveli.cardcost.enums.Permission.BO_DELETE;
import static com.etraveli.cardcost.enums.Permission.BO_READ;
import static com.etraveli.cardcost.enums.Permission.BO_UPDATE;
import static com.etraveli.cardcost.enums.Permission.USER_READ;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.etraveli.cardcost.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;


@Profile(value = "default")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UnauthorizedEntryPoint unauthorizedEntryPoint;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  @Bean
  @Order(1)
  public SecurityFilterChain auth0FilterChain(HttpSecurity http) throws Exception {
    http.cors()
    .and()
    .csrf().disable()
    .authorizeHttpRequests().requestMatchers(
        "/api/v1/auth/authenticate",
        "/api/v1/auth/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/swagger-ui.html")
    .permitAll()
    /*.requestMatchers(GET, "/api/v1/management/**")
      .hasAnyRole(BO_READ.name())
     .requestMatchers(POST, "/api/v1/management/**")
      .hasAnyRole(BO_CREATE.name())
     .requestMatchers(PATCH, "/api/v1/management/**")
      .hasAnyRole(BO_UPDATE.name())
     .requestMatchers(DELETE, "/api/v1/management/**")
      .hasAnyRole(BO_DELETE.name())
    .requestMatchers(GET, "/payment-cards-cost")
      .hasAnyRole(USER_READ.name())*/
    .requestMatchers("/api/v1/management/**")
    .hasAnyRole(RoleTypeEnum.BO.name())
    .requestMatchers(GET, "/api/v1/management/**")
    .hasAnyAuthority(BO_READ.name(), BO_UPDATE.name(), PATCH.name(), BO_DELETE.name())
    .requestMatchers(POST, "/api/v1/management/**")
    .hasAnyAuthority(BO_READ.name(), BO_UPDATE.name(), PATCH.name(), BO_DELETE.name())
    .requestMatchers(PATCH, "/api/v1/management/**")
    .hasAnyAuthority(BO_READ.name(), BO_UPDATE.name(), PATCH.name(), BO_DELETE.name())
    .requestMatchers(DELETE, "/api/v1/management/**")
    .hasAnyAuthority(BO_READ.name(), BO_UPDATE.name(), PATCH.name(), BO_DELETE.name())

    
    .requestMatchers("/payment-cards-cost")
    .hasAnyRole(RoleTypeEnum.USER.name())
    .anyRequest().authenticated()
    .and()
    //.exceptionHandling()
    //.authenticationEntryPoint(unauthorizedEntryPoint)
    //.and()
    .sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and().authenticationProvider(authenticationProvider)
    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    ;
    http.csrf().disable();
    return http.build();
  }

}
