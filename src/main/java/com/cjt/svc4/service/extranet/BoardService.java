package com.cjt.svc4.service.extranet;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjt.svc4.domain.extranet.Board;
import com.cjt.svc4.domain.extranet.BoardFileList;
import com.cjt.svc4.domain.extranet.BoardParams;
import com.cjt.svc4.domain.extranet.BoardReplyList;
import com.cjt.svc4.domain.extranet.Menu;
import com.cjt.svc4.domain.extranet.ReaderList;
import com.cjt.svc4.mapper.extranet.BoardMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardMapper boardMapper;

    @Transactional(readOnly = true)
    public List<Board> getBoardList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardView(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardView(boardParamsRequest);
    }

    @Transactional
    public List<ReaderList> getReaderList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getReaderList(boardParamsRequest);
    }
     @Transactional(readOnly = true)
     public List<BoardFileList> getBoardFileList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardFileList(boardParamsRequest);
     }

     @Transactional(readOnly = true)
     public List<BoardFileList> getBoardFile(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardFile(boardParamsRequest);
     }

    @Transactional(readOnly = true)
    public List<BoardReplyList> getBoardReplyList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardReplyList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardMoveList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardMoveList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardIconList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardIconList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> boardReadAlready(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.boardReadAlready(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Menu> getHeadTitle(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getHeadTitle(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getReservePossibleTimeList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getReservePossibleTimeList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getMenuDepth(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getMenuDepth(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public BoardParams likeThisAuth(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.likeThisAuth(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getMainBoardList(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getMainBoardList(boardParamsRequest);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardNewCount(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getBoardNewCount(boardParamsRequest);
    }

    @Transactional
    public String increaseReadCount(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.increaseReadCount(boardParamsRequest);
    }

    @Transactional
    public void boardUpdate(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardUpdate(boardParamsRequest);
    }

    @Transactional
    public void setChangeBoardName(BoardParams boardParamsRequest) throws Exception {
        boardMapper.setChangeBoardName(boardParamsRequest);
    }

    @Transactional
    public void setChangeMenuGroup(BoardParams boardParamsRequest) throws Exception {
        boardMapper.setChangeMenuGroup(boardParamsRequest);
    }

    @Transactional
    public void setLike(BoardParams boardParamsRequest) throws Exception {
        boardMapper.setLike(boardParamsRequest);
    }

    @Transactional
    public BoardParams getHeadTitleInsertUpdate(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.getHeadTitleInsertUpdate(boardParamsRequest);
    }

    @Transactional
    public void unCheckTop(BoardParams boardParamsRequest) throws Exception {
        boardMapper.unCheckTop(boardParamsRequest);
    }
    
    @Transactional
    public void boardMove(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardMove(boardParamsRequest);
    }

    @Transactional
    public void setChangeOrder(BoardParams boardParamsRequest) throws Exception {
        boardMapper.setChangeOrder(boardParamsRequest);
    }
    
    @Transactional
    public BoardParams boardIconInsert(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.boardIconInsert(boardParamsRequest);
    }

    @Transactional
    public void boardInsert(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardInsert(boardParamsRequest);
    }
    
    @Transactional
    public void boardFileInsert(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardFileInsert(boardParamsRequest);
    }

    @Transactional
    public void replyRegister(BoardParams boardParamsRequest) throws Exception {
        boardMapper.replyRegister(boardParamsRequest);
    }

    @Transactional
    public void boardReply(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardReply(boardParamsRequest);
    }

   @Transactional
    public void setBoardAuthUser(BoardParams boardParamsRequest) throws Exception {
        boardMapper.setBoardAuthUser(boardParamsRequest);
    }

   @Transactional
    public void replyDelete(BoardParams boardParamsRequest) throws Exception {
        boardMapper.replyDelete(boardParamsRequest);
    }

   @Transactional
    public void boardFileDelete(BoardParams boardParamsRequest) throws Exception {
        boardMapper.boardFileDelete(boardParamsRequest);
    }    

   @Transactional
   public void boardIconDelete(BoardParams boardParamsRequest) throws Exception {
       boardMapper.boardIconDelete(boardParamsRequest);
   }

   @Transactional
   public void boardDelete(BoardParams boardParamsRequest) throws Exception {
       boardMapper.boardDelete(boardParamsRequest);
   }
   
   @Transactional
   public void adminBoardDelete(BoardParams boardParamsRequest) throws Exception {
       boardMapper.adminBoardDelete(boardParamsRequest);
   }   

    @Transactional
    public BoardParams reserveInsert(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.reserveInsert(boardParamsRequest);
    }

    @Transactional
    public void reserveUpdate(BoardParams boardParamsRequest) throws Exception {
        boardMapper.reserveUpdate(boardParamsRequest);
    }

    @Transactional
    public BoardParams reserveDelete(BoardParams boardParamsRequest) throws Exception {
        return boardMapper.reserveDelete(boardParamsRequest);
    }   
    
}
