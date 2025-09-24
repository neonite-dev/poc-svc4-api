<script setup lang="ts">
import { computed, onMounted, ref, watch, reactive } from 'vue'
import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue'
import UiEmptyTitleCard from '@/components/shared/UiEmptyTitleCard.vue'
import { useRoute } from 'vue-router'
import { useMenuStore } from '@/stores/menu'
import { storeToRefs } from 'pinia'
import { router } from '@/router'
import { CatIcon, PaletteIcon } from 'vue-tabler-icons'
import ColorPicker from 'primevue/colorpicker'
import DatePicker from 'primevue/datepicker'
import FileUpload from 'primevue/fileupload'
import { content1, content2 } from '@/views/pages/board/tableContent'
import { convertDate, convertDateFormat, formatBytes, getUserInfo } from '@/views/pages/board/Common'
import {
  SimpleUploadAdapter,
  ClassicEditor,
  AccessibilityHelp,
  Alignment,
  Autoformat,
  Autosave,
  BlockQuote,
  Bold,
  CloudServices,
  Essentials,
  FontBackgroundColor,
  FontColor,
  FontFamily,
  FontSize,
  GeneralHtmlSupport,
  Highlight,
  HorizontalLine,
  ImageBlock,
  ImageCaption,
  ImageInline,
  ImageResize,
  ImageStyle,
  ImageTextAlternative,
  ImageToolbar,
  ImageUpload,
  Indent,
  IndentBlock,
  Italic,
  Link,
  LinkImage,
  List,
  ListProperties,
  MediaEmbed,
  Paragraph,
  PasteFromOffice,
  RemoveFormat,
  SelectAll,
  SpecialCharacters,
  SpecialCharactersArrows,
  SpecialCharactersCurrency,
  SpecialCharactersEssentials,
  SpecialCharactersLatin,
  SpecialCharactersMathematical,
  SpecialCharactersText,
  Strikethrough,
  Subscript,
  Superscript,
  Table,
  TableCaption,
  TableCellProperties,
  TableColumnResize,
  TableProperties,
  TableToolbar,
  TextTransformation,
  TodoList,
  Underline,
  Undo,
  type Writer,
} from 'ckeditor5'
import translations from 'ckeditor5/translations/ko.js'
import {
  getHeadTitle,
  insertBoard,
  getIconList,
  getBoardView,
  boardUpdate,
  boardReply,
  boardIconDelete,
  getReservePossibleTimeList,
  reserveInsert,
  reserveUpdate,
  getFileList,
  boardFileDelete,
  likeThisAuth,
} from '@/apis/board'
import type { MenuName, Board, BoardViewParam, IconList, BoardList, ReserveParam, BoardFileList } from '@/types/boardTypes/board'
import type { Menu } from '@/types/commonTypes/menu'

//UserInfo
const { cls: loginCls, userId: loginId, name: loginName, token: token, authYn: auth } = getUserInfo()

//헤드메뉴 변수
const route = useRoute()
const menuStore = useMenuStore()
const { menus, currentMenu, currentBoardType } = storeToRefs(menuStore)
const { query } = route
const pageMeta = ref({ title: '' })
const breadcrumbs = ref<any>([])

//분류
const isEdit = computed(() => route.path.includes('update')).value
const isWrite = computed(() => route.path.includes('insert')).value
const isReply = computed(() => route.path.includes('reply')).value
const boardViewItems = ref<Board[]>([])
const options = ref<MenuName[]>([])
const selectedValue = ref('말머리선택')
const headtitleParam: any = reactive({
  menuId: query.menuId,
})

//파일 업로드
const formData = new FormData()
const fileYn = ref<boolean>(false)

const passThrough = {
  content: { style: { width: '1100px', padding: '5px' } },
  root: { style: { border: 'none' } },
  file: { style: { padding: '10px' }, pcBadge: { root: { style: { display: 'none' } } } },
  header: { style: { padding: '5px' } },
}

const cancelBtnStyle = {
  style: {
    backgroundColor: '#ba3d9b',
    border: 'none',
  },
}
//예약 변수
const writerCheck = ref('')

//아이콘 변수
const iconPopup = ref(false)
const iconList = ref<IconList[]>([])

//수정 변수
const selectedHeadtitle = ref('')

