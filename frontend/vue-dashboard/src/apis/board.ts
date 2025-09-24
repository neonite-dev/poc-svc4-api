import { useAPI } from '@/composables/use-api'
import type {
  Board,
  BoardParam,
  BoardViewParam,
  MenuName,
  ReaderList,
  CommentList,
  CommentParam,
  BoardDelParam,
  BoardList,
  ReserveParam,
  BoardFileList,
  LikeParam,
  MainParam,
} from '@/types/boardTypes/board'

export const getBoardList = async (param: BoardParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/list', param)
  return data
}

export const getBoardView = async (param: BoardViewParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/view', param)
  return data
}

export const insertBoard = async (param: any) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/insertBoard', param)
  return data
}

export const getHeadTitle = async (param: BoardParam) => {
  const { data } = await useAPI.post<MenuName>('/api/extra/board/getHeadTitle', param)
  return data
}

export const increaseReadCount = async (param: BoardViewParam) => {
  const { data } = await useAPI.post('/api/extra/board/updateReadCount', param)
  return data
}

export const getReaderList = async (boardId: string, reader: string) => {
  const { data } = await useAPI.post<ReaderList>('/api/extra/board/readerList', { boardId, reader })
  return data
}

export const insertComment = async (param: CommentParam) => {
  const { data } = await useAPI.post('/api/extra/board/addComment', param)
  return data
}

export const getBoardReplyList = async (boardId: string, userId: string) => {
  const { data } = await useAPI.post<CommentList>('/api/extra/board/replyList', { boardId, userId })
  return data
}

export const replyDelete = async (replyId: string) => {
  const { data } = await useAPI.post('/api/extra/board/delReply', { replyId })
  return data
}

export const getIconList = async () => {
  const { data } = await useAPI.post<Board>('/api/extra/board/iconList')
  return data
}

export const boardDelete = async (param: BoardDelParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/delBoard', param)
  return data
}

export const boardReply = async (param: BoardParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/insertReply', param)
  return data
}

export const boardUpdate = async (param: BoardParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/updateBoard', param)
  return data
}

export const boardIconDelete = async (param: any) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/delIcon', param)
  return data
}

export const getReservePossibleTimeList = async (param: any) => {
  const { data } = await useAPI.post<BoardList>('/api/extra/board/getReserveTime', param)
  return data
}

export const reserveInsert = async (param: ReserveParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/insertReserve', param)
  return data
}

export const reserveUpdate = async (param: ReserveParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/updateReserve', param)
  return data
}

export const reserveDelete = async (param: ReserveParam) => {
  const { data } = await useAPI.post('/api/extra/board/delReserve', param)
  return data
}

export const getFileList = async (boardId: string) => {
  const { data } = await useAPI.post<BoardFileList>('/api/extra/board/fileList', { boardId })
  return data
}

export const boardFileDelete = async (fileId: string) => {
  const { data } = await useAPI.post('/api/extra/board/delFile', { fileId })
  return data
}

export const likeThisAuth = async (userId: string, cls: string) => {
  const { data } = await useAPI.post('/api/extra/board/likeThisAuth', { userId, cls })
  return data
}

export const setLike = async (param: LikeParam) => {
  const { data } = await useAPI.post('/api/extra/board/setlike', param)
  return data
}

export const getMainBoardList = async (param: MainParam) => {
  const { data } = await useAPI.post<Board>('/api/extra/board/getMainBoardList', param)
  return data
}

export const getBoardNewCount = async () => {
  const { data } = await useAPI.post<Board>('/api/extra/board/getBoardNewCount')
  return data
}
