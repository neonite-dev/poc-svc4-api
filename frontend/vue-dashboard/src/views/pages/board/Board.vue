<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue'
import UiEmptyTitleCard from '@/components/shared/UiEmptyTitleCard.vue'
import { PencilPlusIcon, CopyIcon, ProgressCheckIcon, ProgressHelpIcon, ProgressBoltIcon } from 'vue-tabler-icons'
import { format } from 'date-fns'
import { getBoardList, getHeadTitle } from '@/apis/board'
import { getAuthUserList } from '@/apis/common'
import { useRoute } from 'vue-router'
import { useMenuStore } from '@/stores/menu'
import { storeToRefs } from 'pinia'
import { router } from '@/router'
import { getUserInfo } from '@/views/pages/board/Common'
import type { Menu } from '@/types/commonTypes/menu'
import type { AuthInfo } from '@/types/commonTypes/common'
import type { Board, BoardParam, MenuName } from '@/types/boardTypes/board'

const route = useRoute()
const menuStore = useMenuStore()
const { setCurrentMenu } = menuStore
const { menus, currentMenu, currentClass, currentBoardType } = storeToRefs(menuStore)
setCurrentMenu(route.params.menuId.toString())

//UserInfo
const { userId: loginId } = getUserInfo()

//공지 숨기기
const hideNotice = ref<boolean>(false)
const showAll = ref<boolean>(true)

//말머리 변수
const menuId = ref(route.params.menuId)
const headtitlelists = ref<MenuName[]>([])
const headtitleYN = ref<boolean>(false)

//헤더
const pageMeta = ref({ title: '' })
const breadcrumbs = ref<any>([])
const headers = ref<any>([
  { key: 'num', title: '번호', align: 'start', sortable: false, width: '5%' },
  { key: 'title', title: '제목', align: 'center', sortable: false },
  { key: 'departmentName', title: '소속', align: 'center', sortable: false, width: '12%' },
  { key: 'name', title: '작성자', align: 'center', sortable: false, width: '10%' },
  { key: 'regDate', title: '작성일', align: 'center', sortable: false, width: '10%' },
  { key: 'readCount', title: '조회수', align: 'end', sortable: false, width: '8%' },
])

const headerB = ref<any>([
  { key: 'num', title: '번호', align: 'start', sortable: false, width: '5%' },
  { key: 'state', title: '상태', align: 'center', sortable: false, width: '8%' },
  { key: 'title', title: '제목', align: 'center', sortable: false },
  { key: 'departmentName', title: '소속', align: 'center', sortable: false, width: '12%' },
  { key: 'name', title: '작성자', align: 'center', sortable: false, width: '10%' },
  { key: 'regDate', title: '작성일', align: 'center', sortable: false, width: '10%' },
  { key: 'readCount', title: '조회수', align: 'end', sortable: false, width: '8%' },
])

const headerG = ref<any>([
  { key: 'num', title: '번호', align: 'start', sortable: false, width: '5%' },
  { key: 'title', title: '제목', align: 'center', sortable: false },
  { key: 'headTitle', title: '제안분류', align: 'start', sortable: false, width: '15%' },
  { key: 'name', title: '작성자', align: 'center', sortable: false, width: '10%' },
  { key: 'regDate', title: '작성일', align: 'center', sortable: false, width: '10%' },
  { key: 'readCount', title: '조회수', align: 'end', sortable: false, width: '8%' },
])

// const headersFilter = computed(() => {
//   if (items.value?.length > 0 && items.value.find(m => m.state && m.state !== '') == null) {
//     setCurrentMenu(route.params.menuId.toString())
//     return headers.value.filter((m: any) => m.key !== 'state')
//   }
//   return headers.value
// })

const pageMetaByMenuId = computed(() => {
  if (menus.value?.length > 0 && route.params.menuId) {
    setCurrentMenu(route.params.menuId.toString())
    pageMeta.value.title = currentMenu.value.menuName
  }
  return pageMeta.value.title
})

const breadcrumbsByMenuId = computed(() => {
  breadcrumbs.value = [].concat([])
  if (menus.value?.length > 0 && route.params.menuId && currentMenu.value?.menuName != null) {
    const bufArr = {
      title: currentMenu.value?.menuName,
      disabled: true,
      href: '#',
    }
    breadcrumbs.value.unshift(bufArr)
    recursiveReverseItem(menus.value, currentMenu.value)
  }
  return breadcrumbs.value
})

