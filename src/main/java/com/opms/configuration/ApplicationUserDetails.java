package com.opms.configuration;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.opms.db.entities.User;

public class ApplicationUserDetails implements UserDetails{
	
	private static final long serialVersionUID = -3875088506765024782L;
	
	private final User user;

    public ApplicationUserDetails(User user){
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return Collections.singleton(new SimpleGrantedAuthority( user.getUserRole().getName() ));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
		return user.getIsActivated();
	}
	
	public boolean hasRole(String roleName) {
        return  this.user.hasRole(roleName);
    }
}
