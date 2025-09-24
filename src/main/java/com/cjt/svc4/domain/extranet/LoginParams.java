package com.cjt.svc4.domain.extranet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(title = "LoginParams", description = "로그인 파라미터")
public class LoginParams {
    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "패스워드", description = "")
    private String password;

    @Schema(title = "아이피 정보", description = "")
    private String userIP;

    @Schema(title = "아이피 주소", description = "")
    private String clientIP;

    @Schema(title = "사용자 아이디", description = "")
    private String userName;

    @Schema(title = "사용 브라우저", description = "")
    private String browser;

    @Schema(title = "중복 로그인 여부", description = "Y: 중복로그인, N:중복로그인 아님")
    private String duplicateYN;

    @Schema(title = "인증번호", description = "")
    private String authNum;

    @Schema(title = "타입", description = "")
    private String type;

    @Schema(title = "로그아웃 여부", description = "Y:로그아웃, N:로그인")
    private String logoutYN;

    @Schema(title = "OTP키", description = "")
    private String otpKey;

}
