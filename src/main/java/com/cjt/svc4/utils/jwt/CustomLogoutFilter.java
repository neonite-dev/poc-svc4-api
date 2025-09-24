package com.cjt.svc4.utils.jwt;

import com.cjt.svc4.domain.extranet.LoginParams;
import com.cjt.svc4.service.extranet.LoginService;
import com.cjt.svc4.utils.SecurityUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    
    private final LoginService loginService;

    public CustomLogoutFilter(JWTUtil jwtUtil, LoginService loginService) {

        this.jwtUtil = jwtUtil;
        this.loginService = loginService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        //path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            if (!requestUri.matches("^\\/api/extra/login/signout$")) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // AccessToken 정보 조회 
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        if (refreshToken == null) {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String userId = jwtUtil.getUserId(refreshToken);
        LoginParams loginRequest = new LoginParams();
        loginRequest.setUserName(userId);
        loginRequest.setClientIP(SecurityUtil.getIpAddress(request));
        loginRequest.setBrowser(request.getHeader("User-Agent"));
        loginRequest.setType("O");
        try{
            this.loginService.setUserLoginHis(loginRequest);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setStatus(HttpServletResponse.SC_OK);

        
        //DB에 저장되어 있는지 확인
        // CookieUtils cookieUtils = new CookieUtils(request);
        // String userId = cookieUtils.getValue("userId");
        // LoginParams param = new LoginParams();
        // param.setUserId(userId);
        // Login login;
        // try {
        //     login = loginService.loginChk(param);
        // } catch (Exception e) {
        //     login = new Login();
        // }

        // Boolean isExist = login.getLoginYn() == "Y";
        // if (!isExist) {
        //     //response status code
        //     response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //     return;
        // }

        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        //refreshRepository.deleteByRefresh(refresh);
        // try {
        //     loginService.logout(param);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        //Refresh 토큰 Cookie 값 0
        // Cookie cookie = new Cookie("refresh", null);
        // cookie.setMaxAge(0);
        // cookie.setPath("/");

        // response.addCookie(cookie);
        // response.setStatus(HttpServletResponse.SC_OK);
    }
}
