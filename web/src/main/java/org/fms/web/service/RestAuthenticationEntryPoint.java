package org.fms.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
*@Author 郭恒
*@Date 2017/7/5 19:31
*验证失败处理器
*/
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Authentication Failed: " + e.getMessage());
	}
}
