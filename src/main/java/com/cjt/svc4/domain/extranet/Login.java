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
@Schema(title = "Login", description = "로그인 객체")
public class Login {

    @Schema(title = "로그인 성공 여부", description = "")
    private String loginYn;
    
    @Schema(title = "아이피 주소", description = "")
    private String clientIP;

    @Schema(title = "사용자 아이디", description = "")
    private String userName;

    @Schema(title = "사용 브라우저", description = "")
    private String browser;

    @Schema(title = "중복 로그인 여부", description = "Y: 중복로그인, N:중복로그인 아님")
    private String duplicateYN;

    @Schema(title = "인증 성공 여부", description = "")
    private String authYn;

    @Schema(title = "사용자 아이디", description = "")
    private String userId;

    @Schema(title = "이름", description = "")
    private String name;

    @Schema(title = "학급", description = "")
    private String cls;
    
    @Schema(title = "실", description = "")
    private String room;
    
    @Schema(title = "핸드폰 정보", description = "")
    private String tel;
    
    @Schema(title = "팀", description = "")
    private String team;
    
    @Schema(title = "파트", description = "")
    private String part;

    @Schema(title = "결과값", description = "")
    private String result;
    
    @Schema(title = "타입", description = "")
    private String otpKey;
    
    @Schema(title = "이메일 주소", description = "")
    private String email;

    @Schema(title = "결과값YN", description = "")
    private String resultYn;
    
    @Schema(title = "JWT AccessToken", description = "")
    private String token;

    @Schema(title = "JWT RefreshToken", description = "")
    private String refreshToken;
}
