<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { BoxIcon, CopyIcon, HeadingIcon, KeyIcon, SettingsIcon, ToolIcon, UsersIcon } from 'vue-tabler-icons'
import { getAuthList } from '@/apis/common'
import { getUserInfo } from '@/views/pages/board/Common'
import { clsChange } from '../board/Common'
import { useAuthStore } from '@/stores/auth'
import type { AuthParam, AuthList } from '@/types/commonTypes/common'

const authStore = useAuthStore()
const selection = ref('E')
const authcheck = new Map()
const options = ref<Array<any>>([])
const authType = ref(['' as string])

//UserInfo
const { userId: loginId } = getUserInfo()

const userListParam: AuthParam = reactive({
  cls: selection.value,
  authType: '',
  userId: loginId,
  pageNo: 1,
  pageSize: 100,
})

//학급권한 확인
const getAuth = async () => {
  const list = await authStore.getAuth()

  list.forEach(item => {
    if (item.cls.match(/[MEKG]/)) {
      authcheck.set(clsChange(item.cls), item.cls)
    }
  })

  const optionsTemp = ref(
    [...authcheck.entries()].map(([key, value]) => ({
      text: key,
      value: value,
    })),
  )
  options.value = optionsTemp.value
}

//권한 메뉴 리스트
const getUserList = async () => {
  userListParam.cls = selection.value
  authType.value = [] //초기화

  try {
    const data: AuthList[] = await getAuthList(userListParam)
    if (data.length > 0) {
      for (let i = 0; i < data.length; i++) {
        authType.value[i] = data[i].authType
      }
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  getUserList()
  getAuth()
})
</script>

<template>
  <v-row>
    <v-col fluid>
      <div class="d-flex align-start">
        <v-btn
          v-if="authType.includes('A01')"
          :prepend-icon="KeyIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          :to="`/admin/auth/${selection}`"
          >기능관리</v-btn
        >

        <v-btn
          v-if="authType.includes('A02')"
          :prepend-icon="ToolIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          >게시판 관리</v-btn
        >
        <v-btn
          v-if="authType.includes('A03')"
          :prepend-icon="SettingsIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          >메뉴권한</v-btn
        >
        <v-btn
          v-if="authType.includes('A04')"
          :prepend-icon="HeadingIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          >말머리 설정</v-btn
        >
        <v-btn
          v-if="authType.includes('A09')"
          :prepend-icon="UsersIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          :to="`/admin/users/${selection}`"
          >사용자 관리</v-btn
        >
        <v-btn
          v-if="authType.includes('A10')"
          :prepend-icon="BoxIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          >조직 관리</v-btn
        >
        <v-btn
          v-if="authType.includes('A11')"
          :prepend-icon="CopyIcon"
          color="lightsecondary"
          class="mr-2"
          variant="flat"
          size="default"
          >필터 관리</v-btn
        >
      </div>
    </v-col>
    <v-col cols="2">
      <v-select
        :items="options"
        color="secondary"
        variant="outlined"
        density="compact"
        v-model="selection"
        item-title="text"
        item-value="value"
        @update:model-value="getUserList"
        hide-details
      ></v-select>
    </v-col>
  </v-row>
</template>
