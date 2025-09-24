package com.cjt.svc4.service.extranet;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.extranet.Login;
import com.cjt.svc4.domain.extranet.LoginParams;
import com.cjt.svc4.mapper.extranet.LoginMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginMapper loginMapper;
 
    @Transactional
    public Login loginChk(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.loginChk(loginParamsRequest);
    }

    @Transactional
    public LoginParams getUserDuplicateWhether(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.getUserDuplicateWhether(loginParamsRequest);
    }

    @Transactional(readOnly = true)
    public Login checkSMSAuth(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.checkSMSAuth(loginParamsRequest);
    }

    @Transactional
    public Login userAuthCheck(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.userAuthCheck(loginParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Login> userInfo(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.userInfo(loginParamsRequest);
    }
    
    @Transactional
    public void authNumInsert(LoginParams loginParamsRequest) throws Exception {
        loginMapper.authNumInsert(loginParamsRequest);
    }

    @Transactional
    public Login setUserLoginHis(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.setUserLoginHis(loginParamsRequest);
    }

    @Transactional
    public void logout(LoginParams loginParamsRequest) throws Exception {
        loginMapper.logout(loginParamsRequest);
    }

    @Transactional(readOnly = true)
    public LoginParams getUserLogoutWhether(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.getUserLogoutWhether(loginParamsRequest);
    }

    @Transactional(readOnly = true)
    public Login getOtpKey(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.getOtpKey(loginParamsRequest);
    }

    @Transactional
    public Login otpKeyUpdate(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.otpKeyUpdate(loginParamsRequest);
    }

    @Transactional
    public Login loginPwChange(LoginParams loginParamsRequest) throws Exception {
        return loginMapper.loginPwChange(loginParamsRequest);
    }

    
}
