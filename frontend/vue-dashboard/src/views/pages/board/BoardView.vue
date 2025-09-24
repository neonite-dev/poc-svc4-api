<script setup lang="ts">
import { computed, ref, reactive, onMounted } from 'vue'
import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue'
import UiEmptyTitleCard from '@/components/shared/UiEmptyTitleCard.vue'
import { getBoardView, increaseReadCount, getReaderList, insertComment, getBoardReplyList, replyDelete, boardDelete, reserveDelete, getFileList, setLike } from '@/apis/board'
import { useRoute } from 'vue-router'
import { useMenuStore } from '@/stores/menu'
import { storeToRefs } from 'pinia'
import { router } from '@/router'
import { PrinterIcon } from 'vue-tabler-icons'
import { formatBytes, convertDateFormat, getUserInfo } from '@/views/pages/board/Common'
import { useDateFormat, useNow } from '@vueuse/core'
import type { Board, BoardViewParam, ReaderList, CommentParam, CommentList, BoardDelParam, ReserveParam, BoardFileList, LikeParam } from '@/types/boardTypes/board'
import type { Menu } from '@/types/commonTypes/menu'

const route = useRoute()
const menuStore = useMenuStore()
const { menus, currentMenu, currentBoardType } = storeToRefs(menuStore)
const { query } = route
const pageMeta = ref({ title: '' })
const breadcrumbs = ref<any>([])

//UserInfo
const { cls: loginCls, userId: loginId, token: token, authYn: auth } = getUserInfo()

const pageMetaByMenuId = computed(() => {
  if (menus.value?.length > 0 && currentMenu.value?.menuName) {
    pageMeta.value.title = currentMenu.value.menuName
  }
  return pageMeta.value.title
})

