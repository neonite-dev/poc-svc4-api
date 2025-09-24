<script setup lang="ts">
const logoPath = '/images/logo.png'
</script>

<template>
  <div class="header">
    <nav class="navbar navbar-expand-md navbar-light bg-light mb-3 p-1">
      <a href="/"><img :src="logoPath" alt="logo"></a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
          <li class="nav-item"><a class="nav-link" href="/vueapp">VueApp</a></li>
          <li class="nav-item"><a class="nav-link" href="/crm/test">[thymeleaf] test</a></li>
          <li class="nav-item"><a class="nav-link" href="/crm/codes">[thymeleaf] mcrm</a></li>
          <li class="nav-item"><a class="nav-link" href="/eduapp/test">[thymeleaf] crm-test</a></li>
          <li class="nav-item"><a class="nav-link" href="/eduapp/codes">[thymeleaf] crm</a></li>
          <li class="nav-item"><a class="nav-link" href="https://extra.milkt.co.kr/">익스트라넷</a></li>
        </ul>
      </div>
      <ul class="navbar-nav">
        <li class="nav-item"><a class="nav-link" href="/">Logout</a></li>
      </ul>	
    </nav>
  </div>
</template>

<style scoped>
.header {
  width: 1280px;
  position: fixed;
  margin: -150px 0px 0px -150px;
}
</style>