//TOP으로 올리기
const topCheck = ref<boolean>(false)

//댓글 좋아요
const likeCheck = ref<boolean>(false)
const likeAuth = ref<boolean>(false)

//답글 변수
const boardRegDate = ref('')
const boardWriter = ref('')
const boardWriterName = ref('')
const boardContent = ref('')

//원글 변수
const boardViewParam: BoardViewParam = reactive({
  boardId: route.params.boardId ? route.params.boardId.toString() : '',
  userId: loginId,
})

//DatePicker
const dateValue = ref<Date>(new Date())
const convertedDate = ref(convertDate(dateValue.value))
const timeValue = ref('13:00')
const reserveList = ref<BoardList[]>([])

//ColorPicker
const colorPickerShow = ref<boolean>(false)
const colorValue = ref('')
const colorPick = computed(() => {
  if (query.menuId === '10002') {
    return 'colorPicker2'
  } else {
    return 'colorPicker'
  }
})

//Placeholder
const formContent1 = ref(content1)
const formContent2 = ref(content2)

//첨부파일 변수
const attachedfiles = ref<BoardFileList[]>([])

//헤드메뉴
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

//Ckeditor5
const editorContent: any = ref('')
const isLayoutReady = ref(false)
const config: any = ref(null)
const editor = ClassicEditor
const editorInstance: any = ref('')

