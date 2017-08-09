package org.fms.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*
*@Author 郭恒
*@Date 2017/7/5 15:13
*/
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
	/*
	*全局设置的允许跨区请求的路径
	*/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")//参数代表可以访问该接口的域名，设置为”*”可支持所有域。
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT")//请求方式
				.maxAge(3600);
	}

}