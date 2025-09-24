export interface TreeItem {
  key: number
  label: string
  data: string
  icon: string
  isExpand: boolean
  type: string
  sort: number
  children: TreeItem[]
}
