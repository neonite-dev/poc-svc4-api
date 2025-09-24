export interface Board extends BoardList {
  rowNum: number
  boardId: string
  menuId: string
  type: string
  num: number
  writer: string
  title: string
  readCount: number
  regDate: string
  refer: number
  step: number
  depth: number
  delflag: string
  content: string
  state: string
  gubun1: string
  gubun2: string
  topOrder: number
  headTitle: string
  remoteIP: string
  color: string
  temp: number
  useReplyLike: number
  writerName: string
  replyCount: number
  idx: number
  newCount: number
}

export interface BoardList {
  departmentName: string
  totalCnt: number
  replyCount: number
  fileCount: number
  name: string
  userId: string
  reserveDate: string
  reserveTime: string
}

export interface BoardParam {
  SearchMode: string
  MenuId: string
  SearchText: string
  State: string
  Writer: string
  Size: number
  No: number
  Secret: string
  HeadTitle: string
  BlindYN: string
  Cls: string
  SearchType: string
  ReserveDate: string
  ReserveSort: string
}

export interface BoardViewParam {
  boardId: string
  userId: string
}

export interface MenuName {
  headValue: string
  headTitle: string
  sort: number
}

export interface ReaderList {
  boardId: string
  reader: string
  readCount: number
  readTime: string
  reader_Name: string
}

export interface CommentParam {
  boardId: string
  writer: string
  content: string
}

export interface CommentList {
  replyId: string
  boardId: string
  writer: string
  content: string
  regdate: Date | string
  writerName: string
  rwriter: string
  totallikecnt: string
  ismylike: number
}

export interface BoardDelParam {
  menuId: number
  boardId: string
  userId: string
  cls: string
}

export interface IconList {
  idx: number
  src: string
}

export interface ReserveParam {
  boardId: string
  teacherId: string
  reserveDate: string
  reserveTime: string
  idx: number
}

export interface BoardFileList {
  fileid: string
  boardid: string
  filename: string
  newfilename: string
  filebytes: number
  filepath: string
  ext: string
  filetotalbytes: number
}

export interface LikeParam {
  replyId: string
  userId: string
  likeYn: number
}

export interface MainParam {
  topCnt: number
  userId: string
  type: string
}
