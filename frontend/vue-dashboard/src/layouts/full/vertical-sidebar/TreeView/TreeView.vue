<script setup lang="ts">
import { router } from '@/router'
import type { Menu } from '@/types/commonTypes/menu'
import type { TreeItem } from '@/types/commonTypes/tree'
import { buildTree } from '@/utils/helpers/menu-parser'
import Tree, { type TreeExpandedKeys, type TreeSelectionKeys } from 'primevue/tree'
import type { TreeNode } from 'primevue/treenode'
import { computed, ref, onMounted } from 'vue'
import { getBoardNewCount } from '@/apis/board'
import type { Board } from '@/types/boardTypes/board'

const props = defineProps({ item: Object, type: String, target: Object })
const selectedTreeValue = ref<TreeSelectionKeys>({})
const curSelectedNodeKey = ref<number>(-1)
const expandedKeys = ref<TreeExpandedKeys>({})
const treeValue = ref<TreeItem[]>([])
const loading = ref<boolean>(true)
const newCount = ref<Board[]>([])

//새 글 체크
const getCount = async () => {
  try {
    const data: any = await getBoardNewCount()

    if (data.value?.length > 0) {
      newCount.value = [...data.value] as Board[]
    }
  } catch (e) {
    console.error(e)
  }
}

/**
 * 메뉴아이템 가공 및 computed
 */
const parseMenuItem = computed(() => {
  loading.value = true
  if (props.type === 'menuList') {
    const buf = props.item as Menu[]
    if (treeValue.value?.length === 0) {
      if (buf.length > 0) {
        const cRoot = buf.filter((m: Menu) => m.cls === 'G') // 공통
        const eRoot = buf.filter((m: Menu) => m.cls === 'E') // 초등
        const kRoot = buf.filter((m: Menu) => m.cls === 'K') // 유아
        const mRoot = buf.filter((m: Menu) => m.cls === 'M') // 중고등
        treeValue.value.push(
          ...(buildTree(cRoot, null) as TreeItem[]),
          ...(buildTree(kRoot, null) as TreeItem[]),
          ...(buildTree(eRoot, null) as TreeItem[]),
          ...(buildTree(mRoot, null) as TreeItem[]),
        )
      }
    }
    if (props.target?.menuId) {
      selectedTreeValue.value = {}
      selectedTreeValue.value[props.target?.menuId] = true
      recursiveReverseSelect(buf, props.target as Menu)
      if (curSelectedNodeKey.value === -1) {
        recursiveReverseExpand(buf, props.target as Menu)
      }
    }
    loading.value = false
    return treeValue.value
  }
  return []
})

/**
 * 노드 펼치기
 * @param node TreeNode
 */
const expandNode = (node: TreeNode) => {
  if (node.children && node.children.length && node.key) {
    expandedKeys.value[node.key] = true
    for (const child of node.children) {
      expandNode(child)
    }
  }
}
/**
 * 노드 전체 접기
 */
const expandAll = () => {
  const nodes: any = treeValue.value
  for (const node of nodes) {
    expandNode(node)
  }
}

/**
 * 노드 접기
 * @param node TreeNode
 * @param currentExpandedSiteKey 노드 확장 키
 */
function collapseNode(node: TreeNode, currentExpandedSiteKey: TreeSelectionKeys) {
  if (node.children && node.children.length && node.key) {
    currentExpandedSiteKey[node.key] = false

    for (const child of node.children) {
      collapseNode(child, currentExpandedSiteKey)
    }
  }
}
/**
 * 노드 전체 접기
 */
const collapseAll = () => {
  const nodes: any = treeValue.value
  const currentExpandedSiteKey = expandedKeys.value
  for (const node of nodes) {
    collapseNode(node, currentExpandedSiteKey)
  }
  expandedKeys.value = currentExpandedSiteKey
}

/**
 * 노드 확장 재귀
 * @param node TreeNode
 * @param isExpand 확장여부
 */
const expandRecursive = (node: TreeNode, isExpand: boolean) => {
  const expKeys = expandedKeys.value as any
  expKeys[node.key] = true

  if (node.children) {
    node?.children.forEach(childNode => {
      expandRecursive(childNode, isExpand)
    })
  }
}

/**
 * 현재메뉴를 기준으로 선택된 전체 폴더 및 선택여부를 지정
 * @param data 메뉴 데이터
 * @param curItem 현재 메뉴
 */
