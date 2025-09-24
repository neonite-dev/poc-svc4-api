package com.cjt.svc4.domain.extranet;

import java.util.Date;

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
@Schema(title = "Board", description = "게시판 리스트 객체")
public class Board extends BoardList {
    @Schema(title = "정렬번호", description = "페이지 번호 정렬")
    private int rowNum;

    @Schema(title = "글 고유번호", description = "")
    private String boardId;

    @Schema(title = "메뉴 아이디", description = "")
    private int menuId;

    @Schema(title = "상위메뉴아이디", description = "")
    private int upperMenuId;

    @Schema(title = "유형", description = "")
    private String type;

    @Schema(title = "번호", description = "")
    private int num;

    @Schema(title = "작성자", description = "")
    private String writer;

    @Schema(title = "제목", description = "")
    private String title;

    @Schema(title = "조회수", description = "")
    private int readCount;

    @Schema(title = "작성일", description = "")
    private Date regDate;

    @Schema(title = "", description = "")
    private int refer;

    @Schema(title = "답글순서", description = "0:원글, 1~ 답글 순서")
    private int step;

    @Schema(title = "", description = "")
    private int depth;

    @Schema(title = "삭제여부", description = "")
    private String delflag;

    @Schema(title = "내용", description = "")
    private String content;

    @Schema(title = "상태", description = "")
    private String state;

    @Schema(title = "구분값1", description = "")
    private String gubun1;

    @Schema(title = "구분값2", description = "")
    private String gubun2;

    @Schema(title = "탑글 순서", description = "")
    private int topOrder;

    @Schema(title = "상단 제목", description = "")
    private String headTitle;

    @Schema(title = "아이피주소", description = "")
    private String remoteIP;

    @Schema(title = "색상", description = "")
    private String color;

    @Schema(title = "임시", description = "")
    private int temp;

    @Schema(title = "댓글 좋아요 사용여부", description = "")
    private int useReplyLike;

    //이관관련
    @Schema(title = "메뉴 이름", description = "")    
    private String menuName;

    //아이콘 리스트
    @Schema(title = "인덱스 번호", description = "")
    private int idx;

    @Schema(title = "아이콘 주소", description = "")
    private String src;

    @Schema(title = "등록 아이디", description = "")
    private String regUserId;

    @Schema(title = "예약일", description = "")
    private String reserveDate;

    @Schema(title = "예약시간", description = "")
    private String reserveTime;

    @Schema(title = "새글 갯수", description = "")    
    private int newCount;

    @Schema(title = "부서명", description = "")    
    private String departmentName;
    
    @Schema(title = "학급", description = "")    
    private String cls;

    @Schema(title = "사용여부", description = "")    
    private String use_Yn;

    @Schema(title = "순서", description = "")    
    private int sort;

    @Schema(title = "정렬순서", description = "")    
    private String orderSort;

    @Schema(title = "메뉴 depth", description = "")    
    private String menuDepth;

    @Schema(title = "depth 이름", description = "")    
    private String depth_Fullname;
    
    @Schema(title = "이름", description = "")    
    private String writerName;
}
