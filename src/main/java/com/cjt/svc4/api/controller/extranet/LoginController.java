package com.cjt.svc4.api.controller.extranet;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cjt.svc4.domain.extranet.CommonParams;
import com.cjt.svc4.domain.extranet.Login;
import com.cjt.svc4.domain.extranet.LoginParams;
import com.cjt.svc4.service.extranet.CommonService;
import com.cjt.svc4.service.extranet.LoginService;
import com.cjt.svc4.utils.SecurityUtil;
import com.cjt.svc4.utils.jwt.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name="Login Controller API", description = "로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/extra/login")
public class LoginController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private final LoginService loginService;
    private final JWTUtil jwtUtil;
    @Value("${spring.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${spring.jwt.refresh-expiration}")
    private Long refreshExpiration;
    @Autowired
    private final CommonService commonService;

    @Operation(summary = "JWT 로그인 인증", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "signin")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response, LoginParams loginRequest) throws Exception {
        
        Login loginResponse = new Login();
        String loginStatus = loginService.loginChk(loginRequest).getLoginYn();
        loginResponse.setLoginYn(loginStatus);
        if (loginStatus.equals("N")) {
            loginResponse.setResult("로그인 정보가 정확하지 않습니다.");
            return ResponseEntity.ok(loginResponse);
        } else if (loginStatus.equals("B")) {
            loginResponse.setResult("계정이 잠금처리 되었습니다. 사용을 원하시면 관리자에게 문의 하시기 바랍니다.");
            return ResponseEntity.ok(loginResponse);
        } else if (loginStatus.equals("C")) {
            loginResponse.setResult("ChangePassword");
            return ResponseEntity.ok(loginResponse);
        }
        // loginStatus == "C" => ChangePassword

        // loginType => "SMS", "OPT" 설정 체크
        // 아래 구문 수행 후 별도 리턴 수행 할것

        // 로그인 히스토리 입력 //SetUserLoginHis 타입(I:로그인, O:로그아웃)
        if(loginRequest.getUserName() != loginRequest.getUserId()){
            loginRequest.setUserName(loginRequest.getUserId());
        }
        loginRequest.setClientIP(SecurityUtil.getIpAddress(request));
        loginRequest.setBrowser(request.getHeader("User-Agent"));
        loginRequest.setType("I");
        loginService.setUserLoginHis(loginRequest);
        

        // 다중처리 되므로 우선 하나만 추출 -> 로직 수정할것
        List<Login> loginUserInfos = loginService.userInfo(loginRequest);
        if (loginUserInfos.size() > 0) {
            loginResponse = loginUserInfos.getFirst();
            loginResponse.setLoginYn(loginStatus);
        }

        // role은 임의 설정 GetAuthList 에서 필터 하고 올것
        String role = "User";

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", loginRequest.getUserId(), role, accessExpiration); // 90분: 5400000 // 1시간 : 3600000 //10분: 600000
        String newRefresh = jwtUtil.createJwt("refresh", loginRequest.getUserId(), role, refreshExpiration); // 1일

        loginResponse.setLoginYn("Y");
        loginResponse.setToken(newAccess);
        loginResponse.setRefreshToken(newRefresh);
        loginResponse.setUserId(loginRequest.getUserId());

        CommonParams param = new CommonParams();
        param.setUserId(loginRequest.getUserId().toString());
        param.setPageNo(1);
        param.setPageSize(20);
        List<CommonParams> list = commonService.getAuthList(param);

        if(!list.isEmpty()){  loginResponse.setAuthYn("true");}

        return ResponseEntity.ok(loginResponse);
    }
    
    @Operation(summary = "JWT Refresh Token", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 헤더에서 Authorization 키에 담긴 토큰을 꺼냄
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        if (refreshToken == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.FORBIDDEN);
        }

        //expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.FORBIDDEN);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.FORBIDDEN);
        }

        // role은 임의 설정 GetAuthList 에서 필터 하고 올것
        String userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);
        //make new JWT
        String newAccess = jwtUtil.createJwt("access", userId, role, accessExpiration); // 90분: 5400000 // 1시간 : 3600000 //10분: 600000
        String newRefresh = jwtUtil.createJwt("refresh", userId, role, refreshExpiration); // 1일

        Login loginResponse = new Login();
        loginResponse.setToken(newAccess);
        loginResponse.setRefreshToken(newRefresh);
        loginResponse.setUserId(userId);

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "로그인 체크", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "loginChk")
    public Login loginChk(LoginParams loginParamsRequest) throws Exception {
        return loginService.loginChk(loginParamsRequest);
    }

    @Operation(summary = "중복 로그인 체크", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "dupleLoginChk", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserDuplicateWhether(HttpServletRequest request, LoginParams loginParamsRequest) throws Exception {
        String ipAddr = SecurityUtil.getIpAddress(request);
        loginParamsRequest.setClientIP(ipAddr);
        loginParamsRequest.setBrowser(request.getHeader("User-Agent"));
        
        loginService.getUserDuplicateWhether(loginParamsRequest);
        return loginParamsRequest.getDuplicateYN();
    }

    @Operation(summary = "SMS 인증정보 업데이트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "smsAuth")
    public String checkSMSAuth(LoginParams loginParamsRequest) throws Exception {
        return loginService.checkSMSAuth(loginParamsRequest).getAuthYn();
    }

    @Operation(summary = "2차인증 핸드폰 정보 체크", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "phoneAuth")
    public String userAuthCheck(LoginParams loginParamsRequest) throws Exception {
        return loginService.userAuthCheck(loginParamsRequest).getTel();
    }
    
    @Operation(summary = "사용자 정보", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "userInfo")
    public List<Login> userInfo(LoginParams loginParamsRequest) throws Exception {
        return loginService.userInfo(loginParamsRequest);
    }
    
    @Operation(summary = "SMS인증번호 저장", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "saveAuth")
    public void authNumInsert(LoginParams loginParamsRequest) throws Exception {
        loginService.authNumInsert(loginParamsRequest);
    }
    
    @Operation(summary = "로그인 히스토리", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "loginHis")
    public String setUserLoginHis(LoginParams loginParamsRequest) throws Exception {
        return loginService.setUserLoginHis(loginParamsRequest).getResult();
    }

    @Operation(summary = "로그인 되어 있는 IP 정보 로그아웃", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "logout")
    public void logout(LoginParams loginParamsRequest) throws Exception {
        loginService.logout(loginParamsRequest);
    }

    @Operation(summary = "로그아웃 여부", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "logoutInfo")
    public String getUserLogoutWhether(LoginParams loginParamsRequest) throws Exception {
        loginService.getUserLogoutWhether(loginParamsRequest);
        return loginParamsRequest.getLogoutYN();
    }

    @Operation(summary = "마이페이지 사용자정보 - OTP키 정보", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "otpInfo")
    public Login getOtpKey(LoginParams loginParamsRequest) throws Exception {
        return loginService.getOtpKey(loginParamsRequest);
    }
 
    @Operation(summary = "OTP키 정보 업데이트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "otpInfoUpdate")
    public Login otpKeyUpdate(LoginParams loginParamsRequest) throws Exception {
        return loginService.otpKeyUpdate(loginParamsRequest);
    }

    @Operation(summary = "비밀번호 변경", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "pwChange")
    public String loginPwChange(LoginParams loginParamsRequest) throws Exception {
        return loginService.loginPwChange(loginParamsRequest).getResultYn();
    }         

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }    
}
