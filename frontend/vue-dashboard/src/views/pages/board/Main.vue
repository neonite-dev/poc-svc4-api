<script setup lang="ts">
import { getMainBoardList } from '@/apis/board'
import { reactive, ref, onMounted, computed } from 'vue'
import { format } from 'date-fns'
import type { Board, MainParam } from '@/types/boardTypes/board'
import { BookIcon, PinIcon } from 'vue-tabler-icons'
import { getUserInfo } from '@/views/pages/board/Common'

//UserInfo
const { cls: loginCls, userId: loginId } = getUserInfo()

//클래스별 공지 메뉴 아이디 지정
const noticeId = computed(() => {
  switch (loginCls) {
    case 'K':
      return 1597
    case 'E':
      return 9
    case 'M':
      return 1197
    case 'G':
      return 10001
    default:
      return 0
  }
})

const monthId = computed(() => {
  switch (loginCls) {
    case 'K':
      return 1464
    case 'E':
      return 624
    case 'M':
      return 1207
    case 'G':
      return 10002
    default:
      return 0
  }
})

const notice = ref<Board[]>([])
const items = ref<Board[]>([])

const headers = ref<any>([
  { key: 'title', title: '제목', align: 'center', sortable: false },
  { key: 'departmentName', title: '소속', align: 'center', sortable: false, width: '13%' },
  { key: 'name', title: '작성자', align: 'center', sortable: false, width: '10%' },
  { key: 'regDate', title: '날짜', align: 'center', sortable: false, width: '12%' },
])

const mainBoardParam: MainParam = reactive({
  topCnt: 8,
  userId: loginId,
  type: '',
})

const getMainList1 = async () => {
  try {
    mainBoardParam.type = 'N'
    const data: any = await getMainBoardList(mainBoardParam)
    notice.value = []

    if (data.value?.length > 0) {
      notice.value = [].concat(data.value as any) as Board[]
    }
  } catch (e) {
    console.error(e)
  }
}

const getMainList2 = async () => {
  try {
    mainBoardParam.type = 'M'
    const data: any = await getMainBoardList(mainBoardParam)
    items.value = []

    if (data.value?.length > 0) {
      items.value = [].concat(data.value as any) as Board[]
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  getMainList1()
  getMainList2()
})
</script>

<template>
  <v-row>
    <v-col cols="12">
      <v-card elevation="0">
        <div class="d-flex align-start text-h4 font-weight-bold pa-3 ml-3 text-secondary">
          <PinIcon
            size="20"
            class="mr-2"
          />
          공지사항
        </div>

        <v-divider />

        <v-card-text class="pb-3 pt-0">
          <div>
            <v-data-table-server
              :headers="headers"
              :items="notice"
              :item-value="(item: any) => `${item.num}-${item.version}`"
              noDataText="검색 결과가 없습니다."
              itemsPerPageText=""
              density="compact"
              :items-length="8"
              :hide-default-footer="true"
              class="text-h6"
            >
              <template v-slot:body="{ items }">
                <tr
                  v-for="item in items"
                  :key="item.boardId"
                >
                  <td>
                    <div class="text-start">
                      <v-icon
                        v-if="item.topOrder === 1"
                        icon="mdi-volume-high"
                        color="primary"
                      ></v-icon>
                      <v-label v-if="item.step && item.step > 0"
                        ><!--댓글-->
                        <div
                          v-for="v in item.step"
                          :key="v"
                          class="pl-4"
                        ></div>
                        <v-icon
                          icon="mdi-subdirectory-arrow-right"
                          color="warning"
                        ></v-icon
                      ></v-label>
                      <v-hover>
                        <router-link
                          :to="{ path: `/boardview/${item.boardId}`, query: { menuId: noticeId } }"
                          class="mx-2 text-body-2 text-decoration-none"
                          style="color: black"
                        >
                          <span v-html="item.title"></span>
                        </router-link>
                      </v-hover>
                    </div>
                  </td>
                  <td>
                    <div class="text-center text-body-2 text-decoration-none">
                      {{ item.departmentName }}
                    </div>
                  </td>
                  <td class="text-center text-body-2 text-decoration-none">
                    {{ item.name }}
                  </td>
                  <td>
                    <div class="text-center text-body-2 text-decoration-none">{{ format(new Date(item.regDate), 'yyyy-MM-dd') }}</div>
                  </td>
                </tr>
              </template>
            </v-data-table-server>
          </div>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>

  <v-row>
    <v-col cols="12">
      <v-card elevation="0">
        <div class="d-flex align-start text-h4 font-weight-bold pa-3 ml-3 text-secondary">
          <BookIcon
            size="20"
            class="mr-2"
          />
          월 상담 전략
        </div>

        <v-divider />

        <v-card-text class="pb-3 pt-0">
          <div>
            <v-data-table-server
              :headers="headers"
              :items="items"
              :item-value="(item: any) => `${item.num}-${item.version}`"
              noDataText="검색 결과가 없습니다."
              itemsPerPageText=""
              density="compact"
              :items-length="8"
              :hide-default-footer="true"
              class="text-h6"
            >
              <template v-slot:body="{ items }">
                <tr
                  v-for="item in items"
                  :key="item.boardId"
                >
                  <td>
                    <div class="text-start">
                      <v-icon
                        v-if="item.topOrder === 1"
                        icon="mdi-volume-high"
                        color="primary"
                      ></v-icon>
                      <v-hover>
                        <router-link
                          :to="{ path: `/boardview/${item.boardId}`, query: { menuId: monthId } }"
                          class="mx-2 text-body-2 text-decoration-none"
                          style="color: black"
                        >
                          <span v-html="item.title"></span>
                        </router-link>
                      </v-hover>
                    </div>
                  </td>
                  <td>
                    <div class="text-center text-body-2 text-decoration-none">
                      {{ item.departmentName }}
                    </div>
                  </td>
                  <td class="text-center text-body-2 text-decoration-none">
                    {{ item.name }}
                  </td>
                  <td>
                    <div class="text-center text-body-2 text-decoration-none">{{ format(new Date(item.regDate), 'yyyy-MM-dd') }}</div>
                  </td>
                </tr>
              </template>
            </v-data-table-server>
          </div>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<style lang="css" scoped>
.v-table tbody tr:nth-child(odd) {
  background-color: #f2f2f2;
}
</style>
