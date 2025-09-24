package com.cjt.svc4.service.extranet;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.extranet.Login;
import com.cjt.svc4.domain.extranet.LoginParams;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginService loginService;

    public UserDetailsServiceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

	@Override
	@Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LoginParams loginParamsRequest = new LoginParams();
        loginParamsRequest.setUserId(userId);
		List<Login> user;
        try {
            user = loginService.userInfo(loginParamsRequest);
            if (user.size() == 0) {
                return null;
                // throw new UsernameNotFoundException("User Not Found with username: " + userId);    
            }
        } catch (Exception e) {
            return null;
            // throw new UsernameNotFoundException("User Not Found with username: " + userId);
        }
        return UserDetailsImpl.build(user.getFirst());
	}
}
