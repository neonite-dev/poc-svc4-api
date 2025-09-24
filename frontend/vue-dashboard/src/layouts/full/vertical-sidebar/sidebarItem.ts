import { CircleIcon, KeyIcon, DashboardIcon } from 'vue-tabler-icons'

export interface menu {
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
}
const rootHost = `${import.meta.env.VITE_ROOT_HOST}`

const sidebarItem: menu[] = [
  { header: 'Dashboard' },
  {
    title: '[thymeleaf] Dashboard',
    icon: DashboardIcon,
    to: `${rootHost}`,
    type: 'href',
  },
  {
    title: '[Vue] Dashboard',
    icon: DashboardIcon,
    to: `${rootHost}/vueapp2`,
    type: 'href',
  },
  {
    title: '[Vue] Vue Cli',
    icon: DashboardIcon,
    to: `${rootHost}/vueapp`,
    type: 'href',
  },
  { divider: true },
  { header: 'Pages' },
  {
    title: 'Authentication',
    icon: KeyIcon,
    to: '/auth',
    children: [
      {
        title: 'Login',
        icon: CircleIcon,
        to: '/auth/login',
      },
      {
        title: 'Register',
        icon: CircleIcon,
        to: '/auth/register',
      },
    ],
  },
]

export default sidebarItem
