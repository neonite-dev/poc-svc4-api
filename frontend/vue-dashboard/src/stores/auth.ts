import { defineStore } from 'pinia'
import { router } from '@/router'
import { checkUserLoginDuple, procLogin, procLogout, reissueToken } from '@/apis/login'
import { getAuthList } from '@/apis/common'
import type { AuthParam } from '@/types/commonTypes/common'
import { reactive } from 'vue'
const baseUrl = `${import.meta.env.VITE_API_URL}/users`

const authInfo: AuthParam = reactive({
  cls: '',
  authType: '',
  userId: '',
  pageNo: 1,
  pageSize: 100,
})

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in

    // @ts-ignore
    user: JSON.parse(localStorage.getItem('user')),
    returnUrl: null,
  }),
  actions: {
    async login(username: string, password: string) {
      // const user = await fetchWrapper.post(`${baseUrl}/authenticate`, { username, password })
      const user = await procLogin(username, password)
      if (user?.loginYn !== 'Y') {
        alert(user.result)
        this.logout()
        return
      }
      // update pinia state
      this.user = user
      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('user', JSON.stringify(user))
      // redirect to previous url or default to home page
      router.push(this.returnUrl || '/')
    },
    async reissue() {
      const res = await reissueToken()
      if (res?.refreshToken) {
        this.user.token = res.token
        this.user.refreshToken = res.refreshToken
        // store user details and jwt in local storage to keep user logged in between page refreshes
        localStorage.setItem('user', JSON.stringify(this.user))
        return true
      }
      return false
    },
    async userDupleCheck(username: string) {
      const isDuplicateCheck = await checkUserLoginDuple(username)
      return isDuplicateCheck === 'Y'
    },
    async logout() {
      await procLogout(this.user?.userId)
      this.user = null
      localStorage.removeItem('user')
      router.push('/auth/login')
    },
    async getAuth() {
      authInfo.userId = this.user?.userId
      const auth = await getAuthList(authInfo)
      return auth
    },
  },
})
