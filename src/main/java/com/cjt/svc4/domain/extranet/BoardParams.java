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
@Schema(title = "BoardParams", description = "게시판 파라미터 객체")
public class BoardParams {

    @Schema(title = "검색 방법", description = "")
    private String searchMode;

    @Schema(title = "메뉴 아이디", description = "")
    private int menuId;

    @Schema(title = "메뉴 이름", description = "")
    private String menuName;

    @Schema(title = "검색어", description = "")
    private String searchText;

    @Schema(title = "상태", description = "")
    private String state;

    @Schema(title = "작성자", description = "")
    private String writer;

    @Schema(title = "최대 게시물 목록", description = "")
    private int size;

    @Schema(title = "번호", description = "")
    private int no;

    @Schema(title = "비밀글", description = "")
    private String secret;

    @Schema(title = "상단 제목", description = "")
    private String headTitle;

    @Schema(title = "게시글 가림 여부", description = "")
    private String blindYN;

    @Schema(title = "학급", description = "")
    private String cls;

    @Schema(title = "검색 유형", description = "")
    private String searchType;

    @Schema(title = "예약 날짜", description = "")
    private String reserveDate;

    @Schema(title = "예약 정렬", description = "")
    private String reserveSort;

    @Schema(title = "예약 시간", description = "")
    private String reserveTime;

    @Schema(title = "교사 아이디", description = "")
    private String teacherId;

    @Schema(title = "게시판 고유 아이디", description = "")
    private String boardId;

    @Schema(title = "아이디", description = "")
    private String userId;

    @Schema(title = "열람자", description = "")
    private String reader;

    @Schema(title = "파일고유번호", description = "")
    private String fileId;

    @Schema(title = "메뉴 Depth", description = "")
    private String strMenuDepth;

    @Schema(title = "메뉴 아이디", description = "")
    private String strMenuId;

    @Schema(title = "권한자", description = "")
    private String authYN;

    @Schema(title = "탑 게시글 갯수", description = "")
    private int topCnt;

    @Schema(title = "유형", description = "N: 공지사항,  M: 월상담전략")
    private String type;

    @Schema(title = "탑글 순서", description = "")
    private int topOrder;

    @Schema(title = "글 제목", description = "")
    private String title;

    @Schema(title = "글 제목 색상", description = "")
    private String color;

    @Schema(title = "댓글 좋아요 사용여부", description = "")
    private String useReplyLike;

    @Schema(title = "내용", description = "")
    private String content;

    @Schema(title = "구분값1", description = "")
    private String gubun1;

    @Schema(title = "구분값2", description = "")
    private String gubun2;

    @Schema(title = "답글 고유 번호", description = "")
    private String replyId;

    @Schema(title = "댓글 좋아요 여부", description = "")
    private int likeYn;

    @Schema(title = "인덱스", description = "")
    private int idx;

    @Schema(title = "주소", description = "")
    private String src;

    @Schema(title = "이관 고유번호", description = "게시판 이관시 새로 생성된 글의 새 고유번호")
    private String reBoardId;

    @Schema(title = "", description = "")
    private String reNewBoardId;

    @Schema(title = "아이피 정보", description = "")
    private String remoteIP;

    @Schema(title = "파일명", description = "")
    private String fileName;

    @Schema(title = "새파일명", description = "")
    private String newFileName;

    @Schema(title = "파일용량", description = "")
    private long fileBytes;

    @Schema(title = "파일주소", description = "")
    private String filePath;

    @Schema(title = "OUTPUT INDEX", description = "프로시저 실행후 OUTPUT 인덱스번호")
    private int outIdx;

    @Schema(title = "", description = "")
    private String mode;

    @Schema(title = "상위 메뉴 아이디", description = "")
    private String upperMenuId;

    @Schema(title = "메뉴 순서", description = "")
    private int sort;

    @Schema(title = "변경 날짜", description = "")
    private String changeDate;

    @Schema(title = "변경 시간", description = "")
    private String changeTime;

    @Schema(title = "변경된 로우 수", description = "")
    private int affectedRow;
}
