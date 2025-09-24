import { defineStore } from 'pinia'
import type { Menu } from '@/types/commonTypes/menu'
import { getMenuListById } from '@/apis/common'
import { addRootMenu } from '@/utils/helpers/menu-parser'
import { useAuthStore } from '@/stores/auth'

export const useMenuStore = defineStore({
  id: 'menu',
  state: (): {
    menus: Menu[]
    currentMenu: Menu
    currentType: Menu
  } => ({
    menus: [] as Menu[],
    currentMenu: {} as Menu,
    currentType: {} as Menu,
  }),
  getters: {
    currentClass: (state: any) => state.currentMenu.cls,
    currentBoardType: (state: any) => state.currentMenu.type,
  },
  actions: {
    async getMenus() {
      const { user, logout } = useAuthStore()
      if (!user) {
        logout()
        return
      }
      const buf = [] as Menu[]
      if (user?.userId && user?.userId != '') {
        const data = await getMenuListById(user?.userId)

        const cRoot = addRootMenu(
          data?.filter((m: Menu) => m.cls === 'G'),
          100000,
          '공통',
        )
        const kRoot = addRootMenu(
          data?.filter((m: Menu) => m.cls === 'K'),
          100001,
          '유아',
        )
        const eRoot = addRootMenu(
          data?.filter((m: Menu) => m.cls === 'E'),
          100002,
          '초등',
        )
        const mRoot = addRootMenu(
          data?.filter((m: Menu) => m.cls === 'M'),
          100003,
          '중ㆍ고등',
        ) // 중고등
        buf.push(...cRoot, ...eRoot, ...kRoot, ...mRoot)
      }
      this.menus = buf
    },

    setCurrentMenu(menuId: string | number) {
      if (this.$state.menus.length > 0) {
        this.currentMenu = this.$state.menus?.find(m => m.menuId == menuId) as Menu
      }
    },
  },
})
