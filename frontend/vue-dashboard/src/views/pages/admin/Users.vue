<script setup lang="ts">
import { ref, reactive } from 'vue'
import UiEmptyTitleCard from '@/components/shared/UiEmptyTitleCard.vue'
import { UsersIcon } from 'vue-tabler-icons'
import { getUserInfoList } from '@/apis/common'
import { format } from 'date-fns'
import { getUserInfo } from '@/views/pages/board/Common'
import { useRoute } from 'vue-router'
import type { UserListParam, UserList } from '@/types/commonTypes/common'

const route = useRoute()
const items = ref<UserList[]>([])
const totalItems = ref<number>(0)

const headers = ref<any>([
  { key: 'rowNumber', title: 'No.', align: 'start', sortable: false, width: '5%' },
  { key: 'name', title: '사용자명', align: 'center', sortable: false, width: '9%' },
  { key: 'userId', title: '사용자아이디', align: 'center', sortable: false, width: '12%' },
  { key: 'tel', title: '전화번호', align: 'center', sortable: false, width: '12%' },
  { key: 'creDate', title: '등록일자', align: 'center', sortable: false, width: '10%' },
  { key: 'roomNm', title: '실', align: 'center', sortable: false, width: '8%' },
  { key: 'teamNm', title: '팀', align: 'center', sortable: false, width: '10%' },
  { key: 'partNm', title: '파트', align: 'center', sortable: false, width: '11%' },
  { key: '', title: '조직수정', align: 'center', sortable: false, width: '7%' },
  { key: 'loginFailCnt', title: '실패횟수', align: 'center', sortable: false, width: '9%' },
  { key: '', title: '잠금', align: 'end', sortable: false, width: '6%' },
])

const userListParam: UserListParam = reactive({
  searchMode: route.params.cls.toString(),
  searchText: '',
  lock: '',
  pageNo: 1,
  pageSize: 10,
  userId: '',
  sortExpression: '',
})

//사용자 리스트
const getUserList = async ({ page, itemsPerPage }: any) => {
  userListParam.pageNo = page || userListParam.pageNo + 1
  userListParam.pageSize = itemsPerPage || userListParam.pageSize

  try {
    const data: UserList[] = await getUserInfoList(userListParam)

    if (data.length > 0) {
      items.value = data
      if (items.value?.length > 0) {
        const totalCnt = items.value[0].totalRowsNum
        totalItems.value = totalCnt
      }
    }
  } catch (e) {
    console.error(e)
  }
}
</script>

<template>
  <UiEmptyTitleCard>
    <v-row>
      <v-col>
        <div class="text-subtitle-1 font-weight-bold text-secondary">
          <UsersIcon
            size="15"
            class="mr-2"
          />사용자 관리
        </div>
        <v-divider class="mt-2 mb-3" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="8">
        <v-text-field
          type="text"
          variant="outlined"
          density="compact"
          label="사용자 검색"
          v-model="userListParam.searchText"
        ></v-text-field>
      </v-col>
    </v-row>
    <v-divider class="mb-0" />
    <v-row>
      <v-col>
        <v-data-table-server
          v-model:items-per-page="userListParam.pageSize"
          :headers="headers"
          :items="items"
          :items-length="totalItems"
          item-value="id"
          @update:options="getUserList"
          :search="userListParam.searchText"
          noDataText="검색 결과가 없습니다."
          itemsPerPageText=""
          density="compact"
          fixed-header
          height="410px"
        >
          <template v-slot:body="{ items }">
            <tr
              v-for="item in items"
              :key="item.rowNumber"
            >
              <td class="text-start">{{ item.rowNumber }}</td>
              <td class="text-center">{{ item.name }}</td>
              <td class="text-center">{{ item.userId }}</td>
              <td class="text-center">{{ item.tel }}</td>
              <td class="text-center">{{ format(new Date(item.creDate), 'yyyy-MM-dd') }}</td>
              <td class="text-center">{{ item.roomNm }}</td>
              <td class="text-center">{{ item.teamNm }}</td>
              <td class="text-center">{{ item.partNm }}</td>
              <td class="text-center">
                <v-btn
                  size="small"
                  color="secondary"
                  variant="flat"
                  >조직수정</v-btn
                >
              </td>
              <td class="text-center">{{ item.loginFailCnt }}</td>
              <td class="text-end"></td>
            </tr>
          </template>
        </v-data-table-server> </v-col
    ></v-row>
  </UiEmptyTitleCard>
</template>
