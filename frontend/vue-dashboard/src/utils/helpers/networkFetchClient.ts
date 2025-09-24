import { router } from '@/router'
import { useAuthStore } from '@/stores/auth'
import { createFetch } from '@vueuse/core'

export const networkFetchClient = createFetch({
  // baseUrl: `${import.meta.env.VITE_API_URL}`,
  combination: 'chain',
  options: {
    beforeFetch({ options }: any) {
      const { user } = useAuthStore()
      const isLoggedIn = !!user?.token
      if (isLoggedIn) {
        options.headers.Authorization = `Bearer ${user.token}`
        // options.headers.access = `${user.token}`
        if (user.refreshToken) {
          options.headers['Authorization-Refresh'] = `Bearer ${user.refreshToken}`
        }
      }
      return { options }
    },
    afterFetch(ctx) {
      // if the response contains a data property, return it
      if (ctx.data) {
        return ctx.data
      }

      return ctx
    },
    refetch: true,
    updateDataOnError: true,
    onFetchError(ctx) {
      if (ctx.data) {
        return ctx.data
      }
      const { user, logout } = useAuthStore()
      if (ctx.response && user) {
        if ([401, 403].includes(ctx.response.status)) {
          if (ctx.error?.message?.includes('access token expired')) {
            // retry
            console.log(ctx.error.message)
            return ctx
          } else {
            // && user
            // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
            logout()
          }
        }
        if ([500].includes(ctx.response.status)) {
          // 상태별 error 페이지 구현 할것
          // router.push('/500')
        }
        return ctx
      }
      return ctx
    },
  },
  fetchOptions: {
    mode: 'cors',
  },
})
