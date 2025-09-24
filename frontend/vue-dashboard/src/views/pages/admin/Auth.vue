<script setup lang="ts">
import { ref, reactive, onMounted, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import UiEmptyTitleCard from '@/components/shared/UiEmptyTitleCard.vue'
import { KeyIcon } from 'vue-tabler-icons'
import { getAuthListCount, getAuthList, setAuth, setAuthDelete } from '@/apis/common'
import { clsChange } from '../board/Common'
import { getUserInfo } from '@/views/pages/board/Common'
import type { AuthParam, AuthList, AuthInsertParam, AuthDelParam } from '@/types/commonTypes/common'

//UserInfo
const { userId: loginId } = getUserInfo()

const route = useRoute()
const authCnt = ref<string[]>([])
const items = ref<AuthList[]>([])
const totalItems = ref<number>(0)
const getId = ref('')
const authPop = ref<boolean>(false)
const authType = ref('')
const authTitleName = ref('')

const cls = computed(() => route.params.cls)

const authTitle = ref<any>([
  { title: '기능 권한', key: 'A01' },
  { title: '게시판 권한', key: 'A02' },
  { title: '메뉴 권한', key: 'A03' },
  { title: '말머리 설정', key: 'A04' },
  { title: '게시물 관리', key: 'A05' },
  { title: '이모티콘 관리', key: 'A06' },
  { title: '관리자 권한', key: 'A07' },
  { title: '이관 권한', key: 'A08' },
  { title: '사용자 관리 권한', key: 'A09' },
  { title: '조직 관리 권한', key: 'A10' },
  { title: '필터 관리 권한', key: 'A11' },
  { title: '댓글 좋아요 권한', key: 'A12' },
])

const headers = ref<any>([
  { title: 'No.', key: 'rowNumber', align: 'start', sortable: false },
  { title: '사용자', key: 'userId', align: 'start', sortable: false },
  { title: '학교급', key: 'cls', align: 'center', sortable: false },
  { title: '권한삭제', key: '', align: 'end', sortable: false },
])

const authListParam: AuthParam = reactive({
  cls: cls.value.toString(),
  authType: '',
  userId: '',
  pageNo: 1,
  pageSize: 10,
})

const authInsertParam: AuthInsertParam = reactive({
  cls: cls.value.toString(),
  authType: '',
  userId: '',
  regUserId: loginId,
  returnCode: '',
})

const authDelParam: AuthDelParam = reactive({
  cls: cls.value.toString(),
  idx: 0,
})

function pop(type: string) {
  const foundItem = authTitle.value.find((item: { key: string }) => item.key === type)
  if (foundItem) {
    authTitleName.value = foundItem.title
  }

  authListParam.authType = type
  getAuthListByCls({ page: 1 })
  authPop.value = true
  authType.value = type //현재 오픈된 모달 authtype 저장
}

//아이디 텍스트
watch(getId, () => {
  authInsertParam.userId = getId.value
})

watch(cls, () => {
  getAuthCntList()
  getAuthListByCls({ page: 1 })
})

//기능권한 카운트
const getAuthCntList = async () => {
  try {
    const data: any = await getAuthListCount(cls.value.toString())

    if (data.value?.length > 0) {
      for (let i = 0; i < data.value.length; i++) {
        authCnt.value[i] = data.value[i].cnt.toString()
      }
    }
  } catch (e) {
    console.error(e)
  }
}

//권한 삭제
const delAuth = async (idx: number) => {
  try {
    authDelParam.idx = idx
    authDelParam.cls = cls.value.toString()
    const data: any = await setAuthDelete(authDelParam)

    if (data.value.success) {
      alert('권한이 삭제되었습니다.')
      getId.value = ''
      getAuthCntList()
    } else {
      alert('권한 삭제에 실패했습니다!')
    }
    getAuthListByCls({ page: 1 })
  } catch (e) {
    console.error(e)
    alert('권한 삭제에 실패했습니다.')
  }
}

// 권한부여
const authInsert = async () => {
  try {
    authInsertParam.userId = getId.value
    authInsertParam.authType = authType.value
    authInsertParam.cls = cls.value.toString()
    const data: any = await setAuth(authInsertParam)

    if (data.value.result === 'Y') {
      alert('권한이 부여되었습니다.')
      getId.value = ''
      getAuthCntList()
    } else if (data.value.result === 'N') {
      alert('사용자 아이디가 없습니다.')
    } else if (data.value.result === 'D') {
      alert('이미 권한이 있는 사용자 입니다.')
    }
    getAuthListByCls({ page: 1 })
  } catch (e) {
    console.error(e)
    alert('오류가 발생했습니다.')
  }
}

//권한 목록
const getAuthListByCls = async ({ page, itemsPerPage }: any) => {
  authListParam.pageNo = page || authListParam.pageNo + 1
  authListParam.pageSize = itemsPerPage || authListParam.pageSize

  try {
    authListParam.cls = cls.value.toString()
    const data: AuthList[] = await getAuthList(authListParam)

    if (data.length > 0) {
      items.value = data
      if (items.value?.length > 0) {
        const totalCnt = items.value[0].totalRowsNum
        totalItems.value = totalCnt
      }
    } else {
      items.value = []
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  getAuthCntList()
})
</script>

<template>
  <UiEmptyTitleCard>
    <v-row>
      <v-col>
        <div class="text-subtitle-1 font-weight-bold text-secondary">
          <KeyIcon
            size="15"
            class="mr-2"
          />기능 권한 관리
        </div>
        <v-divider class="mt-2" />
        <v-table density="comfortable">
          <thead>
            <tr class="text-subtitle-1">
              <th class="font-weight-bold">No.</th>
              <th class="font-weight-bold">권한명</th>
              <th class="font-weight-bold">권한 요약</th>
              <th class="font-weight-bold">권한 부여 시 현상</th>
              <th class="font-weight-bold">사용자</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>기능 권한</td>
              <td>익스트라넷 기능 권한을 관리하는 권한 (현재 페이지)</td>
              <td>익스트라넷 메뉴 권한 내 ‘기능 권한 관리‘ 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  size="default"
                  color="secondary"
                  @click="pop('A01')"
                  :text="authCnt[0]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>2</td>
              <td>게시판 권한</td>
              <td>메뉴를 추가/수정/삭제/이동하는 권한</td>
              <td>상단에 게시판 관리 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A02')"
                  size="default"
                  color="secondary"
                  :text="authCnt[1]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>3</td>
              <td>메뉴 권한</td>
              <td>사용자에게 메뉴를 부여하는 권한</td>
              <td>상단에 메뉴 권한 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A03')"
                  size="default"
                  color="secondary"
                  :text="authCnt[2]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>4</td>
              <td>말머리 설정</td>
              <td>게시판 말머리를 추가/삭제/수정하는 권한</td>
              <td>상단에 말머리 설정 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A04')"
                  size="default"
                  color="secondary"
                  :text="authCnt[3]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>5</td>
              <td>게시물 관리</td>
              <td>타사용자의 게시물을 수정/삭제하는 권한</td>
              <td>타사용자의 게시물을 수정/삭제 가능</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A05')"
                  size="default"
                  color="secondary"
                  :text="authCnt[4]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>6</td>
              <td>이모티콘 관리</td>
              <td>에디터 이모티콘을 관리할수 있는 권한</td>
              <td>에디터 이모티콘 등록/삭제 가능</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A06')"
                  size="default"
                  color="secondary"
                  :text="authCnt[5]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>7</td>
              <td>관리자 권한</td>
              <td>게시물 관리자 삭제/공지 제거 권한</td>
              <td>타사용자의 게시물 삭제 및 공지제거 가능</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A07')"
                  size="default"
                  color="secondary"
                  :text="authCnt[6]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>8</td>
              <td>이관 권한</td>
              <td>게시물 이관 권한</td>
              <td>타사용자의 게시물 이관 가능</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A08')"
                  size="default"
                  color="secondary"
                  :text="authCnt[7]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>9</td>
              <td>사용자 관리</td>
              <td>익스트라넷 사용자를 관리할 수 있는 권한</td>
              <td>상단에 사용자 관리 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A09')"
                  size="default"
                  color="secondary"
                  :text="authCnt[8]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>10</td>
              <td>조직 관리</td>
              <td>익스트라넷 조직을 관리할 수 있는 권한</td>
              <td>상단에 조직 관리 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A10')"
                  size="default"
                  color="secondary"
                  :text="authCnt[9]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>11</td>
              <td>필터 관리</td>
              <td>게시판내 필터기능 버튼/드롭다운 설정</td>
              <td>상단에 필터 관리 버튼 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A11')"
                  size="default"
                  color="secondary"
                  :text="authCnt[10]?.toString()"
                ></v-chip>
              </td>
            </tr>
            <tr>
              <td>12</td>
              <td>댓글 좋아요 권한</td>
              <td>게시글 작성시 댓글[좋아요] 기능 설정 가능 권한</td>
              <td>게시글 작성시 댓글[좋아요] 사용 체크박스 노출</td>
              <td style="text-align: center">
                <v-chip
                  class="text-body-2 font-weight-bold"
                  @click="pop('A12')"
                  size="default"
                  color="secondary"
                  :text="authCnt[11]?.toString()"
                ></v-chip>
              </td>
            </tr>
          </tbody>
        </v-table>
      </v-col>
    </v-row>

    <!--기능권한 사용자 모달-->
    <div class="text-center">
      <v-dialog
        v-model="authPop"
        width="550"
      >
        <v-container
          fluid
          class="page-wrapper"
        >
          <UiEmptyTitleCard>
            <v-card variant="flat">
              <div class="text-subtitle-1 font-weight-bold text-secondary d-inline-flex align-center justify-space-between w-100">
                <KeyIcon
                  size="15"
                  class="mr-2"
                />{{ authTitleName }} 사용자 목록() <v-spacer />
                <v-btn
                  icon="mdi-close"
                  size="small"
                  variant="flat"
                  @click="(authPop = false), (getId = '')"
                ></v-btn>
              </div>

              <v-divider class="mt-1 mb-3" />
              <v-row>
                <v-col cols="2"><div class="text-body-1 mt-2 ml-3">아이디</div></v-col>
                <v-col
                  cols="8"
                  class="pt-3 pl-0 pb-0"
                >
                  <v-text-field
                    type="text"
                    variant="outlined"
                    density="compact"
                    v-model="getId"
                    @update:modelValue="getAuthListByCls({ page: 1 })"
                    @keyup.enter="authInsert"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="2"
                  class="pl-0"
                >
                  <v-btn
                    size="small"
                    color="secondary"
                    class="mt-1"
                    variant="flat"
                    @click="authInsert"
                    >부여</v-btn
                  >
                </v-col>
              </v-row>

              <v-divider class="mb-1" />
              <div>
                <v-data-table-server
                  v-model:items-per-page="authListParam.pageSize"
                  :headers="headers"
                  :items="items"
                  :items-length="totalItems"
                  item-value="id"
                  @update:options="getAuthListByCls"
                  noDataText="검색 결과가 없습니다."
                  itemsPerPageText=""
                  density="compact"
                  fixed-header
                  height="400px"
                >
                  <template v-slot:body="{ items }">
                    <tr
                      v-for="item in items"
                      :key="item.rowNumber"
                    >
                      <td>{{ item.rowNumber }}</td>
                      <td class="text-start">{{ item.name }} / {{ item.userId }}</td>
                      <td class="text-center">{{ clsChange(item.cls) }}</td>

                      <td class="text-end">
                        <v-btn
                          size="small"
                          color="error"
                          variant="flat"
                          @click="delAuth(item.idx)"
                          >삭제</v-btn
                        >
                      </td>
                    </tr>
                  </template>
                </v-data-table-server>
              </div>
            </v-card>
          </UiEmptyTitleCard>
        </v-container>
      </v-dialog>
    </div>
  </UiEmptyTitleCard>
</template>
<style lang="css" scoped>
.v-table tbody tr:nth-child(odd) {
  background-color: #f2f2f2;
}
</style>