function initializeEditorConfig() {
  config.value = {
    toolbar: {
      items: [
        'fontFamily',
        'fontSize',
        'fontColor',
        'fontBackgroundColor',
        'alignment',
        '|',
        'bold',
        'italic',
        'underline',
        'blockQuote',
        'bulletedList',
        'numberedList',
        'todoList',
        '|',
        'indent',
        'outdent',
        '|',
        'specialCharacters',
        'strikethrough',
        'removeFormat',
        'subscript',
        'superscript',
        'horizontalLine',
        'highlight',
        '|',
        'imageUpload',
        'insertTable',
        'mediaEmbed',
        'link',
        '|',
        'undo',
        'redo',
      ],
      shouldNotGroupWhenFull: false,
    },
    simpleUpload: {
      uploadUrl: '/api/extra/board/imageUpload',
      withCredentials: true,
      encodeUrl: false,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
    plugins: [
      SimpleUploadAdapter,
      AccessibilityHelp,
      Alignment,
      Autoformat,
      Autosave,
      BlockQuote,
      Bold,
      CloudServices,
      Essentials,
      FontBackgroundColor,
      FontColor,
      FontFamily,
      FontSize,
      GeneralHtmlSupport,
      Highlight,
      HorizontalLine,
      ImageBlock,
      ImageCaption,
      ImageInline,
      ImageResize,
      ImageStyle,
      ImageTextAlternative,
      ImageToolbar,
      ImageUpload,
      Indent,
      IndentBlock,
      Italic,
      Link,
      LinkImage,
      List,
      ListProperties,
      MediaEmbed,
      Paragraph,
      PasteFromOffice,
      RemoveFormat,
      SelectAll,
      SpecialCharacters,
      SpecialCharactersArrows,
      SpecialCharactersCurrency,
      SpecialCharactersEssentials,
      SpecialCharactersLatin,
      SpecialCharactersMathematical,
      SpecialCharactersText,
      Strikethrough,
      Subscript,
      Superscript,
      Table,
      TableCaption,
      TableCellProperties,
      TableColumnResize,
      TableProperties,
      TableToolbar,
      TextTransformation,
      TodoList,
      Underline,
      Undo,
    ],
    fontFamily: {
      supportAllValues: true,
    },
    fontSize: {
      options: [10, 12, 14, 'default', 18, 20, 22],
      supportAllValues: true,
    },
    htmlSupport: {
      allow: [
        {
          name: /^.*$/,
          styles: true,
          attributes: true,
          classes: true,
        },
      ],
    },
    image: {
      resize: true,
      resizeOptions: [
        {
          name: 'imageResize:original',
          label: 'Original',
          value: null,
        },
        {
          name: 'imageResize:50',
          label: '50%',
          value: '50',
        },
        {
          name: 'imageResize:75',
          label: '75%',
          value: '75',
        },
      ],
      toolbar: ['imageStyle:alignLeft', 'imageStyle:alignCenter', 'imageStyle:alignRight', '|', 'imageResize', '|', 'imageTextAlternative'],
    },
    placeholder: '',
    language: 'ko',
    link: {
      addTargetToExternalLinks: true,
      defaultProtocol: 'https://',
      decorators: {
        toggleDownloadable: {
          mode: 'manual',
          label: 'Downloadable',
          attributes: {
            download: 'file',
          },
        },
      },
    },
    list: {
      properties: {
        styles: true,
        startIndex: true,
        reversed: true,
      },
    },
    table: {
      contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties'],
    },
    translations: [translations],
  }
  isLayoutReady.value = true
}

onMounted(() => {
  initializeEditorConfig()
  loadItems()
  getIcons()
  getPossibleTime()
  if (isEdit) {
    fileLists()
  }
  getlikeAuth()
})

//인서트 변수
const boardInsertParam: any = reactive({
  menuId: query.menuId,
  writer: loginId,
  color: colorValue.value ? colorValue.value : '',
  state: currentBoardType.value === 'B' && isReply === true ? '완료' : currentBoardType.value === 'B' && isReply === false ? '예정' : '',
  topOrder: topCheck.value ? 1 : 0,
  gubun1: '',
  gubun2: '',
  useReplyLike: 0,
  cls: loginCls,
  userId: loginId,
  boardId: route.params.boardId ? route.params.boardId.toString() : '',
  idx: 0,
  headTitle: selectedHeadtitle.value ? selectedHeadtitle.value : '',
})

//예약 변수
const reserveInsertParam: ReserveParam = reactive({
  boardId: '',
  teacherId: loginId,
  reserveDate: convertedDate.value ? convertedDate.value : '',
  reserveTime: timeValue.value ? timeValue.value : '',
  idx: 0,
})

//댓글좋아요 권한
const getlikeAuth = async () => {
  try {
    const data: any = await likeThisAuth(loginId, loginCls)

    if (data.value.success) {
      likeAuth.value = data.value.auth
    }
  } catch (e) {
    console.error(e)
  }
}

//말머리 셀렉션
const loadItems = async () => {
  try {
    const data: any = await getHeadTitle(headtitleParam)

    if (data.value?.length > 0) {
      options.value = [...data.value] as MenuName[]
    }
  } catch (e) {
    console.error(e)
  }
}

//예약가능 시간
const getPossibleTime = async () => {
  try {
    const data: any = await getReservePossibleTimeList(reserveInsertParam)

    if (data.value?.length > 0) {
      reserveList.value = [...data.value] as BoardList[]
    }
  } catch (e) {
    console.error(e)
  }
}

//예약추가
const addReserve = async () => {
  try {
    await reserveInsert(reserveInsertParam)
  } catch (e) {
    console.error(e)
    alert('예약에 실패했습니다.')
  }
}

//예약수정
const updateReserve = async () => {
  try {
    if (loginId != writerCheck.value) {
      alert('작성자만 예약시간 수정이 가능합니다.')
      return false
    } else {
      await reserveUpdate(reserveInsertParam)
    }
  } catch (e) {
    console.error(e)
    alert('예약수정에 실패했습니다.')
  }
}

//첨부파일 등록
async function upload() {
  await fetch('http://localhost:8080/api/extra/board/insertBoardFile', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    method: 'POST',
    body: formData,
  })
}

// 글작성
const writeBoard = async () => {
  try {
    const data: any = await insertBoard(boardInsertParam)

    if (data.value.success) {
      reserveInsertParam.boardId = data.value.boardId
      formData.append('boardId', data.value.boardId)
      addReserve()
      if (fileYn.value === true) {
        upload()
      } //첨부파일 등록

      alert('게시글이 등록되었습니다.')
      router.push({ path: `/boardview/${data.value.boardId}`, query: { menuId: data.value.menuId } })
    } else {
      alert('게시글 등록에 실패했습니다!')
    }
  } catch (e) {
    console.error(e)
    alert('게시글 등록에 실패했습니다.')
  }
}

