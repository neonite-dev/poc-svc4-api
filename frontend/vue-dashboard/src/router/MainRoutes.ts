const MainRoutes = {
  path: '/main',
  meta: {
    requiresAuth: false,
  },
  redirect: '/main/dashboard/default',
  component: () => import('@/layouts/full/FullLayout.vue'),
  children: [
    {
      name: 'LandingPage',
      path: '/',
      component: () => import('@/views/pages/board/Main.vue'),
    },
    {
      name: 'Default',
      path: '/dashboard/default',
      component: () => import('@/views/pages/board/Main.vue'),
    },
    {
      name: 'Starter',
      path: '/starter',
      component: () => import('@/views/StarterPage.vue'),
    },
    {
      name: 'Tabler Icons',
      path: '/icons/tabler',
      component: () => import('@/views/utilities/icons/TablerIcons.vue'),
    },
    {
      name: 'Material Icons',
      path: '/icons/material',
      component: () => import('@/views/utilities/icons/MaterialIcons.vue'),
    },
    {
      name: 'Typography',
      path: '/utils/typography',
      component: () => import('@/views/utilities/typography/TypographyPage.vue'),
    },
    {
      name: 'Shadows',
      path: '/utils/shadows',
      component: () => import('@/views/utilities/shadows/ShadowPage.vue'),
    },
    {
      name: 'Colors',
      path: '/utils/colors',
      component: () => import('@/views/utilities/colors/ColorPage.vue'),
    },
    {
      name: 'Board',
      path: '/board',
      component: () => import('@/views/pages/board/Board.vue'),
    },
    {
      name: 'BoardList',
      path: '/board/:menuId',
      component: () => import('@/views/pages/board/Board.vue'),
    },
    {
      name: 'BoardView',
      path: '/boardview/:boardId',
      component: () => import('@/views/pages/board/BoardView.vue'),
    },
    {
      name: 'BoardInsert',
      path: '/insert',
      component: () => import('@/views/pages/board/BoardInsert.vue'),
    },
    {
      name: 'BoardReply',
      path: '/reply/:boardId',
      component: () => import('@/views/pages/board/BoardInsert.vue'),
    },
    {
      name: 'BoardUpdate',
      path: '/update/:boardId',
      component: () => import('@/views/pages/board/BoardInsert.vue'),
    },
  ],
}

export default MainRoutes
