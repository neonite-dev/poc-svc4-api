import type { Menu } from '@/types/commonTypes/menu'
import type { TreeItem } from '@/types/commonTypes/tree'
import { CircleIcon, FolderIcon } from 'vue-tabler-icons'

/**
 * Tree Menu Item Setting
 * @param data
 * @param parentId
 * @returns
 */
export function buildTree(data: Menu[], parentId: null | number): TreeItem[] {
  const tree = [] as TreeItem[]
  data?.forEach((item: Menu): void => {
    if (item?.upperMenuId == parentId?.toString()) {
      const children = buildTree(data, item.menuId)
      const isExpend = data.findIndex(m => m.upperMenuId == item.menuId.toString()) > -1
      const treeItem: TreeItem = {
        key: item.menuId,
        label: item.menuName,
        data: item.cls,
        icon: iconReplace(item, isExpend), //isExpend ? ([100000, 100001, 100002, 100003].includes(item.menuId) ? 'pi pi-fw pi-home' : 'pi pi-fw pi-folder') : 'pi pi-fw pi-file',
        isExpand: isExpend,
        type: isExpend ? 'folder' : 'url',
        sort: item.sort,
        children: [],
      }
      if (children.length) {
        treeItem.children = children
      }
      tree.push(treeItem)
    }
  })
  const sortTree = sortRecursive('children')(sort('sort'))
  const treeSort = sortTree(tree)
  return treeSort
}

function iconReplace(item: Menu, isExpand: boolean) {
  let pi = 'pi pi-fw pi-file'
  if (isExpand && item) {
    pi = 'pi pi-fw pi-folder'
    if ([100000, 100001, 100002, 100003].includes(item.menuId)) {
      return 'pi pi-fw pi-home'
    }
    if (['관리'].includes(item.menuName.slice(-2))) {
      return 'pi pi-fw pi-cog'
    }
    if (['자료'].includes(item.menuName.slice(-2)) || ['자료실'].includes(item.menuName.slice(-3))) {
      return 'pi pi-fw pi-save'
    }
    if (['팀', '실'].includes(item.menuName.slice(-1)) || ['사업부'].includes(item.menuName.slice(-3))) {
      return 'pi pi-fw pi-sitemap'
    }
    if (['요청'].includes(item.menuName.slice(-2))) {
      return 'pi pi-fw pi-send'
    }
    if (['상담', '녹취'].includes(item.menuName.slice(-2)) || ['상담'].includes(item.menuName.substring(0, 2)) || ['초등상담'].includes(item.menuName.substring(0, 4))) {
      return 'pi pi-fw pi-whatsapp'
    }
    if (['쪽지', '톡쌤'].includes(item.menuName.slice(-2))) {
      return 'pi pi-fw pi-comments'
    }
    if (['장애', '오류'].includes(item.menuName.slice(-2)) || ['장애'].includes(item.menuName.substring(0, 2))) {
      return 'pi pi-fw pi-exclamation-triangle'
    }
    if (['공통'].includes(item.menuName.substring(0, 2))) {
      return 'pi pi-fw pi-globe'
    }
  }
  return pi
}

export interface menu {
  key: number
  header?: string
  title?: string
  icon?: object
  to?: string
  divider?: boolean
  chip?: string
  chipColor?: string
  chipVariant?: string
  chipIcon?: string
  children?: menu[]
  disabled?: boolean
  type?: string
  subCaption?: string
  data: string
}

export function parseMenuTree(data: Menu[], parentId?: null | number) {
  const tree = [] as menu[]
  data?.forEach((item: Menu): void => {
    if (item?.upperMenuId == parentId?.toString()) {
      const children = parseMenuTree(data, item.menuId)
      const treeItem: menu = {
        key: item.menuId,
        title: item.menuName,
        data: item.cls,
        to: `/board/${item.menuId}`,
        icon: data.findIndex(m => m.upperMenuId == item.menuId.toString()) > -1 ? FolderIcon : CircleIcon,
      }
      if (children.length) {
        treeItem.children = children as any
      }
      tree.push(treeItem)
    }
  })
  const sortTree = sortRecursive('children')(sort('sort'))
  return sortTree(tree)
}

export function addRootMenu(nodes: Menu[], rootKey: number, rootText: string): Menu[] {
  // root 추가
  if (nodes?.length > 0) {
    nodes
      .filter(m => m.upperMenuId == null)
      .forEach((v: Menu) => {
        v.upperMenuId = rootKey.toString()
      })
    nodes.push({
      menuId: rootKey,
      menuName: rootText,
      upperMenuId: undefined,
      use_Yn: '',
      type: '',
      sort: 0,
      cls: nodes[0].cls,
    })
  }
  return nodes
}

export const sort =
  (field: any, dir = 'asc') =>
  (xs: any) =>
    [...xs].sort(({ [field]: a }, { [field]: b }) => (dir == 'asc' ? 1 : -1) * (a < b ? -1 : a > b ? 1 : 0))

export const sortRecursive = (childField: any) => (sort: any) => (xs: any) =>
  sort([...xs]).map(({ [childField]: cf, ...rest }) => ({
    ...rest,
    [childField]: sortRecursive(childField)(sort)(cf),
  }))
