<script setup lang="ts">
const props = defineProps({
  title: String,
})
</script>

<template>
  <v-card variant="flat">
    <v-card-text>
      <slot />
    </v-card-text>
  </v-card>
</template>
