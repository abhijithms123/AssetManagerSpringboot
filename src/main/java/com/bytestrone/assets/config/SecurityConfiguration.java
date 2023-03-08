package com.bytestrone.assets.config;


import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeHttpRequests()
		        .antMatchers("/api/v1/auth/**").permitAll()
//		        .antMatchers("/hardwareDashboard/**").hasRole("ADMIN")
//		        .antMatchers("/softwareDashboard/**").hasRole("ADMIN")
				.anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/app/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                
		return http.build();
	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable().authorizeHttpRequests(authorize -> {
//			try {
//				authorize.antMatchers("/hardware/**").hasRole("ADMIN")
//						.antMatchers("/api/v1/auth/**").permitAll()
//						.anyRequest().authenticated().and().sessionManagement()
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//						.authenticationProvider(authenticationProvider)
//						.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//		        
//
//		return http.build();
//	}
	
	
}
