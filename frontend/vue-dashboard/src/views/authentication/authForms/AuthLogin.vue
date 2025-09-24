<script setup lang="ts">
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { Form } from 'vee-validate'

const checkbox = ref(false)
const valid = ref(false)
const show1 = ref(false)

const password = ref('')
const userId = ref('')
const passwordRules = ref([(v: string) => !!v || '비밀번호를 입력해주세요', (v: string) => (v && v.length <= 20) || '비밀번호는 최대 20자까지 입력할수 있습니다.'])
//const emailRules = ref([(v: string) => !!v || 'E-mail is required', (v: string) => /.+@.+\..+/.test(v) || 'E-mail must be valid'])

async function validate(values: any, { setErrors }: any) {
  const authStore = useAuthStore()
  const isDuplicateCheck = await authStore.userDupleCheck(userId.value)
  if (isDuplicateCheck) {
    if (!confirm('다른 PC / 브라우저에 로그인 중입니다.\r\n계속하시겠습니까?\r\n계속 진행할 경우 다른 접속 로그인은 종료됩니다.')) {
      return false
    }
  }
  return authStore.login(userId.value, password.value).catch(error => setErrors({ apiError: error }))
}
</script>

<template>
  <v-row>
    <v-col class="d-flex align-center">
      <v-divider />
    </v-col>
  </v-row>
  <Form
    v-slot="{ errors, isSubmitting }"
    class="mt-7 loginForm"
    @submit="validate"
  >
    <v-text-field
      v-model="userId"
      label="아이디"
      class="mt-4 mb-4"
      required
      density="compact"
      hide-details="auto"
      variant="outlined"
      color="primary"
    ></v-text-field>
    <v-text-field
      v-model="password"
      :rules="passwordRules"
      label="비밀번호"
      required
      density="compact"
      variant="outlined"
      color="primary"
      hide-details="auto"
      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="show1 ? 'text' : 'password'"
      class="pwdInput"
      @click:append="show1 = !show1"
    ></v-text-field>

    <div class="d-sm-flex align-center mt-2 mb-7 mb-sm-0">
      <v-checkbox
        v-model="checkbox"
        :rules="[(v: any) => !!v || 'You must agree to continue!']"
        label="아이디 저장"
        required
        color="primary"
        class="ms-n2"
        hide-details
      ></v-checkbox>
      <div class="ml-auto">
        <a
          href="javascript:void(0)"
          class="text-secondary text-decoration-none"
          >비밀번호 찾기</a
        >
      </div>
    </div>
    <v-btn
      color="secondary"
      :loading="isSubmitting"
      block
      class="mt-2"
      variant="flat"
      size="large"
      :disabled="valid"
      type="submit"
    >
      로그인</v-btn
    >
    <div
      v-if="errors.apiError"
      class="mt-2"
    >
      <v-alert color="error">{{ errors.apiError }}</v-alert>
    </div>
  </Form>
  <div class="mt-5 text-center">
    <v-divider />
    <v-btn
      variant="plain"
      to="/auth/register"
      class="mt-2 text-capitalize mr-n2"
      >※ 이 사이트는 크롬 또는 마이크로 엣지에 최적화 되어 있습니다.</v-btn
    >
    <v-row>
      <v-col>
        <v-chip
          text="크롬 다운로드"
          href="https://www.google.co.kr/chrome/?brand=CHBD&amp;gclid=CjwKCAiAz4b_BRBbEiwA5XlVViNptWRN4TAOmPi7ZNNlY1RRIOkiG28u7XTTj6SW7deGaAgpn-IUJRoCrdoQAvD_BwE&amp;gclsrc=aw.ds"
          class="mt-2 mr-n2"
        >
        </v-chip>
      </v-col>

      <v-col>
        <v-chip
          text="엣지 다운로드"
          href="https://www.microsoft.com/ko-kr/edge"
          class="mt-2 mr-n2"
        >
        </v-chip>
      </v-col>
    </v-row>
  </div>
</template>
<style lang="scss">
.outlinedInput .v-field {
  border: 1px solid rgba(0, 0, 0, 8%);
  box-shadow: none;
}

.orbtn {
  border-color: rgba(0, 0, 0, 8%);
  margin-block: 20px;
  margin-inline: 15px;
  padding-block: 2px;
  padding-inline: 40px;
}

.pwdInput {
  position: relative;

  .v-input__append {
    position: absolute;
    inset-block-start: 50%;
    inset-inline-end: 10px;
    transform: translateY(-50%);
  }
}

.loginForm {
  .v-text-field .v-field--active input {
    font-weight: 500;
  }
}
</style>
