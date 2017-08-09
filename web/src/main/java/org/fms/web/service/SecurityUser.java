package org.fms.web.service;

import org.fms.mysql.entity.Role;
import org.fms.mysql.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1L;
	public SecurityUser(User user) {
		if(user != null)
		{
			this.setId(user.getId());
			this.setName(user.getName());
			this.setDeparment(user.getDeparment());
			this.setJob(user.getJob());
			this.setPhone(user.getPhone());
			this.setEmail(user.getEmail());
			this.setRoles(user.getRoles());
			this.setPassword(user.getPassword());
			this.setState(user.getState());
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<Role> roles = this.getRoles();
		if(roles != null)
		{
			for (Role role : roles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
				authorities.add(authority);
			}
		}
		return authorities;
	}


	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
