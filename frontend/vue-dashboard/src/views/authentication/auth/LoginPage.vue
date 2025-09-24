<script setup lang="ts">
import Logo from '@/layouts/full/logo/LogoDark.vue'
import AuthLogin from '../authForms/AuthLogin.vue'
</script>

<template>
  <v-row
    class="h-screen"
    no-gutters
  >
    <v-col
      cols="12"
      class="d-flex align-center bg-lightsecondary"
    >
      <v-container>
        <div class="pa-7">
          <v-row justify="center">
            <v-col cols="12">
              <v-card
                elevation="0"
                class="loginBox"
              >
                <v-card
                  variant="flat"
                  class="img"
                >
                  <v-card-text class="pa-0">
                    <v-row class="pa-0">
                      <v-col
                        cols="12"
                        class="pt-5 overflow-hidden"
                      >
                        <div class="empty"></div>
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col
                        cols="12"
                        class="text-center p-0"
                      >
                        <h2 class="text-secondary text-h2">ExtraNet</h2>
                      </v-col>
                    </v-row>

                    <v-row class="pl-10 pr-10 pb-10">
                      <v-col> <AuthLogin /> </v-col>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-card>
            </v-col>
          </v-row>
        </div>
      </v-container>
    </v-col>
  </v-row>
</template>
<style lang="scss">
.loginBox {
  margin-block: 0;
  margin-inline: auto;
  max-inline-size: 475px;
}

.img {
  background-image: url('/images/intro_mIlkt.jpg');
  background-position: top;
  background-repeat: no-repeat;
  background-size: contain;
}

.empty {
  block-size: 360px;
}
</style>
