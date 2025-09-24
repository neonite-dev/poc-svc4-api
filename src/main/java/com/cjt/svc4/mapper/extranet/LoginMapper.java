package com.cjt.svc4.mapper.extranet;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cjt.svc4.domain.extranet.Login;
import com.cjt.svc4.domain.extranet.LoginParams;

@Repository
@Mapper
public interface LoginMapper {
    /* 
     * 로그인 체크
     * UserId, Password, UserIP
     */
    Login loginChk(LoginParams loginParamsRequest);
    
    /* 
     * 중복 로그인 체크
     * UserName(아이디), ClientIP, Browser, DuplicateYN
     */
    LoginParams getUserDuplicateWhether(LoginParams loginParamsRequest);
    
    /* 
     * SMS 인증정보 업데이트
     * UserId, AuthNum
     */
    Login checkSMSAuth(LoginParams loginParamsRequest);

    /* 
     * 2차인증 핸드폰 정보 체크
     * UserId
     */
    Login userAuthCheck(LoginParams loginParamsRequest);

    /* 
     * 사용자 정보
     * UserId
     */
    List<Login> userInfo(LoginParams loginParamsRequest);

    /* 
     * SMS인증번호 저장
     * UserId, AuthNum
     */
    void authNumInsert(LoginParams loginParamsRequest);

    /* 
     * 로그인 히스토리
     * UserName(아이디), ClientIP, Browser, Type(I:로그인, O:로그아웃)
     */
    Login setUserLoginHis(LoginParams loginParamsRequest);

    /* 
     * 로그아웃 시간 업데이트
     * UserName(아이디)
     */
    void logout(LoginParams loginParamsRequest);
    
    /* 
     * 로그아웃 여부
     * UserName(아이디), LogoutYN(Y:로그아웃, N:로그인)
     */
    LoginParams getUserLogoutWhether(LoginParams loginParamsRequest);
    
    /* 
     * 마이페이지 사용자정보 OTP 키 정보
     * UserId
     */
    Login getOtpKey(LoginParams loginParamsRequest);

    /* 
     * OTP키 업데이트
     * UserId, OtpKey
     */
    Login otpKeyUpdate(LoginParams loginParamsRequest);

    /* 
     * 비밀번호 변경
     * UserId, Password, UserIP
     */
    Login loginPwChange(LoginParams loginParamsRequest);


}
