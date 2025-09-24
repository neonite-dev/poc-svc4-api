<script setup lang="ts">
import { shallowRef } from 'vue'
const rootHost = `${import.meta.env.VITE_ROOT_HOST}`
const footerLink = shallowRef([
  {
    title: 'Home',
    to: `/`,
  },
  {
    title: 'Swagger',
    to: `${rootHost}/swagger-ui/index.html?urls.primaryName=extranet#/`,
  },
])
</script>
<template>
  <v-footer class="px-0 footer mt-2">
    <v-row
      justify="center"
      align="center"
      no-gutters
    >
      <v-col
        cols="12"
        sm="6"
      >
        <p class="text-body-1 mb-0 text-sm-left text-center">
          MilkT ♥ ExtraNet crafted by
          <a
            href="/"
            class="text-darkText text-decoration-none"
            target="_blank"
            >서비스개발4팀</a
          >
        </p>
      </v-col>
      <v-col
        class="text-sm-right text-center"
        cols="12"
        sm="6"
      >
        <a
          v-for="(item, i) in footerLink"
          :key="i"
          class="mx-2 text-body-1 text-darkText text-decoration-none"
          target="_blank"
          :href="item.to"
        >
          {{ item.title }}
        </a>
      </v-col>
    </v-row>
  </v-footer>
</template>

<style lang="scss">
.v-footer {
  position: unset;
}

footer {
  a {
    &:hover {
      color: rgb(var(--v-theme-primary)) !important;
    }
  }
}
</style>