const recursiveReverseItem = (data: Menu[], curItem: Menu) => {
  if (curItem?.upperMenuId) {
    const item = data?.find(m => m.menuId.toString() == curItem?.upperMenuId)
    if (item) {
      const retItem = {
        title: item.menuName,
        disabled: false,
        to: `/board/${curItem.menuId}`,
      } as any
      breadcrumbs.value.unshift(retItem)
      recursiveReverseItem(data, item)
    }
  }
  return null
}

changeHeader()

//내가쓴글보기
const isTypeB = computed(() => {
  if (currentBoardType.value === 'B') {
    return true
  }
  return false
})

const viewMine = ref<boolean>(false)
const loading = ref<boolean>(true)
const authInfos = ref<AuthInfo[]>([])
const items = ref<Board[]>([])
const totalItems = ref<number>(0)

//Type B 게시판
const all = ref<boolean>(false)
const pending = ref<boolean>(false)
const ongoing = ref<boolean>(false)
const completed = ref<boolean>(false)

const boardParam: BoardParam = reactive({
  MenuId: route.params.menuId.toString(),
  SearchMode: '제목+내용',
  SearchText: '',
  State: '',
  Writer: '',
  Size: 30,
  No: 0,
  Secret: '',
  HeadTitle: '말머리선택',
  BlindYN: '',
  Cls: currentClass,
  SearchType: '0',
  ReserveDate: '',
  ReserveSort: '',
})

//function
//////////////////////////////////////////////////////

function viewMineBtn() {
  viewMine.value = true
  pending.value = false
  ongoing.value = false
  completed.value = false
  all.value = false
  loadItems({ page: 1 })
}

function changeHeader() {
  if (route.params.menuId === '10002') {
    headerB.value[3].title = '예약일시'
  }
}

function makeTopHide() {
  hideNotice.value = !hideNotice.value
  showAll.value = !showAll.value
}

function changeProgress(st: string) {
  ongoing.value = st.includes('O')
  pending.value = st.includes('P')
  completed.value = st.includes('C')
  all.value = !ongoing.value && !pending.value && !completed.value
  viewMine.value = false
  loadItems({ data: 1 })
}

//권한
const auth = async () => {
  try {
    authInfos.value = await getAuthUserList(route.params.menuId.toString())
    const authYN = authInfos.value.some(item => item.userId === loginId)

    if (!authYN) {
      router.push('/pages/error')
    }
  } catch (e) {
    console.error(e)
  }
}

auth()

//말머리 셀렉트
const getHeadTitleValue = async () => {
  try {
    const data: any = await getHeadTitle(boardParam)

    if (data.value?.length > 1) {
      //기본값 1개 나옴
      headtitlelists.value = [...data.value] as MenuName[]
      headtitleYN.value = true
      boardParam.HeadTitle = '말머리선택'
    } else {
      headtitleYN.value = false
    }
  } catch (e) {
    console.error(e)
  }
}
getHeadTitleValue()

const loadItems = async ({ page, itemsPerPage }: any) => {
  loading.value = true
  boardParam.No = (page || boardParam.No + 1) - 1
  boardParam.Size = itemsPerPage || boardParam.Size

  if (pending.value) {
    boardParam.State = '예정'
  } else if (ongoing.value) {
    boardParam.State = '진행중'
  } else if (completed.value) {
    boardParam.State = '완료'
  } else if (viewMine.value) {
    boardParam.State = '내가쓴글'
    boardParam.Writer = loginId
  } else {
    boardParam.State = ''
  }

  try {
    const data: any = await getBoardList(boardParam)
    items.value = []
    if (data.value?.length > 0) {
      items.value = [].concat(data.value as any) as Board[]
      if (items.value?.length > 0) {
        const totalCnt = items.value[0].totalCnt
        totalItems.value = totalCnt
      }
    }
    loading.value = false
  } catch (e) {
    items.value = []
    loading.value = false
  }
}

