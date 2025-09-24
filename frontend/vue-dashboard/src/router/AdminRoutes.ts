const AdminRoutes = {
  path: '/admin',
  component: () => import('@/views/pages/admin/Admin.vue'),
  meta: {
    requiresAuth: false,
  },
  redirect: '/admin/auth',
  children: [
    {
      name: 'Auth',
      path: '/admin/auth/:cls',
      component: () => import('@/views/pages/admin/Auth.vue'),
    },
    {
      name: 'Users',
      path: '/admin/users/:cls',
      component: () => import('@/views/pages/admin/Users.vue'),
    },
  ],
}

export default AdminRoutes
