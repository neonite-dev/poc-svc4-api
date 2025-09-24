package com.cjt.svc4.service.extranet;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cjt.svc4.domain.extranet.Login;

public class UserDetailsImpl implements UserDetails {

    private final Login login;
    
    public UserDetailsImpl(Login login) {
        this.login = login;
    }

    public static UserDetailsImpl build(Login user) {
        return new UserDetailsImpl(user);
    }
    // public static UserDetailsImpl build(Login user) {
    //     return new UserDetailsImpl(
    //             user.getUserId(),
    //             user.getUserName());
    // }
    
    // public UserDetailsImpl(Long id, String username, String email, String password,
	// 		Collection<? extends GrantedAuthority> authorities) {
	// 	this.id = id;
	// 	this.username = username;
	// 	this.password = password;
	// 	this.authorities = authorities;
	// }

	// public static UserDetailsImpl build(Login user) {
	// 	List<GrantedAuthority> authorities = user.getAuthYn().stream()
	// 			.map(role -> new SimpleGrantedAuthority(role.getName().name()))
	// 			.collect(Collectors.toList());

	// 	return new UserDetailsImpl(
	// 			user.getUserId(), 
	// 			user.getUserName(), 
	// 			user.getPassword(),
	// 			authorities);
	// }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return login.getAuthYn();
            }
        });
        return authorities;
	}

	public String getUserId() {
		return login.getUserId();
	}

	public String getEmail() {
		return login.getEmail();
	}

	@Override
	public String getPassword() {
		return this.getPassword();
	}

	@Override
	public String getUsername() {
		return login.getUserName();
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