//새 메뉴 클릭시마다 실행
watch(
  () => route.params.menuId,
  val => {
    if (val && val != '') {
      boardParam.MenuId = val as string
      boardParam.No = 0
      boardParam.SearchText = ''
      boardParam.SearchType = '0'
      boardParam.HeadTitle = ''
      ongoing.value = false
      pending.value = false
      completed.value = false
      all.value = false
      viewMine.value = false
      loadItems({ data: 1 })
      setCurrentMenu(boardParam.MenuId)
      getHeadTitleValue()
      changeHeader()
      hideNotice.value = false
      showAll.value = true
      auth()
    }
  },
)

watch(
  () => route.params.menuId,
  newMenuId => {
    menuId.value = newMenuId
  },
)
</script>

<template>
  <BaseBreadcrumb
    :title="pageMetaByMenuId"
    :breadcrumbs="breadcrumbsByMenuId"
  ></BaseBreadcrumb>

  <UiEmptyTitleCard>
    <v-row
      justify="center"
      class="justify-space-between mb-3"
    >
      <v-col
        cols="3"
        md="2"
      >
        <v-select
          v-model="boardParam.SearchMode"
          variant="outlined"
          density="compact"
          :items="['제목', '내용', '작성자', '제목+내용', '소속']"
          @update:modelValue="loadItems({ page: 1 })"
        ></v-select>
      </v-col>
      <v-col
        cols="3"
        md="2"
      >
        <v-select
          v-model="boardParam.SearchType"
          variant="outlined"
          item-title="title"
          item-value="value"
          :items="[
            { title: '원글+답변', value: '0' },
            { title: '원글', value: '1' },
            { title: '답변', value: '2' },
          ]"
          @update:modelValue="loadItems({ page: 1 })"
          density="compact"
        ></v-select>
      </v-col>
      <v-col
        v-if="headtitleYN"
        cols="3"
        md="2"
      >
        <v-select
          density="compact"
          v-model="boardParam.HeadTitle"
          variant="outlined"
          :items="headtitlelists"
          item-title="headTitle"
          item-value="headValue"
          @update:modelValue="loadItems({ page: 1 })"
        ></v-select>
      </v-col>

      <v-col
        v-if="headtitleYN"
        cols="2"
        md="3"
      >
        <v-text-field
          v-model="boardParam.SearchText"
          label="검색어를 입력하세요."
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          hide-details
          single-line
          density="compact"
        ></v-text-field>
      </v-col>

      <v-col
        v-else
        cols="5"
        md="5"
      >
        <v-text-field
          v-model="boardParam.SearchText"
          label="검색어를 입력하세요."
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          hide-details
          single-line
          density="compact"
        ></v-text-field>
      </v-col>

      <v-col
        cols="5"
        md="3"
      >
        <div class="d-flex ga-2 justify-end">
          <v-checkbox
            v-if="menuId != '9'"
            class="mr-2"
            label="공지사항 숨기기"
            color="secondary"
            v-model="hideNotice"
            @click="makeTopHide"
          ></v-checkbox>

          <v-btn
            v-tooltip="'내가 쓴 글 보기'"
            variant="flat"
            icon="mdi-book-open"
            color="lightsecondary"
            v-if="isTypeB"
            @click="viewMineBtn"
          ></v-btn>
          <router-link :to="{ path: `/insert`, query: { menuId: menuId, cls: currentClass, type: currentBoardType } }">
            <v-btn
              v-tooltip="'새글 작성'"
              :icon="PencilPlusIcon"
              variant="flat"
              color="lightsecondary"
            ></v-btn>
          </router-link>
        </div>
      </v-col>
      <div
        class="ml-5"
        v-if="menuId === '2010'"
      >
        <b>※ 게시물 등록 시 제안 양식에 맞춰서 작성 해주시기 바랍니다. 양식이 누락된 경우 답변이 불가합니다.</b>
      </div>
      <v-col
        cols="12"
        sm="6"
        class="py-1 mt-n5"
        v-if="isTypeB"
      >
        <v-btn
          :prepend-icon="CopyIcon"
          @click="changeProgress('')"
          :color="all == true ? 'lightsecondary' : ''"
          class="mr-2"
          variant="flat"
          >전체</v-btn
        >
        <v-btn
          :prepend-icon="ProgressHelpIcon"
          @click="changeProgress('P')"
          :color="pending == true ? 'lightsecondary' : ''"
          class="mr-2"
          variant="flat"
          >예정</v-btn
        >
        <v-btn
          :prepend-icon="ProgressBoltIcon"
          @click="changeProgress('O')"
          :color="ongoing == true ? 'lightsecondary' : ''"
          class="mr-2"
          variant="flat"
          >진행중</v-btn
        >
        <v-btn
          :prepend-icon="ProgressCheckIcon"
          @click="changeProgress('C')"
          :color="completed == true ? 'lightsecondary' : ''"
          class="mr-2"
          variant="flat"
          >완료</v-btn
        >
      </v-col>
    </v-row>

    <v-divider />

    <v-data-table-server
      v-model:items-per-page="boardParam.Size"
      :headers="currentBoardType === 'B' ? headerB : currentBoardType === 'G' ? headerG : headers"
      :items="items"
      :items-length="totalItems"
      :loading="loading"
      :search="boardParam.SearchText"
      :item-value="(item: any) => `${item.num}-${item.version}`"
      @update:options="loadItems"
      noDataText="검색 결과가 없습니다."
      itemsPerPageText=""
      density="comfortable"
    >
      <template v-slot:body="{ items }">
        <tr
          v-for="item in items"
          :key="item.boardId"
          :data-item-key="item.topOrder"
          v-show="showAll ? true : hideNotice && item.topOrder === 0 ? true : false"
        >
          <td>
            <div class="text-center">{{ item.num }}</div>
          </td>
          <td v-if="currentBoardType == 'B'">
            <div class="text-center">
              <v-chip
                :color="item.state === '완료' ? 'success' : item.state === '진행중' ? 'primary' : item.state === '예정' ? 'secondary' : 'none'"
                :text="item.state === '' ? '-' : item.state"
                size="small"
                variant="tonal"
                label
              ></v-chip>
            </div>
          </td>
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
                  :to="{ path: `/boardview/${item.boardId}`, query: { menuId: menuId } }"
                  class="mx-2 text-body-1"
                  :class="{ 'font-weight-bold': item.topOrder === 1 }"
                  :style="{ color: item.color === null ? '#000000' : item.color }"
                  ><span v-if="currentBoardType === 'A' || currentClass === 'G'">
                    <span class="notice">{{ item.headTitle && item.headTitle.length > 1 ? '[' + item.headTitle + ']' : '' }}</span>
                    {{ item.title }}
                  </span>
                  <span
                    v-else
                    v-html="item.title"
                  ></span>
                </router-link>
              </v-hover>

              <v-label v-if="item.replyCount && item.replyCount > 0">
                <v-chip
                  color="secondary"
                  variant="flat"
                  size="x-small"
                  class="ml-2 text-white"
                  prepend-icon="mdi-chat"
                  >{{ item.replyCount }}</v-chip
                >
              </v-label>

              <v-label v-if="item.fileCount && item.fileCount > 0">
                <v-chip
                  color="primary"
                  variant="flat"
                  size="x-small"
                  class="ml-2 text-white"
                  prepend-icon="mdi-download"
                  >{{ item.fileCount }}</v-chip
                >
              </v-label>
            </div>
          </td>
          <td>
            <div
              class="text-center reserve"
              v-if="item.reserveDate != null && currentBoardType === 'B'"
            >
              [ {{ item.reserveDate }} ,{{ item.reserveTime }} ]
            </div>
            <div
              class="text-center"
              v-else-if="menuId === '10002' && currentBoardType === 'B'"
            ></div>
            <div
              class="text-start"
              v-else-if="currentBoardType === 'G'"
            >
              {{ item.headTitle }}
            </div>
            <div
              class="text-center"
              v-else-if="currentBoardType === 'B'"
            >
              {{ item.departmentName }}
            </div>
            <div
              class="text-center"
              v-else
            >
              {{ item.departmentName }}
            </div>
          </td>
          <td class="text-center">
            {{ item.name }}
          </td>
          <td>
            <div class="text-center">{{ format(new Date(item.regDate), 'yyyy-MM-dd') }}</div>
          </td>
          <td class="text-end">
            {{ item.readCount }}
          </td>
        </tr>
      </template>
    </v-data-table-server>
  </UiEmptyTitleCard>
</template>

<style lang="css" scoped>
a {
  color: #000;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.notice {
  color: #ef42f5;
  font-weight: bold;
}

.reserve {
  color: purple;
  font-style: italic;
  font-weight: bold;
}

tr[data-item-key='1'] {
  background-color: #fcf0f4;
}
</style>
