<script setup lang="ts">
import { RouterView } from 'vue-router'
import AdminHeader from '@/views/pages/admin/AdminHeader.vue'
</script>

<template>
  <v-locale-provider>
    <v-app theme="PurpleTheme">
      <v-main>
        <v-container fluid>
          <AdminHeader />
        </v-container>

        <v-container
          fluid
          class="page-wrapper"
        >
          <div>
            <RouterView />
          </div>
        </v-container>
      </v-main>
    </v-app>
  </v-locale-provider>
</template>
