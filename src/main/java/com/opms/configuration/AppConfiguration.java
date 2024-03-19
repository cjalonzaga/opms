package com.opms.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfiguration {
	
	@Autowired
    private ApplicationUserDetailsService userDetailsService;
	
	@Autowired
	private LoginSuccessHandlers loginSuccessHandlers;
	
	@Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  provider;
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers(HttpMethod.GET ,"/signup" ,"/ajax/**", 
						"/signup/**", "/css/**" , "/js/**" , "/vendors/**", "/assets/**" , "/images/**").permitAll()
				.requestMatchers(HttpMethod.POST , "/signup/**").permitAll()
				.anyRequest()
				.authenticated()
		)
		.formLogin( (formLogin) ->
			formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login_process")
				.successHandler(loginSuccessHandlers)
				.failureUrl("/login?failed")
				.permitAll()
		).logout( (logout) ->
			logout
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.logoutUrl("/perform_logout")
				.logoutSuccessUrl("/login")
				.permitAll()
		);
        
        return http.build();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
}
