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
@Schema(title = "CommonParams", description = "공통 파라미터")
public class CommonParams {
    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "메뉴 고유 번호", description = "")
    private String menuId;

    @Schema(title = "학급", description = "E:초등, K:유아, M:중등, G:공통")
    private String cls;

    @Schema(title = "권한 레벨", description = "")
    private int level;

    @Schema(title = "탑아이디", description = "")
    private String topId;

    @Schema(title = "이름", description = "")
    private String name;
    
    @Schema(title = "비밀번호", description = "")
    private String password;

    @Schema(title = "핸드폰 번호", description = "")
    private String phone;

    @Schema(title = "아이피", description = "")
    private String ip;

    @Schema(title = "로그인 유저 아이디", description = "")
    private String loginUserId;

    @Schema(title = "검색 유형", description = "")
    private String searchMode;

    @Schema(title = "검색어", description = "")
    private String searchText;

    @Schema(title = "잠금 여부", description = "")
    private String lock;

    @Schema(title = "페이지 번호", description = "")
    private int pageNo;

    @Schema(title = "페이지 사이즈", description = "")
    private int pageSize;

    @Schema(title = "정렬 순서", description = "")
    private String sortExpression;

    @Schema(title = "전체 검색 수", description = "")
    private int totalRowsNum;

    @Schema(title = "생성일", description = "")
    private String creDate;

    @Schema(title = "패스워드 업데이트", description = "")
    private String updatePass;

    @Schema(title = "마지막 로그인 시간", description = "")
    private String lastLoginDate;

    @Schema(title = "아이디 블록 여부", description = "")
    private String extraID_Block;

    @Schema(title = "로그인 실패횟수", description = "")
    private int loginFailCnt;
    
    @Schema(title = "전화번호", description = "")
    private String tel;

    @Schema(title = "이메일 주소", description = "")
    private String email;

    @Schema(title = "정렬 번호", description = "")
    private int rowNumber;

    @Schema(title = "부서명", description = "")
    private String roomNm;

    @Schema(title = "팀명", description = "")
    private String teamNm;

    @Schema(title = "파트명", description = "")
    private String partNm;

    @Schema(title = "타입", description = "")
    private String type;

    @Schema(title = "액션 유저 아이디", description = "")
    private String actionUserId;

    @Schema(title = "아이디", description = "")
    private String id;

    @Schema(title = "새로운 아이디", description = "")
    private String newId;

    @Schema(title = "실", description = "")
    private String room;

    @Schema(title = "팀", description = "")
    private String team;

    @Schema(title = "파트", description = "")
    private String part;

    @Schema(title = "flag", description = "")
    private String flag;

    @Schema(title = "권한 타입", description = "")
    private String authType;

    @Schema(title = "등록 유저 아이디", description = "")
    private String regUserId;

    @Schema(title = "리턴 코드", description = "")
    private String returnCode;

    @Schema(title = "인덱스", description = "")
    private int idx;

    @Schema(title = "리턴 값", description = "")
    private String returnValue;

    @Schema(title = "새 메뉴 아이디", description = "")
    private int newMenuId;

    @Schema(title = "상위 메뉴 아이디", description = "")
    private int upperMenuId;

    @Schema(title = "메뉴명", description = "")
    private String menuName;

    @Schema(title = "권한 복사 여부", description = "")
    private String cloneAuthYn;

}
