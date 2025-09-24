<template>
  <div class="logo d-flex ga-2 justify-center">
    <RouterLink to="/">
      <img
        src="/images/h1_logo.png"
        style="inline-size: 140px"
      />
    </RouterLink>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
const primary = ref('rgb(var(--v-theme-primary))')
const secondary = ref('rgb(var(--v-theme-secondary))')
</script>
