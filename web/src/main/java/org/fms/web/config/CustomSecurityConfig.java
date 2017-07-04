package org.fms.web.config;

import org.fms.mysql.repository.UserRepository;
import org.fms.web.service.CustomUserDetailsService;
import org.fms.web.service.LoginSuccessHandler;
import org.fms.web.service.RestAuthenticationEntryPoint;
import org.fms.web.service.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by lion on 2017/6/27.
 */
@Configuration
@EnableWebSecurity
//用于@PreAuthorize的生效,基于方法的权限控制
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	 CustomUserDetailsService customUserDetailsService;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;
	@Autowired
	RestLogoutSuccessHandler restLogoutSuccessHandler;
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/images/imagecode", "/checkcode","/test").permitAll()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin()
				.successHandler(loginSuccessHandler)
				//登陆失败后的处理
				.failureHandler(simpleUrlAuthenticationFailureHandler)
				.and()
				//登出后的处理
				.logout().logoutSuccessHandler(restLogoutSuccessHandler)
				.and()
				//认证不通过后的处理
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint);
	};


	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		//remember me
		auth.eraseCredentials(false);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LoginSuccessHandler getLoginSuccessHandler(){
		return new LoginSuccessHandler();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler getSimpleUrlAuthenticationFailureHandler(){
		return new SimpleUrlAuthenticationFailureHandler();
	}

	@Bean
	public RestLogoutSuccessHandler getRestLogoutSuccessHandler(){
		return new RestLogoutSuccessHandler();
	}

	@Bean
	public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint(){
		return new RestAuthenticationEntryPoint();
	}
}

