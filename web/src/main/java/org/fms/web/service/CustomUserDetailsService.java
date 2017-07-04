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


@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User user = userRepository.findByName(userName);
		if(user == null){
			throw new UsernameNotFoundException("UserName " + userName + " not found");
		}
		return new SecurityUser(user);
	}
}
