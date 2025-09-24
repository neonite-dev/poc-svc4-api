<script setup lang="ts">
import { router } from '@/router'
</script>

<template>
  <v-row
    no-gutters
    class="h-screen"
  >
    <v-col class="d-flex align-center justify-center">
      <div class="text-center">
        <div class="CardMediaWrapper">
          <img
            src="@/assets/images/maintenance/img-error-bg.svg"
            alt="grid"
            class="w-100"
          />
          <img
            src="@/assets/images/maintenance/img-error-blue.svg"
            alt="grid"
            class="CardMediaParts"
          />
          <img
            src="@/assets/images/maintenance/img-error-text.svg"
            alt="build"
            class="CardMediaBuild"
          />
          <img
            src="@/assets/images/maintenance/img-error-purple.svg"
            alt="build"
            class="CardMediaBuild"
          />
        </div>
        <h1 class="text-h1">페이지 권한이 없습니다.</h1>
        <p>
          <small>해당 게시판 권한이 없습니다 <br />관리자에게 문의하세요</small>
        </p>
        <v-btn
          variant="flat"
          color="primary"
          class="mt-4"
          to="/"
          prepend-icon="mdi-home"
        >
          Home</v-btn
        >
      </div>
    </v-col>
  </v-row>
</template>
<style lang="scss">
.CardMediaWrapper {
  position: relative;
  margin-block: 0;
  margin-inline: auto;
  max-inline-size: 720px;
}

.CardMediaBuild {
  position: absolute;
  animation: 5s bounce ease-in-out infinite;
  inline-size: 100%;
  inset-block-start: 0;
  inset-inline-start: 0;
}

.CardMediaParts {
  position: absolute;
  animation: 10s blink ease-in-out infinite;
  inline-size: 100%;
  inset-block-start: 0;
  inset-inline-start: 0;
}
</style>
