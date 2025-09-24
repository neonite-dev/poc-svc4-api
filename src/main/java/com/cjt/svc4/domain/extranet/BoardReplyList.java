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
@Schema(title = "BoardReplyList", description = "게시판 답글 데이터 저장 객체")
public class BoardReplyList {
    @Schema(title = "답글 고유 번호", description = "")
    private String replyId;

    @Schema(title = "게시판 고유 번호", description = "")
    private String boardId;

    @Schema(title = "작성자", description = "")
    private String writer;

    @Schema(title = "내용", description = "")
    private String content;

    @Schema(title = "작성일", description = "")
    private Date regdate;

    @Schema(title = "작성자명", description = "")
    private String writerName;

    @Schema(title = "수정자명", description = "")
    private String rwriter;
    
    @Schema(title = "총 좋아요수", description = "")
    private int totallikecnt;

    @Schema(title = "Ismylike", description = "")
    private int ismylike;

    @Schema(title = "권한여부", description = "")
    private String authYN;

}
