package com.rootlab.awsbook.config.auth;

import com.rootlab.awsbook.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.headers().frameOptions().disable()
				.and()
					.authorizeRequests()
					.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
					.antMatchers("/api/v1/**").hasRole(Role.USER.name())
					.anyRequest().authenticated()
				.and()
					.logout()
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true).deleteCookies("SESSION")
				.and()
					.oauth2Login()
						.userInfoEndpoint()
							.userService(customOAuth2UserService);

		//Spring Security Integration with spring-session-jdbc
		http.rememberMe((rememberMe) -> rememberMe
				.rememberMeServices(rememberMeServices())
		);
	}

	/*
	spring-session-jdbc 의존성 추가시 session cookie가 SESSION, JSESSIONID 중복 생성됨
	spring-session-jdbc 공식문서의 Spring Security Integration with spring-session-jdbc 부분 참고
	ref: https://docs.spring.io/spring-session/reference/spring-security.html#spring-security-rememberme
	 */
	@Bean
	public SpringSessionRememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices =
				new SpringSessionRememberMeServices();
		// optionally customize
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}
}
