package com.cjt.svc4.domain.extranet;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(title = "Common", description = "공통 객체")
public class Common {
    @Schema(title = "학급", description = "E:초등, K:유아, M:중등, G:공통")
    private String cls;

    @Schema(title = "권한 레벨", description = "")
    private int level;

    @Schema(title = "탑아이디", description = "")
    private String topId;

    @Schema(title = "아이디", description = "")
    private String id;

    @Schema(title = "이름", description = "")
    private String name;

    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "실", description = "")
    private String room;

    @Schema(title = "팀", description = "")
    private String team;

    @Schema(title = "파트", description = "")
    private String part;

    @Schema(title = "부서코드", description = "")
    private String code;

    @Schema(title = "파트", description = "")
    private String type;

    @Schema(title = "상위 소속", description = "")
    private String parent;

    @Schema(title = "권한타입", description = "")
    private String authType;

    @Schema(title = "카운트", description = "")
    private int cnt;

    @Schema(title = "로우 넘버", description = "")
    private int rowNumber;

    @Schema(title = "등록자", description = "")
    private String regUserId;

    @Schema(title = "등록일", description = "")
    private String regDate;

    @Schema(title = "전체 검색 수", description = "")
    private int totalRowsNum;

}
