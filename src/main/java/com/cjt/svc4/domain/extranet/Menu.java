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
@Schema(title = "Menu", description = "메뉴 객체")
public class Menu {
    @Schema(title = "메뉴 아이디", description = "")
    private int menuId;

    @Schema(title = "메뉴명", description = "")
    private String menuName;

    @Schema(title = "상위 메뉴 아이디", description = "")
    private String upperMenuId;

    @Schema(title = "사용여부", description = "")
    private String use_Yn;

    @Schema(title = "타입", description = "")
    private String type;

    @Schema(title = "정렬", description = "")
    private int sort;

    @Schema(title = "학급", description = "")
    private String cls;

    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "인덱스", description = "")
    private int idx;

    @Schema(title = "날짜", description = "")
    private String indate;

    @Schema(title = "menuChk", description = "TB_Filter 테이블의 MenuId")
    private String menuChk;

    @Schema(title = "정렬번호", description = "")
    private String sortNum;

    @Schema(title = "등록일", description = "")
    private String regDate;

    @Schema(title = "말머리", description = "")
    private String headTitle;

}
