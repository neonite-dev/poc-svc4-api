import { useAPI } from '@/composables/use-api'
import type { Login } from '@/types/loginTypes/login'
import { useFetch } from '@vueuse/core'

export const checkUserLoginDuple = async (userName: string) => {
  // 단순 String 데이터는 text로 전달 받는다.
  const { data }: any = await useAPI.text('/api/extra/login/dupleLoginChk', { userName })
  return data.value
}

export const procLogin = async (userId: string, password: string) => {
  // 단순 String 데이터는 text로 전달 받는다.
  const { data } = await useAPI.post<Login>('/api/extra/login/signin', { userId, password })
  return data.value as Login
}

export const reissueToken = async () => {
  // 단순 String 데이터는 text로 전달 받는다.
  const { data } = await useAPI.post<Login>('/api/extra/login/reissue', {})
  return data.value as Login
}

export const procLogout = async (userId: string) => {
  if (userId && userId != '') {
    await useAPI.post('/api/extra/login/signout', { userId })
  }
}
