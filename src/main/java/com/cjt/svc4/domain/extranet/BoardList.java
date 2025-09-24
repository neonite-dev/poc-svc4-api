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
@Schema(title = "BoardList", description = "게시판 객체")
public class BoardList {
    @Schema(title = "파트 정보", description = "")
    private String departmentName;

    @Schema(title = "총 게시글수", description = "")
    private int totalCnt;

    @Schema(title = "답변글 수", description = "")
    private int replyCount;

    @Schema(title = "파일 수", description = "")
    private int fileCount;

    @Schema(title = "작성자명", description = "")
    private String name;

    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "예약날짜", description = "")
    private String reserveDate;

    @Schema(title = "예약시간", description = "")
    private String reserveTime;
}