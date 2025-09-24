<script setup lang="ts">
import { useCustomizerStore } from '../../../stores/customizer'
import { Menu2Icon, UserIcon } from 'vue-tabler-icons'
import ProfileDD from './ProfileDD.vue'
import { getUserInfo } from '@/views/pages/board/Common'
const customizer = useCustomizerStore()

//관리자 여부
const { authYn: auth, cls: loginCls } = getUserInfo()

function openAdmin() {
  window.open('/vueapp2/admin/auth/' + loginCls, '관리자 페이지', 'width=1200,height=800,scrollbars=no,resizable=no')
}
</script>

<template>
  <v-app-bar
    elevation="0"
    height="80"
  >
    <v-btn
      class="hidden-md-and-down text-secondary"
      color="lightsecondary"
      icon
      rounded="sm"
      variant="flat"
      size="small"
      @click.stop="customizer.SET_MINI_SIDEBAR(!customizer.mini_sidebar)"
    >
      <Menu2Icon
        size="20"
        stroke-width="1.5"
      />
    </v-btn>
    <v-btn
      class="hidden-lg-and-up text-secondary ms-3"
      color="lightsecondary"
      icon
      rounded="sm"
      variant="flat"
      size="small"
      @click.stop="customizer.SET_SIDEBAR_DRAWER"
    >
      <Menu2Icon
        size="20"
        stroke-width="1.5"
      />
    </v-btn>
    <v-spacer />

    <v-chip
      v-if="auth"
      color="error"
      text="관리자 페이지"
      size="small"
      variant="tonal"
      label
      prepend-icon="mdi-cog"
      rounded="pill"
      class="pa-5 ma-2"
      @click="openAdmin"
    ></v-chip>

    <v-divider
      v-if="auth"
      vertical
      color="primary"
      thickness="1"
      style="block-size: 55px"
      class="mt-3 ml-3 mr-2"
    ></v-divider>

    <!--상단 유저 아이콘-->
    <v-menu :close-on-content-click="false">
      <template #activator="{ props }">
        <v-btn
          class="profileBtn text-secondary"
          color="lightsecondary"
          variant="flat"
          rounded="pill"
          size="small"
          v-bind="props"
        >
          <UserIcon stroke-width="1.5" />
        </v-btn>
      </template>
      <v-sheet
        rounded="md"
        width="280"
        elevation="12"
      >
        <ProfileDD />
      </v-sheet>
    </v-menu>
  </v-app-bar>
</template>
