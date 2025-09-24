export interface AuthInfo {
  userId: string
  idx: number
  indate: string
}

export interface AuthList {
  rowNumber: number
  idx: number
  authType: string
  userId: string
  regUserId: string
  regDate: string
  cls: string
  name: string
  totalRowsNum: number
}

export interface AuthParam {
  cls: string
  authType: string
  userId: string
  pageNo: number
  pageSize: number
}

export interface AuthInsertParam {
  cls: string
  authType: string
  userId: string
  regUserId: string
  returnCode: string
}

export interface AuthDelParam {
  cls: string
  idx: number
}

export interface UserList {
  rowNumber: number
  userId: string
  name: string
  email: string
  tel: string
  extraId_Block: string
  loginFailCnt: number
  creDate: string
  cls: string
  room: string
  team: string
  part: string
  roomNm: string
  teamNm: string
  partNm: string
  totalRowsNum: number
}

export interface UserListParam {
  searchMode: string
  searchText: string
  lock: string
  pageNo: number
  pageSize: number
  userId: string
  sortExpression: string
}
