import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { router } from './router'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import PerfectScrollbar from 'vue3-perfect-scrollbar'
import VueTablerIcons from 'vue-tabler-icons'
import '@/scss/style.scss'

//primevue
import PrimeVue from 'primevue/config'
import Aura from '@primevue/themes/aura'
import 'primeicons/primeicons.css'

// print
import print from 'vue3-print-nb'

//ckeditor
import { CkeditorPlugin } from '@ckeditor/ckeditor5-vue'
import './assets/ckStyle.css'
import 'ckeditor5/ckeditor5.css'

const app = createApp(App)
app.use(router)
app.use(PerfectScrollbar)
app.use(createPinia())
app.use(VueTablerIcons)
app.use(print)
app.use(PrimeVue, {
  theme: {
    preset: Aura,
  },
  locale: {
    firstDayOfWeek: 0,
    dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    today: '오늘',
    clear: '초기화',
    dateFormat: 'yy-mm-dd',
    weekHeader: '주',
  },
  pt: {
    badge: { root: { style: { display: 'none' } } },
    fileThumbnail: { style: { display: 'none' } },
    progressbar: { root: { style: { display: 'none' } } },
    pcfilterinput: { root: { style: { padding: '4px', margin: '3px', font: '14px bold' } } },
  },
})
app.use(CkeditorPlugin)
app.use(vuetify).mount('#app')
