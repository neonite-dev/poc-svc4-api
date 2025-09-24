package com.cjt.svc4.mapper.extranet;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cjt.svc4.domain.extranet.Board;
import com.cjt.svc4.domain.extranet.BoardFileList;
import com.cjt.svc4.domain.extranet.BoardParams;
import com.cjt.svc4.domain.extranet.BoardReplyList;
import com.cjt.svc4.domain.extranet.Menu;
import com.cjt.svc4.domain.extranet.ReaderList;

@Repository
@Mapper
public interface BoardMapper {


  /* 
   * 게시판 리스트
   * SearchMode, MenuId, SearchText, State, Writer, Size, No, Secret, HeadTitle, BlindYN, Cls, SearchType, ReserveDate, ReserveSort 
   */
  List<Board> getBoardList(BoardParams boardParamsRequest);

  /* 
  * 게시물 내용 보기
  * BoardId, UserId
  */
  List<Board> getBoardView(BoardParams boardParamsRequest);

  /* 
  * 열람자 리스트
  * BoardId, Reader
  */
  List<ReaderList> getReaderList(BoardParams boardParamsRequest);

  /* 
  * 첨부파일 리스트
  * BoardId
  */  
  List<BoardFileList> getBoardFileList(BoardParams boardParamsRequest);

  /* 
  * 첨부파일 정보
  * BoardId
  */
  List<BoardFileList> getBoardFile(BoardParams boardParamsRequest);

  /* 
  * 답글 리스트
  * BoardId, UserId
  */
  List<BoardReplyList> getBoardReplyList(BoardParams boardParamsRequest);

  /* 
  * 게시글 이관 리스트
  * MenuId, Cls, UserId
  */
  List<Board> getBoardMoveList(BoardParams boardParamsRequest);

  /* 
  * 아이콘 선택 리스트
  */
  List<Board> getBoardIconList(BoardParams boardParamsRequest);

  /* 
  * 사용자별 게시글 읽은 횟수 카운트
  * BoardId, Reader
  */
  List<Board> boardReadAlready(BoardParams boardParamsRequest);
    
  /* 
  * 말머리
  * MenuId
  */
  List<Menu> getHeadTitle(BoardParams boardParamsRequest);
    
  /* 
  * 예약가능시간
  * ReserveDate
  */
  List<Board> getReservePossibleTimeList(BoardParams boardParamsRequest);

  /* 
  * 댓글 좋아요 기능 권한
  * UserId, Cls, AuthYN
  */
  BoardParams likeThisAuth(BoardParams boardParamsRequest);
    
  /* 
  * 상담 현재 메뉴 표시
  * MenuId
  */
  List<Board> getMenuDepth(BoardParams boardParamsRequest);

  /* 
  * 새글 카운트
  */
  List<Board> getBoardNewCount(BoardParams boardParamsRequest);

  /* 
  * 메인 공지사항 리스트
  * TopCnt, UserId, Type
  */
  List<Board> getMainBoardList(BoardParams boardParamsRequest);
    
  /* 
  * 조회수 올림
  * BoardId
  */
  String increaseReadCount(BoardParams boardParamsRequest);
    
  /* 
  * 게시글 수정
  * BoardId, Title, Color, Content, TopOrder, Gubun1, Gubun2, HeadTitle, UseReplyLike, UserId, Cls
  */
  void boardUpdate(BoardParams boardParamsRequest);

  /* 
  * 메뉴이름변경 (게시판이름)
  * MenuId, MenuName
  */
  void setChangeBoardName(BoardParams boardParamsRequest);
    
  /* 
  * 메뉴 그룹 이름 변경
  * MenuId, MenuName
  */
  void setChangeMenuGroup(BoardParams boardParamsRequest);

  /* 
  * 댓글 좋아요 기능
  * ReplyId, UserId, LikeYN
  */
  void setLike(BoardParams boardParamsRequest);
  
  /* 
  * 말머리 추가,수정
  * Idx, MenuId, HeadTitle, Mode(I:삽입, M:수정, D:삭제), OutIdx
  */
  BoardParams getHeadTitleInsertUpdate(BoardParams boardParamsRequest);
    
  /* 
  * 예약 시간 변경
  * ChangeDate, ChangeTime, TeacherId, BoardId
  */
  void reserveUpdate(BoardParams boardParamsRequest);
    
  /* 
  * 관리자 공지사항 체크 해제
  * BoardId
  */
  void unCheckTop(BoardParams boardParamsRequest);
    
  /* 
  * 게시글 이동
  * MenuId, BoardId, ReBoardId, UserId
  */
  void boardMove(BoardParams boardParamsRequest);

  /* 
  * 게시판 순서 변경
  * MenuId, UpperMenuId, Sort, Cls
  */
  void setChangeOrder(BoardParams boardParamsRequest);

  /* 
  * 아이콘 삽입 기능
  * UserId, Src, Idx
  */
  BoardParams boardIconInsert(BoardParams boardParamsRequest);

  /* 
  * 새 게시글 작성
  * MenuId, BoardId, ReBoardId, ReNewBoardId, Writer, Title, Color, Content, State, TopOrder, Gubun1, Gubun2, HeadTitle, UseReplyLike, RemoteIP
  */
  void boardInsert(BoardParams boardParamsRequest);
  
  /* 
  * 새 파일첨부
  * FileId, BoardId, FileName, NewFileName, FileBytes, FilePath
  */
  void boardFileInsert(BoardParams boardParamsRequest);
  
  /* 
  * 새 댓글
  * ReplyId, BoardId, Writer, Content
  */
  void replyRegister(BoardParams boardParamsRequest);

  /* 
  * 새 답글
  * MenuId, BoardId, ReBoardId, Writer, Title, Color, Content, State, HeadTitle, RemoteIP
  */
  void boardReply(BoardParams boardParamsRequest);

  /* 
  * 게시판 사용자 권한 설정
  * UserId, MenuId
  */
  void setBoardAuthUser(BoardParams boardParamsRequest);

  /* 
  * 댓글 삭제
  * ReplyId
  */
  void replyDelete(BoardParams boardParamsRequest);

  /* 
  * 첨부파일 삭제
  * FileId
  */
  void boardFileDelete(BoardParams boardParamsRequest);

  /* 
  * 게시판 아이콘 삭제
  * Idx
  */
  void boardIconDelete(BoardParams boardParamsRequest);

  /* 
  * 게시글 삭제
  * MenuId, BoardId, UserId, Cls
  */
  void boardDelete(BoardParams boardParamsRequest);

  /* 
  * 관리자 계정으로 게시글 삭제
  * MenuId, BoardId, UserId
  */
  void adminBoardDelete(BoardParams boardParamsRequest);
  
  /* 
  * 교사원격관리 예약정보 인서트
  * ReserveDate, ReserveTime, TeacherId, BoardId, Idx
  */
  BoardParams reserveInsert(BoardParams boardParamsRequest);

  /* 
  * 교사원격관리 예약정보 삭제
  * ReserveDate, ReserveTime, TeacherId, BoardId, AffectedRow
  */
  BoardParams reserveDelete(BoardParams boardParamsRequest);
  
}