// 글수정
const updateBoard = async () => {
  if (auth || boardWriter.value === loginId) {
    try {
      const data: any = await boardUpdate(boardInsertParam)
      reserveInsertParam.boardId = route.params.boardId.toString()
      if (data.value.success) {
        updateReserve()
        if (fileYn.value === true) {
          upload()
        }
        alert('게시글이 수정되었습니다.')
        router.push({ path: `/boardview/${data.value.boardId}`, query: { menuId: data.value.menuId } })
      } else {
        alert('게시글 수정에 실패했습니다!')
      }
    } catch (e) {
      console.error(e)
      alert('게시글 수정에 실패했습니다.')
    }
  } else {
    alert('수정 권한이 없습니다!')
  }
}

// 답글작성
const replyBoard = async () => {
  try {
    const data: any = await boardReply(boardInsertParam)

    if (data.value.success) {
      formData.append('boardId', data.value.reBoardId)
      if (fileYn.value === true) {
        upload()
      }

      alert('답글이 등록되었습니다.')
      router.push({ path: `/boardview/${data.value.reBoardId}`, query: { menuId: data.value.menuId } })
    } else {
      alert('답글 등록에 실패했습니다!')
    }
  } catch (e) {
    console.error(e)
    alert('답글 등록에 실패했습니다.')
  }
}

//원 게시글 내용
const boardView = async () => {
  try {
    const data: any = await getBoardView(boardViewParam)

    if (data.value?.length > 0) {
      boardViewItems.value = [...data.value] as Board[]

      const [boardData] = boardViewItems.value
      selectedHeadtitle.value = boardData.headTitle.length > 1 ? boardData.headTitle : '말머리선택'
      boardInsertParam.title = boardData.title
      topCheck.value = boardData.topOrder === 1 ? true : false
      colorValue.value = boardData.color
      boardInsertParam.color = boardData.color
      timeValue.value = boardData.reserveTime
      dateValue.value = new Date(boardData.reserveDate)
      likeCheck.value = boardData.useReplyLike === 1 ? true : false
      writerCheck.value = boardData.writer

      if (isReply) {
        editorContent.value = ''
        boardRegDate.value = boardData.regDate
        boardWriterName.value = boardData.writerName
        boardWriter.value = boardData.writer
        boardContent.value = boardData.content
        likeCheck.value = false
      } else {
        editorContent.value = boardData.content
        boardWriter.value = boardData.writer
      }
    }
  } catch (e) {
    console.error(e)
  }
}

if (isEdit || isReply) {
  boardView()
}

//아이콘 리스트
const getIcons = async () => {
  try {
    const data: any = await getIconList()

    if (data.value?.length > 0) {
      iconList.value = [...data.value] as IconList[]
    }
  } catch (e) {
    console.error(e)
  }
}

//아이콘 등록
async function uploadIcon() {
  const result = await fetch('http://localhost:8080/api/extra/board/addIcon', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    method: 'POST',
    body: formData,
  })

  if (result.status === 200) {
    getIcons()
  }
}

// 아이콘 리스트 아이콘 삭제
const iconDelete = async (idx: number) => {
  try {
    boardInsertParam.idx = idx
    const data: any = await boardIconDelete(boardInsertParam)

    if (data.value.success) {
      alert('아이콘이 삭제되었습니다.')
      getIcons()
    } else {
      alert('아이콘 삭제에 실패했습니다!')
    }
  } catch (e) {
    console.error(e)
    alert('아이콘 삭제에 실패했습니다.')
  }
}

//아이콘 업로드
const iconUpload = async (event: any) => {
  const iconFile = event.files

  if (iconFile.length > 0) {
    iconFile.forEach((file: File) => {
      formData.append('attachedFileIcon', file)
    })
    formData.append('loginId', loginId)
    uploadIcon()
  }
}

//파일 첨부 리스트
const fileLists = async () => {
  try {
    const data: any = await getFileList(route.params.boardId.toString())
    attachedfiles.value = [...data.value] as BoardFileList[]
    //데이터 0개여도 리스트 불러와야함
  } catch (e) {
    console.error(e)
  }
}

//첨부파일 삭제
const delFileItem = async (fileitemId: string) => {
  const confirmYN = confirm('첨부파일을 삭제하시겠습니까?')
  if (confirmYN) {
    try {
      const data: any = await boardFileDelete(fileitemId)

      if (data.value.success) {
        alert('첨부파일이 삭제되었습니다.')
        fileLists()
      }
    } catch (e) {
      console.error(e)
      alert('첨부파일 삭제에 실패했습니다.')
    }
  } else {
    return false
  }
}

