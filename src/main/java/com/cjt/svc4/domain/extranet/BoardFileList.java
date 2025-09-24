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
@Schema(title = "BoardFileList", description = "파일 리스트 모델")
public class BoardFileList {
    @Schema(title="파일 고유 번호", description = "")
    private String fileid;

    @Schema(title="파일 이름", description = "")
    private String filename;

    @Schema(title="새로 부여된 파일 이름", description = "")
    private String newfilename;

    @Schema(title="파일 Path", description = "")
    private String filepath;

    @Schema(title="바이트정보", description = "")
    private String filebytes;

    @Schema(title="총 바이트", description = "")
    private String filetotalbytes;
    
    @Schema(title="게시판 고유 번호", description = "")
    private String boardid;

}
