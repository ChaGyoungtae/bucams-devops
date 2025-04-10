

import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './router'
import { createPinia } from 'pinia'

// 💡 임시 memberId 설정 (로그인 없을 때)
// if (!localStorage.getItem('memberId')) {
//     localStorage.setItem('memberId', '1'); // 교수 ID로 가정
//     localStorage.setItem('role', 'PROFESSOR'); // 필요 시 role도
// }

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.mount('#app');

