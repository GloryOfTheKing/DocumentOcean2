package org.fms.web.service;

import org.fms.mysql.entity.User;
import org.fms.mysql.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
*@Author 郭恒
*@Date 2017/7/5 18:43
*用户详细信息服务
*/

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	/*
	*通过用户名加载用户信息
	*/
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByName(userName);
		if(user == null){
			logger.info("UserName " + userName +" notlogin");
			throw new UsernameNotFoundException("UserName " + userName + " not found");
		}else {
			logger.info("UserName " + userName +" login");
		}
		return new SecurityUser(user);
	}
}
