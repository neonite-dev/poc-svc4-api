package com.cjt.svc4.domain.extranet;

import java.util.Date;

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
@Schema(title = "ReaderList", description = "열람자 리스트 객체")
public class ReaderList {
    @Schema(title = "게시판 고유 번호", description = "")
    private String boardId;

    @Schema(title = "열람자", description = "")
    private String reader;

    @Schema(title = "열람횟수", description = "")
    private int readCount;

    @Schema(title = "열람시간", description = "")
    private Date readTime;

    @Schema(title = "열람자명", description = "")
    private String reader_Name;
}