const recursiveReverseExpand = (data: Menu[], curItem: Menu): void => {
  if (curItem?.upperMenuId) {
    const item = data?.find(m => m.menuId.toString() == curItem?.upperMenuId)
    if (item) {
      expandedKeys.value[item.menuId] = true
      selectedTreeValue.value[item.menuId] = true
      recursiveReverseExpand(data, item)
    }
  }
}
/**
 * 현재메뉴를 기준으로 최상위 선택 여부를 지정
 * @param data 메뉴데이터
 * @param curItem 현재 메뉴
 */
const recursiveReverseSelect = (data: Menu[], curItem: Menu): void => {
  if (curItem?.upperMenuId) {
    const item = data?.find(m => m.menuId.toString() == curItem?.upperMenuId)
    if (item) {
      selectedTreeValue.value[item.menuId] = true
      recursiveReverseSelect(data, item)
    }
  }
}

/**
 * Tree 선택 이벤트
 * @param node 선택된 TreeItem
 */
const onNodeSelect = (node: TreeItem): void => {
  curSelectedNodeKey.value = node.key
  if (node.isExpand) {
    const expKeys = expandedKeys.value as any
    if (typeof expKeys[node.key] === 'undefined' || !expKeys[node.key]) {
      expKeys[node.key] = true
    } else {
      expKeys[node.key] = false
    }
  } else {
    selectedTreeValue.value[node.key] = true
    router.push({ path: `/board/${node.key}` })
  }
}

/**
 * Tree 확장 이벤트
 * @param node 선택된 TreeItem
 */
const onNodeExpand = (node: TreeItem): void => {
  curSelectedNodeKey.value = node.key
  expandedKeys.value[node.key] = true
}

/**
 * Tree 축소 이벤트
 * @param node 선택된 TreeItem
 */
const onNodeCollapse = (node: TreeItem): void => {
  curSelectedNodeKey.value = node.key
  expandedKeys.value[node.key] = false
}

onMounted(() => {
  getCount()
})
</script>

<template>
  <v-divider class="my-4 mt-n1" />
  <div class="mt-n5">
    <v-row class="flex flex-wrap gap-2 text-body-2">
      <v-col
        cols="8"
        md="1"
      >
        <v-btn
          v-tooltip="'즐겨찾기'"
          variant="text"
          icon="mdi mdi-heart"
          label="favorite"
          size="small"
          color="primary"
        />
      </v-col>
      <v-col
        cols="8"
        md="1"
      >
        <v-btn
          v-tooltip="'전체 메뉴 펼치기'"
          variant="text"
          icon="mdi mdi-folder-open"
          label="Expand All"
          @click="expandAll"
          size="small"
          color="primary"
        />
      </v-col>
      <v-col
        cols="8"
        md="1"
      >
        <v-btn
          v-tooltip="'전체 메뉴 접기'"
          variant="text"
          icon="mdi mdi-folder"
          label="Collapse All"
          @click="collapseAll"
          size="small"
          color="primary"
        />
      </v-col>
      <!--       
      <v-col
        cols="8"
        md="8"
      >
        <v-text-field
          label="메뉴검색"
          variant="outlined"
          hide-details
          single-line
          density="compact"
        ></v-text-field>
      </v-col> -->
    </v-row>

    <Tree
      :value="parseMenuItem"
      :filter="true"
      filterMode="lenient"
      filterPlaceholder=" 메뉴명 검색"
      v-model:selectionKeys="selectedTreeValue"
      v-model:expandedKeys="expandedKeys"
      class="w-full md:w-[30rem] ml-n8 mt-n7 text-body-2"
      selectionMode="single"
      @node-select="onNodeSelect"
      @node-unselect="onNodeSelect"
      @node-expand="onNodeExpand"
      @node-collapse="onNodeCollapse"
      :loading="loading"
      :pt="{
        nodeContent: { style: { '--p-tree-node-selected-background': '#f5f2fa', '--p-tree-node-selected-color': '#8137a6', '--p-tree-node-icon-selected-color': '#8137a6' } },
      }"
    >
      <template #url="{ node }">
        <RouterLink
          :to="`/board/${node.key}`"
          class="text-darkText text-decoration-none"
          ><a v-html="node.label"></a
        ></RouterLink>
        <v-icon
          v-if="newCount.find(item => item.menuId === node.key)"
          icon="mdi-new-box"
          color="error"
        ></v-icon>
      </template>
    </Tree>
  </div>
</template>
