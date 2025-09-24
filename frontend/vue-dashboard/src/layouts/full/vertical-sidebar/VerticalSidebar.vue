<script setup lang="ts">
import { computed, shallowRef } from 'vue'
import { useCustomizerStore } from '../../../stores/customizer'
import { useMenuStore } from '@/stores/menu'
import { storeToRefs } from 'pinia'
import sidebarItems from './sidebarItem'
import NavGroup from './NavGroup/NavGroup.vue'
import NavItem from './NavItem/NavItem.vue'
import NavCollapse from './NavCollapse/NavCollapse.vue'
import Logo from '../logo/LogoMain.vue'
import TreeView from './TreeView/TreeView.vue'

const customizer = useCustomizerStore()
const sidebarMenu = shallowRef(sidebarItems)
const menuStore = useMenuStore()
const { getMenus } = menuStore
const { menus, currentMenu } = storeToRefs(menuStore)
getMenus()

const treeFilter = computed(() => {
  // debugger
  // if (menus.value?.length > 0) {
  //   const filterEle = menus.value.filter((m: Menu) => m.cls === 'E') // 초등
  //   const parseMenuItems = parseMenuTree(filterEle, null)
  //   sidebarMenu.value.push(...parseMenuItems)
  //   return sidebarMenu.value
  // }

  return sidebarMenu.value
})
</script>

<template>
  <v-navigation-drawer
    v-model="customizer.Sidebar_drawer"
    left
    elevation="0"
    rail-width="15"
    mobile-breakpoint="lg"
    app
    class="leftSidebar"
    :rail="customizer.mini_sidebar"
  >
    <div class="pa-3 mt-2">
      <Logo />
    </div>

    <perfect-scrollbar class="scrollnavbar ml-2">
      <div class="pa-4">
        <TreeView
          :item="menus"
          :type="'menuList'"
          :target="currentMenu"
        />
      </div>
      <v-list class="pa-4">
        <template
          v-for="(item, i) in treeFilter"
          :key="i"
        >
          <NavGroup
            v-if="item.header"
            :key="item.title"
            :item="item"
          />

          <v-divider
            v-else-if="item.divider"
            class="my-3"
          />

          <NavCollapse
            v-else-if="item.children"
            class="leftPadding"
            :item="item"
            :level="0"
          />

          <NavItem
            v-else
            :item="item"
            class="leftPadding"
          />
        </template>
      </v-list>

      <div class="pa-4 text-center">
        <v-chip
          color="inputBorder"
          size="small"
        >
          Extranet v1.0.0
        </v-chip>
      </div>
    </perfect-scrollbar>
  </v-navigation-drawer>
</template>
