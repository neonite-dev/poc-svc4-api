import { useAuthStore } from '@/stores/auth'
import { networkFetchClient } from '@/utils/helpers/networkFetchClient'
import { urlParamParser } from '@/utils/helpers/url-parser'
class useApiClient {
  networkClient: typeof networkFetchClient
  private isRefreshing = false
  private isReissueResult = false
  private refreshTokenPromise: Promise<boolean> | null = null
  private currentMethod = ''
  constructor(networkClient: typeof networkFetchClient) {
    this.networkClient = networkClient
  }

  async get<T>(url: string) {
    const response = await this.networkClient(url, {
      afterFetch: ctx => {
        if (ctx.data) {
          return ctx.data
        }
        return ctx
      },
    })
      .get()
      .json<T>()

    if (response.statusCode?.value === 401) {
      this.currentMethod = 'get'
      return this.handleExpiredAccessToken(url)
    }
    return response
  }

  async post<T>(url: string, body?: unknown, opt?: unknown) {
    const response = await this.networkClient(url, {
      beforeFetch({ options }: any) {
        if (opt) {
          Object.assign(options, opt)
        } else {
          options.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8'
        }
        return { options }
      },
      afterFetch: ctx => {
        if (ctx.data) {
          return ctx.data
        }
        return ctx
      },
    })
      .post(urlParamParser(body))
      .json<T>()

    if (response.statusCode?.value === 401) {
      this.currentMethod = 'post'
      return this.handleExpiredAccessToken(url, body, opt)
    }
    return response
  }

  async text(url: string, body?: unknown, opt?: unknown) {
    const response = this.networkClient(url, {
      beforeFetch({ options }: any) {
        if (opt) {
          Object.assign(options, opt)
        } else {
          options.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8'
        }
        return { options }
      },
      afterFetch: ctx => {
        if (ctx.data) {
          return ctx.data
        }
        return ctx
      },
    })
      .post(urlParamParser(body))
      .text()

    if (response.statusCode?.value === 401) {
      this.currentMethod = 'text'
      return this.handleExpiredAccessToken(url, body, opt)
    }
    return response
  }

  private async handleExpiredAccessToken(url: string, body?: unknown, opt?: unknown): Promise<any> {
    if (!this.isRefreshing) {
      this.isRefreshing = true
      this.refreshTokenPromise = this.getReissueToken()
      this.isReissueResult = await this.refreshTokenPromise
      this.isRefreshing = false
    } else {
      await this.refreshTokenPromise
    }
    if (this.isReissueResult) {
      switch (this.currentMethod) {
        case 'get':
          return this.get(url)
        case 'post':
          return this.post(url, body, opt)
        case 'text':
          return this.text(url, body, opt)
      }
    }
  }

  private async getReissueToken() {
    const { reissue } = useAuthStore()
    const isResult = await reissue()
    this.isReissueResult = isResult
    return isResult
  }
}

export const useAPI = new useApiClient(networkFetchClient)
