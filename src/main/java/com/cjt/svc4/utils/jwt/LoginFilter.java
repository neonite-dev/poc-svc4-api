package com.cjt.svc4.utils.jwt;

import com.cjt.svc4.domain.extranet.LoginParams;
import com.cjt.svc4.service.extranet.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    @Autowired
    private final LoginService loginService;
    @Value("${spring.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${spring.jwt.refresh-expiration}")
    private Long refreshExpiration;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.loginService = loginService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginParams loginParams = new LoginParams();
        // JSON 형식
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginParams = objectMapper.readValue(messageBody, LoginParams.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(loginParams.getUserName());

        String userid = loginParams.getUserId();
        String password = loginParams.getPassword();

        //클라이언트 요청에서 username, password 추출
        // Form-data 형식
        // String username = obtainUsername(request);
        // String password = obtainPassword(request);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userid, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //유저 정보
        String userid = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", userid, role, accessExpiration);
        String refresh = jwtUtil.createJwt("refresh", userid, role, refreshExpiration);

        //Refresh 토큰 저장
        addRefreshEntity(userid, refresh, refreshExpiration);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        // Date date = new Date(System.currentTimeMillis() + expiredMs);

        // RefreshEntity refreshEntity = new RefreshEntity();
        // refreshEntity.setUsername(username);
        // refreshEntity.setRefresh(refresh);
        // refreshEntity.setExpiration(date.toString());

        // LoginService.save(refreshEntity);
    }
}