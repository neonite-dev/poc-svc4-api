import { useAPI } from '@/composables/use-api'
import type { AuthInfo, AuthList, AuthParam } from '@/types/commonTypes/common'
import type { Menu } from '@/types/commonTypes/menu'
import type { AuthInsertParam, AuthDelParam, UserListParam, UserList } from '@/types/commonTypes/common'

export const getMenuList = async (userId: string) => {
  const { data }: any = await useAPI.post<Menu>('/api/extra/common/menuList', { userId })
  return data.value as Menu[]
}

export const getMenuListById = async (userId: string) => {
  const { data }: any = await useAPI.post<Menu>('/api/extra/common/menuList', { userId })
  return data.value as Menu[]
}

export const getAuthUserList = async (menuId: string) => {
  const { data }: any = await useAPI.post<AuthInfo>('/api/extra/common/authUserList', { menuId })
  return data.value as AuthInfo[]
}

export const getAuthList = async (param: AuthParam) => {
  const { data }: any = await useAPI.post<AuthList>('/api/extra/common/getAuthList', param)
  return data.value as AuthList[]
}

export const getAuthListCount = async (cls: string) => {
  const { data }: any = await useAPI.post('/api/extra/common/getAuthListCount', { cls })
  return data
}

export const getUserInfoList = async (param: UserListParam) => {
  const { data }: any = await useAPI.post<UserList>('/api/extra/common/getUserInfoList', param)
  return data.value as UserList[]
}

export const menuAuthInsert = async (param: AuthInsertParam) => {
  const { data }: any = await useAPI.post('/api/extra/common/menuAuthInsert', param)
  return data
}

export const setAuth = async (param: AuthInsertParam) => {
  const { data }: any = await useAPI.post('/api/extra/common/setAuth', param)
  return data
}

export const setAuthDelete = async (param: AuthDelParam) => {
  const { data }: any = await useAPI.post('/api/extra/common/setAuthDelete', param)
  return data
}
