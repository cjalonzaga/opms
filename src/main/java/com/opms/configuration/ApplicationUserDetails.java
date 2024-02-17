package com.opms.configuration;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.opms.db.entities.User;

public class ApplicationUserDetails<T extends User> implements UserDetails, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412673984738505761L;
	
	private final T t;

    public ApplicationUserDetails(T t){
        super();
        this.t = t;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return Collections.singleton(new SimpleGrantedAuthority( t.getUserRole().getName() ));
	}

	@Override
	public String getPassword() {
		return t.getPassword();
	}

	@Override
	public String getUsername() {
		return t.getUsername();
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
	
	public boolean hasRole(String roleName) {
        return this.t.hasRole(roleName);
    }
}