//파일 업로드
const onAdvancedUpload = async (event: any) => {
  const files = event.files

  if (files.length > 0) {
    files.forEach((file: File) => {
      formData.append('attachedFile', file)
    })
    isEdit ? formData.append('boardId', route.params.boardId.toString()) : ''
    formData.append('loginId', loginId)
    fileYn.value = true
  }
}

//Watchers
//////////////////////////////////////////////////////

//예약일자
watch(dateValue, (newValue: Date) => {
  if (query.menuId === '10002') {
    reserveInsertParam.reserveDate = convertDate(newValue)
    dateCheck()
    getPossibleTime()
  }
})

//예약시간
watch(timeValue, (newValue: string) => {
  reserveInsertParam.reserveTime = newValue
})

//제목색상 저장
watch(colorValue, (newValue: string) => {
  if (colorValue.value != null) {
    if (colorValue.value.includes('#')) {
      boardInsertParam.color = newValue
    } else {
      boardInsertParam.color = '#' + newValue
    }
  }
})

//공지사항
watch(topCheck, (newValue: boolean) => {
  boardInsertParam.topOrder = newValue === true ? 1 : 0
})

//댓글 좋아요
watch(likeCheck, (newValue: boolean) => {
  boardInsertParam.useReplyLike = newValue === true ? 1 : 0
})

//일반 글쓰기 말머리
watch(selectedValue, () => {
  boardInsertParam.headTitle = selectedValue.value
})

//수정,답변시 말머리
watch(selectedHeadtitle, () => {
  boardInsertParam.headTitle = selectedHeadtitle.value === '말머리선택' ? '' : selectedHeadtitle.value
})

//ckeditor 내용
watch(editorContent, (newValue: string) => {
  boardInsertParam.content = newValue
})

//functions
//////////////////////////////////////////////////////
function dateCheck() {
  const today = new Date()
  today.setDate(today.getDate() - 1)

  const realToday = new Date()
  const isPast = dateValue.value < today
  if (isWrite && isPast) {
    alert('과거의 날짜는 선택할수없습니다.')
    dateValue.value = realToday
    return false
  }
}

if (query.menuId == '2010') {
  editorContent.value = formContent1.value
} else if (query.menuId == '331') {
  editorContent.value = formContent2.value
}

function showColorPicker() {
  colorPickerShow.value = !colorPickerShow.value
}

function cancelBtn() {
  history.back()
}

//이미지 삽입
function insertImage(imageSrc: any) {
  if (editorInstance.value) {
    editorInstance.value.model.change((writer: Writer) => {
      const imageElement = writer.createElement('imageBlock', {
        src: imageSrc,
      })

      editorInstance.value.model.insertContent(imageElement, editorInstance.value.model.document.selection)
    })
  }
}

function onEditorReady(editor: any) {
  editorInstance.value = editor
}

//공지사항
function makeTop() {
  topCheck.value = topCheck.value == true ? false : true
  boardInsertParam.topOrder = topCheck.value == true ? 1 : 0
}

//댓글 좋아요
function isLike() {
  likeCheck.value = likeCheck.value == true ? false : true
  boardInsertParam.useReplyLike = likeCheck.value == true ? 1 : 0
}
</script>

