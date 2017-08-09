package org.fms.web.config;

import org.fms.web.service.CustomUserDetailsService;
import org.fms.web.service.RestAuthenticationEntryPoint;
import org.fms.web.service.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
	private AuthenticationManager authenticationManager;
	@Autowired
	 CustomUserDetailsService customUserDetailsService;
	@Autowired
	RestLogoutSuccessHandler restLogoutSuccessHandler;
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Autowired
	CustomSecurityMetadataSource securityMetadataSource;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login","/images/imagecode", "/checkcode","/failure").permitAll()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin()
				.defaultSuccessUrl("/loginSuccess")
				.failureUrl("/failure")
				//登陆失败后的处理
				.and()
				//登出后的处理
				.logout().logoutSuccessHandler(restLogoutSuccessHandler)
				.and()
				//认证不通过后的处理
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint);
	}
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
	public RestLogoutSuccessHandler getRestLogoutSuccessHandler(){
		return new RestLogoutSuccessHandler();
	}

	@Bean
	public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint(){
		return new RestAuthenticationEntryPoint();
	}
	@Bean
	public CustomFilterSecurityInterceptor customFilter() throws Exception{
		CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
		customFilter.setSecurityMetadataSource(securityMetadataSource);
		customFilter.setAccessDecisionManager(accessDecisionManager());
		customFilter.setAuthenticationManager(authenticationManager);
		return customFilter;
	}

	@Bean
	public CustomAccessDecisionManager accessDecisionManager() {
		return new CustomAccessDecisionManager();
	}
}

