<script setup lang="ts">
import { LogoutIcon, UserIcon } from 'vue-tabler-icons'
import { useAuthStore } from '@/stores/auth'
import { getUserInfo } from '@/views/pages/board/Common'

const authStore = useAuthStore()

//관리자 여부
const { authYn: auth, name: loginName } = getUserInfo()
</script>

<template>
  <div class="pa-4">
    <h4 class="mb-n1">{{ loginName }}님 환영합니다!</h4>
    <span
      class="text-subtitle-2 text-medium-emphasis"
      v-if="auth"
      >관리자</span
    ><span
      class="text-subtitle-2 text-medium-emphasis"
      v-else
      >교사</span
    >

    <v-divider />
    <perfect-scrollbar style="block-size: calc(100vh - 300px); max-block-size: 100px">
      <v-list class="mt-3 pa-0">
        <v-list-item
          color="secondary"
          rounded="md"
          density="compact"
        >
          <template #prepend>
            <UserIcon
              size="20"
              class="mr-2"
            />
          </template>

          <v-list-item-title class="text-subtitle-1"> 마이 페이지</v-list-item-title>
        </v-list-item>

        <v-list-item
          color="secondary"
          rounded="md"
          density="compact"
          @click="authStore.logout()"
        >
          <template #prepend>
            <LogoutIcon
              size="20"
              class="mr-2"
            />
          </template>

          <v-list-item-title class="text-subtitle-1"> 로그아웃</v-list-item-title>
        </v-list-item>
      </v-list>
    </perfect-scrollbar>
  </div>
</template>
