package com.cjt.svc4.api.controller.extranet;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cjt.svc4.domain.extranet.Board;
import com.cjt.svc4.domain.extranet.BoardFileList;
import com.cjt.svc4.domain.extranet.BoardParams;
import com.cjt.svc4.domain.extranet.BoardReplyList;
import com.cjt.svc4.domain.extranet.Menu;
import com.cjt.svc4.domain.extranet.ReaderList;
import com.cjt.svc4.service.extranet.BoardService;
import com.cjt.svc4.utils.FileHandler;
import com.cjt.svc4.utils.SecurityUtil;
import java.io.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Board Controller API", description = "게시판 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/extra/board")
public class BoardController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
    @Autowired
    private final BoardService boardService;

    @Operation(summary = "게시판 리스트 조회", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "list")
    public List<Board> getBoardList(BoardParams boardParamsRequest) throws Exception {

        if (boardParamsRequest.getHeadTitle().equals("말머리선택")) {
            boardParamsRequest.setHeadTitle("");
        }

        return boardService.getBoardList(boardParamsRequest);
    }

    @Operation(summary = "게시물 내용 보기", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "view")
    public List<Board> getBoardView(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardView(boardParamsRequest);
    }

    @Operation(summary = "열람자 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "readerList")
    public List<ReaderList> getReaderList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getReaderList(boardParamsRequest);
    }

    @Operation(summary = "첨부파일 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "fileList")
    public List<BoardFileList> getBoardFileList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardFileList(boardParamsRequest);
    }

    @Operation(summary = "첨부파일 정보", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "files")
    public List<BoardFileList> getBoardFile(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardFile(boardParamsRequest);
    }

    @Operation(summary = "답글 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "replyList")
    public List<BoardReplyList> getBoardReplyList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardReplyList(boardParamsRequest);
    }

    @Operation(summary = "게시글 이관 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "moveList")
    public List<Board> getBoardMoveList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardMoveList(boardParamsRequest);
    }

    @Operation(summary = "사용자별 게시글 읽은 횟수 카운트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "boardReadAlready")
    public List<Board> boardReadAlready(BoardParams boardParamsRequest) throws Exception {
        return boardService.boardReadAlready(boardParamsRequest);
    }

    @Operation(summary = "글 작성시 말머리 셀렉트 정보", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getHeadTitle")
    public List<Menu> getHeadTitle(BoardParams boardParamsRequest) throws Exception {

        if (boardParamsRequest.getHeadTitle() != null) {
            if (boardParamsRequest.getHeadTitle().equals("말머리선택")) {
                boardParamsRequest.setHeadTitle("");
            }
        }

        return boardService.getHeadTitle(boardParamsRequest);
    }

    @Operation(summary = "예약가능시간", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getReserveTime")
    public List<Board> getReservePossibleTimeList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getReservePossibleTimeList(boardParamsRequest);
    }

    @Operation(summary = "댓글 좋아요 기능 권한", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "likeThisAuth")
    public ResponseEntity<Map<String, Object>> likeThisAuth(BoardParams boardParamsRequest) throws Exception {
        boolean auth = false;
    
        boardService.likeThisAuth(boardParamsRequest);

        if (boardParamsRequest.getAuthYN().equals("Y")) {
            auth = true;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("auth", auth);
        data.put("success", true);

        return ResponseEntity.ok(data);
    }

    @Operation(summary = "상단 현재 메뉴 표시", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getMenuDepth")
    public List<Board> getMenuDepth(BoardParams boardParamsRequest) throws Exception {
        return boardService.getMenuDepth(boardParamsRequest);
    }

    @Operation(summary = "새글 카운트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getBoardNewCount")
    public List<Board> getBoardNewCount(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardNewCount(boardParamsRequest);
    }

    @Operation(summary = "메인 공지사항 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "getMainBoardList")
    public List<Board> getMainBoardList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getMainBoardList(boardParamsRequest);
    }

    @Operation(summary = "조회수 업데이트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateReadCount")
    public void IncreaseReadCount(BoardParams boardParamsRequest) throws Exception {
        boardService.increaseReadCount(boardParamsRequest);
    }

    @Operation(summary = "게시글 수정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateBoard")
    public ResponseEntity<Map<String, Object>> boardUpdate(BoardParams boardParamsRequest) throws Exception {

        boardService.boardUpdate(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("boardId", boardParamsRequest.getBoardId());
        data.put("menuId", boardParamsRequest.getMenuId());
        data.put("success", true);

        return ResponseEntity.ok(data);
    }

    @Operation(summary = "메뉴이름변경 (게시판이름)", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateBoardName")
    public void setChangeBoardName(BoardParams boardParamsRequest) throws Exception {
        boardService.setChangeBoardName(boardParamsRequest);
    }

    @Operation(summary = "메뉴 그룹 이름 변경", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateMenuGroup")
    public void setChangeMenuGroup(BoardParams boardParamsRequest) throws Exception {
        boardService.setChangeMenuGroup(boardParamsRequest);
    }

    @Operation(summary = "댓글 좋아요 기능", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setlike")
    public void setlike(BoardParams boardParamsRequest) throws Exception {
        boardService.setLike(boardParamsRequest);
    }

    @Operation(summary = "아이콘 삽입 기능", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "addIcon")
    public ResponseEntity<Map<String, Object>> boardFileInsert(@RequestParam("loginId") String loginId,
            @RequestPart("attachedFileIcon") MultipartFile attachedFileIcon) throws Exception {

        BoardParams boardParamsRequest = new BoardParams();
        if (!attachedFileIcon.isEmpty()) {
            String savedPath = FileHandler.saveFile(attachedFileIcon, "image", "");
            boardParamsRequest.setSrc(savedPath);
            boardParamsRequest.setUserId(loginId);
            boardService.boardIconInsert(boardParamsRequest);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "아이콘 리스트", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "iconList")
    public List<Board> getBoardIconList(BoardParams boardParamsRequest) throws Exception {
        return boardService.getBoardIconList(boardParamsRequest);
    }

    @Operation(summary = "말머리 추가,수정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateHeadTitle")
    public int getHeadTitleInsertUpdate(BoardParams boardParamsRequest) throws Exception {
        boardService.getHeadTitleInsertUpdate(boardParamsRequest);
        return boardParamsRequest.getIdx();
    }

    @Operation(summary = "관리자 공지사항 체크 해제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "unCheckTop")
    public void unCheckTop(BoardParams boardParamsRequest) throws Exception {
        boardService.unCheckTop(boardParamsRequest);
    }

    @Operation(summary = "게시글 이동", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "moveBoard")
    public void boardMove(BoardParams boardParamsRequest) throws Exception {
        boardService.boardMove(boardParamsRequest);
    }

    @Operation(summary = "게시판 순서 변경", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setChangeOrder")
    public void setChangeOrder(BoardParams boardParamsRequest) throws Exception {
        boardService.setChangeOrder(boardParamsRequest);
    }

    @Operation(summary = "새 답글", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "insertReply")
    public ResponseEntity<Map<String, Object>> boardReply(BoardParams boardParamsRequest, HttpServletRequest request)
            throws Exception {

        String generateReBoardId = CommonUtils.generateId(boardParamsRequest.getWriter());
        String userIP = SecurityUtil.getIpAddress(request);

        boardParamsRequest.setReBoardId(generateReBoardId);
        boardParamsRequest.setRemoteIP(userIP);

        boardService.boardReply(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("reBoardId", generateReBoardId);
        data.put("menuId", boardParamsRequest.getMenuId());
        data.put("success", true);

        return ResponseEntity.ok(data);
    }

    @Operation(summary = "새 게시글 작성", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "insertBoard")
    public ResponseEntity<Map<String, Object>> boardInsert(BoardParams boardParamsRequest, HttpServletRequest request) throws Exception {

        String generateBoardId = CommonUtils.generateId(boardParamsRequest.getWriter());
        String userIP = SecurityUtil.getIpAddress(request);

        boardParamsRequest.setBoardId(generateBoardId);
        boardParamsRequest.setRemoteIP(userIP);
      
        boardService.boardInsert(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("boardId", generateBoardId);
        data.put("menuId", boardParamsRequest.getMenuId());
        data.put("success", true);

       return ResponseEntity.ok(data);
    }

    @Operation(summary = "새 파일첨부", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "insertBoardFile")
    public void boardFileInsert(@RequestParam("boardId") String boardId, @RequestParam("loginId") String loginId,
            @RequestPart("attachedFile") MultipartFile[] attachedFile, HttpServletRequest request) throws Exception {

        BoardParams boardParamsRequest = new BoardParams();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String formattedDate = formatter.format(today);

        if (attachedFile.length > 0) {
            for (MultipartFile file : attachedFile) {
                String newEditFileId = CommonUtils.generateId(loginId);

                boardParamsRequest.setFileName(file.getOriginalFilename());
                boardParamsRequest.setFileBytes(file.getSize());
                boardParamsRequest.setNewFileName(newEditFileId + ".HBStudy");
                boardParamsRequest.setBoardId(boardId);
                boardParamsRequest.setFileId(newEditFileId);
                boardParamsRequest.setFilePath(loginId + "/" + formattedDate);

                boardService.boardFileInsert(boardParamsRequest);

                String savedPath = FileHandler.saveFile(file, "file", boardParamsRequest.getFilePath());
            }
        }
    }

    @Operation(summary = "파일 다운로드", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "downFile")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath)
            throws Exception {
        String path = "webdata/board/" + filePath;

        File file = new File(path, fileName);

        if (file.exists()) {
            String encodedName =  URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedName);

            return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            String errorMessage = "File does not exist";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(errorMessage.getBytes());
            InputStreamResource resource = new InputStreamResource(inputStream);
            return ResponseEntity.badRequest().body(resource);
        }
    }

    @Operation(summary = "새 댓글", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "addComment")
    public ResponseEntity<Map<String, Object>> replyRegister(BoardParams boardParamsRequest) throws Exception {

        String generateReplyId = CommonUtils.generateId(boardParamsRequest.getWriter());

        boardParamsRequest.setReplyId(generateReplyId);
      
        boardService.replyRegister(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);

       return ResponseEntity.ok(data);
    }
    
    @Operation(summary = "게시판 권한 설정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "setBoardAuth")
    public void setBoardAuthUser(BoardParams boardParamsRequest) throws Exception {
        boardService.setBoardAuthUser(boardParamsRequest);
    }

    @Operation(summary = "댓글 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "delReply")
    public ResponseEntity<Map<String, Object>> replyDelete(BoardParams boardParamsRequest) throws Exception {

        boardService.replyDelete(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);

       return ResponseEntity.ok(data);
    }

    @Operation(summary = "첨부파일 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "delFile")
    public ResponseEntity<Map<String, Object>> boardFileDelete(BoardParams boardParamsRequest) throws Exception {
        boardService.boardFileDelete(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "아이콘 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "delIcon")
    public ResponseEntity<Map<String, Object>> boardIconDelete(BoardParams boardParamsRequest) throws Exception {
        
       boardService.boardIconDelete(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);

       return ResponseEntity.ok(data);
    }

    @Operation(summary = "게시판 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "delBoard")
    public ResponseEntity<Map<String, Object>> boardDelete(BoardParams boardParamsRequest) throws Exception {
        
       boardService.boardDelete(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);

       return ResponseEntity.ok(data);
    }

    @Operation(summary = "게시판 삭제 (관리자용)", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "adminDelBoard")
    public void adminBoardDelete(BoardParams boardParamsRequest) throws Exception {
        boardService.adminBoardDelete(boardParamsRequest);
    }

    @Operation(summary = "예약시간 설정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "insertReserve")
    public ResponseEntity<Map<String, Object>> reserveInsert(BoardParams boardParamsRequest) throws Exception {
        boardService.reserveInsert(boardParamsRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("idx", boardParamsRequest.getIdx());
        
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "예약시간 수정", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "updateReserve")
    public void reserveUpdate(BoardParams boardParamsRequest) throws Exception {

        boardParamsRequest.setChangeDate(boardParamsRequest.getReserveDate());
        boardParamsRequest.setChangeTime(boardParamsRequest.getReserveTime());
        boardService.reserveUpdate(boardParamsRequest);
    }
    
    @Operation(summary = "예약 정보 삭제", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "delReserve")
    public int reserveDelete(BoardParams boardParamsRequest) throws Exception {
        boardService.reserveDelete(boardParamsRequest);
        
        return boardParamsRequest.getAffectedRow();
    }

    @Operation(summary = "Ckeditor5 이미지파일 업로드", responses = @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true))))
    @PostMapping(value = "imageUpload")
    public ResponseEntity<?> imageUpload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("upload") MultipartFile multipartFilefile) throws Exception {

        String url = FileHandler.saveFile(multipartFilefile, "image","");
        return ResponseEntity.ok().body(Map.of("url", url));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

    public class CommonUtils {
        public static String generateId(String userId) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String fullDate = LocalDateTime.now().format(dateFormat);
        String generatedId = (userId + fullDate);
        return generatedId;
        }
    }

}