<template>
  <BaseBreadcrumb
    :title="pageMetaByMenuId"
    :breadcrumbs="breadcrumbsByMenuId"
  ></BaseBreadcrumb>

  <UiEmptyTitleCard>
    <v-row>
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 작성자 </v-sheet>
      </v-col>
      <v-col cols="4">
        <v-sheet class="pa-2 ma-1"> {{ loginName }}({{ loginId }}) </v-sheet>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 말머리 </v-sheet>
      </v-col>
      <v-col cols="4"
        ><!--글쓰기-->
        <v-select
          v-if="isWrite"
          density="compact"
          v-model="selectedValue"
          variant="outlined"
          class="custom-text-field"
          :items="options"
          item-title="headTitle"
          item-value="headTitle"
        ></v-select>
        <!--수정-->
        <v-select
          v-else-if="isEdit || isReply"
          density="compact"
          v-model="selectedHeadtitle"
          variant="outlined"
          class="custom-text-field"
          :items="options"
          item-title="headTitle"
          item-value="headTitle"
        ></v-select>
      </v-col>
      <v-col cols="2">
        <v-checkbox
          density="compact"
          v-model="topCheck"
          label="TOP으로 올리기"
          color="secondary"
          @click="makeTop"
        ></v-checkbox
      ></v-col>
    </v-row>

    <!--아이콘 모달-->
    <div class="text-center">
      <v-dialog
        v-model="iconPopup"
        width="700"
      >
        <v-card title="아이콘 삽입">
          <v-divider />
          <ul class="mt-5 mb-5 mr-0 ml-5 gallery">
            <li
              v-for="icons in iconList"
              class="gallery"
              :key="icons.idx"
            >
              <img
                :src="icons.src"
                class="milkt_icon"
                @click="insertImage(icons.src)"
              />

              <v-chip
                href=""
                @click="iconDelete(icons.idx)"
                class="removeList"
                variant="flat"
                color="primary"
                size="small"
                >X</v-chip
              >
            </li>
          </ul>
          <v-divider />

          <v-card class="align-left">
            <FileUpload
              @select="iconUpload"
              name="attachedFileIcon"
              :maxFileSize="1000000"
              chooseLabel="아이콘 추가"
              :show-upload-button="false"
              :show-cancel-button="false"
              class="mt-3 mb-3 ml-3 mr-3 p-button-secondary"
              style="font-size: 12px"
              mode="basic"
            >
            </FileUpload>
          </v-card>

          <template v-slot:append>
            <v-btn
              icon="mdi-close"
              size="small"
              variant="flat"
              @click="iconPopup = false"
            ></v-btn>
          </template>
        </v-card>
      </v-dialog>
    </div>

    <v-row v-if="query.menuId === '10002' && !isReply">
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 예약하기 </v-sheet>
      </v-col>

      <v-col cols="2">
        <DatePicker
          v-model="dateValue"
          date-format="yy-mm-dd"
          showIcon
          fluid
          :show-on-focus="false"
        />
      </v-col>
      <v-col cols="2">
        <v-select
          v-model="timeValue"
          density="compact"
          variant="outlined"
          class="custom-text-field"
          :items="reserveList"
          item-title="reserveTime"
          item-value="reserveTime"
        ></v-select>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 제목 </v-sheet>
      </v-col>
      <v-col cols="5">
        <v-text-field
          v-if="isWrite"
          type="text"
          variant="outlined"
          density="compact"
          v-model="boardInsertParam.title"
        ></v-text-field>

        <v-text-field
          v-else-if="isEdit || isReply"
          type="text"
          variant="outlined"
          density="compact"
          v-model="boardInsertParam.title"
        ></v-text-field>
      </v-col>

      <v-col cols="4">
        <v-btn
          class="mr-2"
          :icon="CatIcon"
          @click="iconPopup = true"
          rounded="sm"
          variant="flat"
          color="lightsecondary"
          v-tooltip="'아이콘 삽입'"
        ></v-btn>

        <v-btn
          :icon="PaletteIcon"
          @click="showColorPicker"
          rounded="sm"
          variant="flat"
          color="lightsecondary"
          v-tooltip="'제목색상'"
        ></v-btn>
      </v-col>
    </v-row>

    <v-sheet
      :class="colorPick"
      v-if="colorPickerShow"
    >
      <ColorPicker
        v-model="colorValue"
        input-id="cp-hex"
        inline
        format="hex"
      />
    </v-sheet>

    <v-responsive width="100%"></v-responsive>

    <v-row>
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 내용 </v-sheet>
      </v-col>

      <v-col cols="6">
        <v-sheet class="pa-2">
          <div>
            <div class="main-container">
              <div
                class="editor-container editor-container_classic-editor"
                ref="editorContainerElement"
              >
                <div class="editor-container__editor">
                  <div ref="editorElement">
                    <ckeditor
                      v-if="isLayoutReady"
                      v-model="editorContent"
                      :editor="editor"
                      :config="config"
                      @ready="onEditorReady"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </v-sheet>
      </v-col>
    </v-row>

    <v-row v-if="attachedfiles.length > 0 && isEdit">
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 첨부파일 </v-sheet>
      </v-col>

      <v-col
        cols="9"
        class="ml-2"
      >
        <ul
          v-for="fileitems in attachedfiles"
          :key="fileitems.fileid"
        >
          <li>
            {{ fileitems.filename }}&nbsp;({{ formatBytes(fileitems.filebytes) }})
            <v-icon
              icon="mdi-close"
              color="error"
              @click="delFileItem(fileitems.fileid)"
            />
          </li>
        </ul>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 파일첨부 </v-sheet>
      </v-col>
      <v-col cols="9">
        <FileUpload
          @select="onAdvancedUpload"
          name="attachedFile"
          :custom-upload="true"
          :multiple="true"
          :maxFileSize="1000000"
          chooseLabel="선택"
          style="border: none; background-color: #ba3d9b; font-size: 16px"
          :show-upload-button="false"
          :cancel-button-props="cancelBtnStyle"
          cancelLabel="취소"
          :pt="passThrough"
          invalid-file-size-message="파일 사이즈 한도를 초과했습니다."
        >
          <template #empty>
            <span class="flex items-center justify-center flex-col">
              <span class="mt-6 mb-0">등록할 파일을 드래그 하세요</span>
            </span>
          </template>
        </FileUpload>
      </v-col>
    </v-row>

    <v-row v-if="likeAuth">
      <v-col cols="1">
        <v-sheet class="pa-2 ma-1"> 댓글(좋아요) </v-sheet>
      </v-col>
      <v-col>
        <v-checkbox
          density="compact"
          v-model="likeCheck"
          label="사용"
          color="secondary"
          @click="isLike"
        ></v-checkbox>
      </v-col>
    </v-row>

    <v-col
      cols="auto"
      class="text-right"
    >
      <v-btn
        v-if="isWrite"
        size="default"
        color="secondary"
        class="mr-2"
        @click="writeBoard"
        variant="flat"
        >등록</v-btn
      >
      <v-btn
        v-else-if="isEdit"
        size="default"
        color="secondary"
        class="mr-2"
        @click="updateBoard"
        variant="flat"
        >수정</v-btn
      >

      <v-btn
        v-else-if="isReply"
        size="default"
        color="secondary"
        class="mr-2"
        @click="replyBoard"
        variant="flat"
        >답글 등록</v-btn
      >

      <v-btn
        size="default"
        color=""
        class="mr-2"
        @click="cancelBtn"
        variant="flat"
        >취소</v-btn
      >
    </v-col>

    <v-row v-if="isReply">
      <v-col>
        <v-sheet class="pa-2 ma-2 replycls"> 아래는 {{ boardWriterName }}({{ boardWriter }}) 님이 {{ convertDateFormat(boardRegDate) }}에 작성한 원본 게시물입니다. </v-sheet>
      </v-col>
    </v-row>

    <v-row v-if="isReply">
      <v-col>
        <div
          class="ck-content ma-5"
          v-html="boardContent"
        ></div>
      </v-col>
    </v-row>
  </UiEmptyTitleCard>
</template>

<style scoped>
.custom-text-field {
  block-size: 15px;
}

.gallery {
  padding: 0;
  margin: 0;
  list-style: none outside none;
}

.gallery li {
  position: relative;
  float: inline-start;
  margin-block: 1px;
  margin-inline: 2px;
}

.milkt_icon {
  max-inline-size: 100px;
}

.replycls {
  border-block-end: 1px solid #ccc;
  border-block-start: 1px solid #ccc;
  margin-block: 8px;
  margin-inline: auto;
}

.removeList {
  position: relative;
  block-size: 20px;
  float: inline-end;
  text-decoration: none;
}

.colorPicker {
  position: absolute;
  float: inline-end;
  inset-block-start: 4%;
  inset-inline-end: 30%;
}

.colorPicker2 {
  position: absolute;
  float: inline-end;
  inset-block-start: 10%;
  inset-inline-end: 30%;
}

ul {
  list-style-type: none;
  padding-inline-start: 5px;
}

.align-left {
  display: flex;
  justify-content: flex-start;
}
</style>