const breadcrumbsByMenuId = computed(() => {
  breadcrumbs.value = [].concat([])
  if (menus.value?.length > 0 && currentMenu.value?.menuName != null) {
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

//게시글 내용 변수
const items = ref<Board[]>([])
const readerList = ref<ReaderList[]>([])
const cmtList = ref<CommentList[]>([])
const txt = ref('')
const readerOpenYN = ref(false)
const title = ref('')
const num = ref(0)
const regDate = ref('')
const content = ref('')
const writer = ref('')
const writerName = ref('')
const menuId = ref('')
const reserveDate = ref('')
const reserveTime = ref('')
const readCount = ref(0)
const attachedfiles = ref<BoardFileList[]>([])
const replyLike = ref(0)
const formatter = 'YYYYMMDDHHmmss'

//예약 변수 - 수정/삭제
const reserveInsertParam: ReserveParam = reactive({
  boardId: route.params.boardId.toString(),
  teacherId: loginId,
  reserveDate: reserveDate.value,
  reserveTime: reserveTime.value,
  idx: 0,
})

const boardViewParam: BoardViewParam = reactive({
  boardId: route.params.boardId.toString() || '',
  userId: loginId,
})

const cmtParam: CommentParam = reactive({
  boardId: route.params.boardId.toString(),
  writer: loginId,
  content: txt,
})

const delParam: BoardDelParam = reactive({
  menuId: Number(currentMenu.value.menuId),
  boardId: route.params.boardId.toString(),
  userId: loginId,
  cls: loginCls,
})

const setLikeParam: LikeParam = reactive({
  replyId: '',
  userId: loginId,
  likeYn: 0,
})

//게시글 내용
const boardView = async () => {
  try {
    const data: any = await getBoardView(boardViewParam)

    if (data.value?.length > 0) {
      items.value = [...data.value] as Board[]

      const [boardData] = items.value
      title.value = boardData.title
      content.value = boardData.content
      num.value = boardData.num
      regDate.value = boardData.regDate
      menuId.value = boardData.menuId
      writer.value = boardData.writer
      writerName.value = boardData.writerName
      replyLike.value = boardData.useReplyLike

      if (query.menuId === '10002') {
        reserveDate.value = boardData.reserveDate
        reserveTime.value = boardData.reserveTime
        readCount.value = boardData.readCount
      }
    }
  } catch (e) {
    console.error(e)
  }
}

//조회수 증가
const updateReadCnt = async () => {
  try {
    await increaseReadCount(boardViewParam)
  } catch (e) {
    console.error(e)
  }
}

//열람자 리스트
const readerLists = async () => {
  try {
    const data: any = await getReaderList(route.params.boardId.toString(), loginId)

    if (data.value?.length > 0) {
      readerList.value = [...data.value] as ReaderList[]
    }
  } catch (e) {
    console.error(e)
  }
}

//파일 첨부 리스트
const fileLists = async () => {
  try {
    const data: any = await getFileList(route.params.boardId.toString())

    if (data.value?.length > 0) {
      attachedfiles.value = [...data.value] as BoardFileList[]
    }
  } catch (e) {
    console.error(e)
  }
}

//파일 다운로드
async function download(filename: string, filepath: string) {
  try {
    const formData = new FormData()
    formData.append('fileName', filename)
    formData.append('filePath', filepath)

    const response = await fetch('http://localhost:8080/api/extra/board/downFile', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      method: 'POST',
      body: formData,
    })

    if (!response.ok) {
      alert('파일이 존재하지 않습니다!')
      return false
    }

    const blob = await response.blob()
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', useDateFormat(useNow(), formatter).value + '_' + filename)
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  } catch (error) {
    console.error(error)
    alert('다운로드에 실패했습니다')
  }
}

//댓글 리스트
const getComments = async () => {
  try {
    const data: any = await getBoardReplyList(route.params.boardId.toString(), loginId)
    cmtList.value = [...data.value] as CommentList[]
  } catch (e) {
    console.error(e)
  }
}

//댓글 등록
const addComment = async () => {
  try {
    if (txt.value.length == 0) {
      alert('댓글을 작성해주세요')
      return false
    }

    const data: any = await insertComment(cmtParam)

    if (data.value.success) {
      alert('댓글이 등록되었습니다.')
      getComments()
      txt.value = ''
    } else {
      alert('댓글 등록에 실패했습니다!')
    }
  } catch (e) {
    console.error(e)
    alert('댓글 등록에 실패했습니다.')
  }
}

//댓글 삭제
const delComment = async (replyId: string) => {
  try {
    const data: any = await replyDelete(replyId)

    if (data.value.success) {
      alert('댓글이 삭제되었습니다.')
      getComments()
    } else {
      alert('댓글 삭제에 실패했습니다!')
    }
  } catch (e) {
    console.error(e)
    alert('댓글 삭제에 실패했습니다.')
  }
}

//댓글좋아요
const makeLike = async (like: number, replyId: string) => {
  try {
    if (like == 1) {
      setLikeParam.likeYn = 0
    } else {
      setLikeParam.likeYn = 1
    }
    setLikeParam.replyId = replyId

    await setLike(setLikeParam)
    getComments()
  } catch (e) {
    console.error(e)
  }
}

//예약삭제
const delReserve = async () => {
  try {
    await reserveDelete(reserveInsertParam)
  } catch (e) {
    console.error(e)
    alert('예약삭제에 실패했습니다.')
  }
}

//글 삭제
const delBoard = async () => {
  if (auth || writer.value === loginId) {
    try {
      const data: any = await boardDelete(delParam)

      if (data.value.success) {
        if (query.menuId === '10002') {
          reserveInsertParam.reserveDate = reserveDate.value
          reserveInsertParam.reserveTime = reserveTime.value

          delReserve()
        }
        alert('글이 삭제되었습니다.')

        router.push({
          name: 'BoardList',
          params: {
            menuId: delParam.menuId,
          },
        })
      } else {
        alert('글 삭제에 실패했습니다!')
      }
    } catch (e) {
      console.error(e)
      alert('글 삭제에 실패했습니다.')
    }
  } else {
    alert('작성자 본인만 삭제할 수 있습니다.')
  }
}

onMounted(() => {
  boardView()
  updateReadCnt()
  readerLists()
  getComments()
  fileLists()
})

//functions
//////////////////////////////////////////////////////

function writerCheck(boardId: string) {
  if (auth || writer.value === loginId) {
    router.push({ path: '/update/' + boardId, query: { menuId: query.menuId } })
  } else {
    alert('작성자만 수정할 수 있습니다.')
    return false
  }
}

function openReaderList() {
  readerOpenYN.value = !readerOpenYN.value
}
</script>

<template>
  <BaseBreadcrumb
    :title="pageMetaByMenuId"
    :breadcrumbs="breadcrumbsByMenuId"
  ></BaseBreadcrumb>

  <UiEmptyTitleCard>
    <div
      id="printSection"
      class="printStyle"
    >
      <v-row
        justify="center"
        class="justify-space-between mb-0"
      >
        <v-col>
          <span>
            <h2>{{ num }}. {{ title }}</h2>
            <span>{{ convertDateFormat(regDate) }} - {{ writerName }} ({{ writer }})</span>
          </span>
          <span>
            <v-chip
              v-if="query.menuId === '10002'"
              color="lightsecondary"
              variant="flat"
              size="small"
              class="ml-2"
              >{{ readCount }}
            </v-chip>
            <v-chip
              v-if="query.menuId === '10002' && reserveDate"
              color="lightprimary"
              variant="flat"
              size="small"
              class="ml-2 text-white"
              >예약일시: {{ reserveDate }} , {{ reserveTime }}
            </v-chip>
          </span>
        </v-col>

        <v-col
          cols="auto"
          class="text-right"
        >
          <v-btn
            size="small"
            class="mr-2 print"
            :icon="PrinterIcon"
            v-print="'#printSection'"
            variant="flat"
            color="#ba3d9b"
            rounded="sm"
          ></v-btn>

          <router-link :to="{ path: `/reply/${boardViewParam.boardId}`, query: { menuId: query.menuId } }">
            <v-btn
              size="small"
              color="#ba3d9b"
              class="mr-2 noPrint"
              variant="flat"
              >답변</v-btn
            >
          </router-link>

          <v-btn
            size="small"
            color="#ba3d9b"
            class="mr-2 noPrint"
            variant="flat"
            @click="writerCheck(boardViewParam.boardId)"
            >수정</v-btn
          >

          <v-btn
            size="small"
            color="secondary"
            class="mr-2 noPrint"
            @click="delBoard"
            variant="flat"
            >삭제</v-btn
          >
          <router-link :to="{ path: `/board/${query.menuId}` }">
            <v-btn
              size="small"
              color="#ba3d9b"
              class="mr-2 noPrint"
              variant="flat"
              >목록</v-btn
            >
          </router-link>
        </v-col>
      </v-row>
      <v-divider class="mb-3" />
      <v-row justify="space-around">
        <v-col
          cols="8"
          class="contentMargin"
        >
          <div
            class="ck-content"
            v-html="content"
          ></div>
        </v-col>
      </v-row>
    </div>

    <div v-if="attachedfiles.length > 0">
      <div class="rowLine"></div>
      <v-row justify="space-around">
        <v-col cols="8"
          ><div>
            <v-row>
              <v-col cols="12">
                <span
                  v-for="fileitems in attachedfiles"
                  :key="fileitems.fileid"
                >
                  <v-btn
                    prepend-icon="mdi-arrow-down-box"
                    variant="tonal"
                    size="small"
                    class="filebtn mb-1"
                    @click="download(fileitems.filename, fileitems.filepath)"
                    >{{ fileitems.filename }}&nbsp;({{ formatBytes(fileitems.filebytes) }})</v-btn
                  >
                </span>
              </v-col>
            </v-row>
          </div>
        </v-col>
      </v-row>
    </div>

    <div v-if="currentBoardType != 'G'">
      <v-row justify="space-around">
        <v-col cols="8">
          <v-divider class="mb-2" />
          <v-btn
            size="small"
            color="secondary"
            class="mr-2"
            @click="openReaderList"
            variant="flat"
            >열람자 안내</v-btn
          >
          <v-divider class="mt-2" />
        </v-col>
      </v-row>
    </div>
    <div v-else>
      <v-row justify="space-around">
        <v-col cols="8">
          <v-divider class="mt-2" />
        </v-col>
      </v-row>
    </div>

    <v-row justify="space-around">
      <v-col cols="8">
        <div v-if="readerOpenYN">
          <v-list
            style="margin-block-end: -10px"
            density="compact"
            nav
          >
            <v-list-item
              density="compact"
              v-for="readers in readerList"
              class="list-item"
              :key="readers.boardId"
            >
              <v-list-item-title style="font-size: 14px"
                >{{ readers.reader }} 님께서 {{ convertDateFormat(readers.readTime) }} 에 최초 열람하셨습니다 -- 열람 횟수 {{ readers.readCount }}</v-list-item-title
              >
            </v-list-item>
          </v-list>
        </div>
      </v-col>
    </v-row>

    <!--댓글-->
    <v-row
      justify="space-around"
      dense
    >
      <v-col
        cols="8"
        v-for="cmt in cmtList"
        :key="cmt.replyId"
      >
        <v-card
          elevation="0"
          class="bg-lightsecondary"
        >
          <v-card-text class="pl-3 pr-3 pt-2 pb-2">
            <div class="d-flex align-center">
              <v-row>
                <v-col
                  cols="2"
                  class="pr-0"
                >
                  <div style="border-inline-end: 1px solid lightgray">
                    <h4 class="text-h5 font-weight-medium">{{ cmt.writerName }} {{ '(' + cmt.writer + ') ' }}</h4>
                    <span class="text-subtitle-2 text-disabled font-weight-medium">{{ convertDateFormat(cmt.regdate) }}</span>
                  </div>
                </v-col>

                <v-col
                  cols="9"
                  class="pl-2"
                >
                  <div class="ml-2">{{ cmt.content }}</div>
                </v-col>

                <!--댓글 좋아요 선택시-->
                <v-col cols="1">
                  <div class="d-flex justify-end">
                    <v-menu v-if="replyLike === 1">
                      <template #activator="{ props }">
                        <v-chip
                          v-if="loginId != cmt.writer && cmt.ismylike === 1"
                          color="secondary"
                          size="small"
                          class="text-white text-h5"
                          @click="makeLike(1, cmt.replyId)"
                          append-icon="mdi-heart"
                          v-bind="props"
                          >{{ cmt.totallikecnt }}</v-chip
                        >
                        <v-chip
                          v-else-if="loginId != cmt.writer && cmt.ismylike === 0"
                          color="secondary"
                          size="small"
                          class="text-white text-h5"
                          @click="makeLike(0, cmt.replyId)"
                          append-icon="mdi-heart-outline"
                          v-bind="props"
                          >{{ cmt.totallikecnt }}</v-chip
                        >
                        <v-chip
                          v-else
                          color="secondary"
                          size="small"
                          class="text-white text-h5"
                          v-bind="props"
                          >{{ cmt.totallikecnt }}</v-chip
                        >
                      </template>
                    </v-menu>

                    <v-menu v-if="cmt.rwriter == loginId">
                      <template #activator="{ props }">
                        <v-btn
                          icon="mdi-close"
                          rounded="sm"
                          color="secondary"
                          variant="flat"
                          size="compact"
                          v-bind="props"
                          v-tooltip="'댓글삭제'"
                          @click="delComment(cmt.replyId)"
                          class="ml-1"
                        >
                        </v-btn> </template
                    ></v-menu>
                  </div>
                </v-col>
              </v-row>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row justify="space-around">
      <v-col cols="8">
        <div>
          <v-card
            elevation="0"
            class="bg-lightsecondary"
          >
            <v-textarea
              class="pl-3 pr-3"
              append-icon="mdi-check"
              v-model="txt"
              label="댓글"
              rows="2"
              @click:append="addComment"
              clearable
              no-resize
              variant="plain"
            ></v-textarea>
          </v-card>
        </div>
      </v-col>
    </v-row>
  </UiEmptyTitleCard>
</template>

<style lang="css" scoped>
a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  color: #0056b3 !important;
  text-decoration: underline;
}

.contentMargin {
  margin-block: 10px 30px;
}

.list-item {
  margin-block: -20px;
  margin-block-end: 1px;
  margin-inline-start: -10px;
}

.filebtn {
  margin-block: 0;
  margin-inline: 5px;
}

.v-textarea {
  font-size: 14px;
}

@media print {
  .printStyle {
    font-size: 10px;
  }

  .noPrint {
    display: none;
  }
}
</style>
