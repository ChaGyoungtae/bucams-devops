
import Login from '@/views/auth/Login.vue';
import AdminPage from '@/views/member/AdminPage.vue';
import MyInfoPage from '@/views/member/MyInfoPage.vue';
import { onMounted } from 'vue';
import { createRouter, createWebHistory } from 'vue-router'


// import AuthLayout from '@/layout/AuthLayout.vue'
// import BaseLayout from '@/layout/BaseLayout.vue'
// import Login from '@/views/auth/Login.vue'
// import NotFound from '@/views/common/NotFound.vue'
// import AddDepartment from '@/views/department/AddDepartment.vue'
// import DepartmentDetail from '@/views/department/DepartmentDetail.vue'
// import Departments from '@/views/department/Departments.vue'
// import Home from '@/views/Home.vue'

// 지연 로딩(Lazy Loading) 적용
// 지연 로딩은 컴포넌트가 이용되는 시점에 컴포넌트 및 관련된 모듈을 웹 서버로부터 로딩하는 방법이다.

const BaseLayout = () => import('@/views/BaseLayout.vue');

const Departments = () => import('@/views/department/ShowDepartment.vue');

const LectureList = () => import('@/views/Lecture/LectureList.vue');
const LectureCreate = () => import('@/views/Lecture/LectureCreate.vue');
const LectureItem = () => import('@/views/Lecture/LectureItem.vue');

const Registrations = () => import('@/views/Registration/Registration.vue');
const MyRegistration = () => import('@/views/Registration/MyRegistration.vue');

const DurationOfRegistration = () => import('@/views/DurationOfRegistration/DurationOfRegistration.vue')

const router = createRouter({
  // 라우터가 사용할 라우팅 모드 지정 (HTML 5 모드)
  history: createWebHistory(import.meta.env.BASE_URL),
  // 요청 경로에 따라 렌더링 할 컴포넌트 배열로 지정
  // 명명된 라우트
  // routes: [
  //   { path: '/', name: 'home', component: Home },
  //   { path: '/departments', name: 'departments', component: Departments },
  //   // 동적 라우트는 일정한 패턴의 URI 경로를 하나의 라우트에 연결하는 방법이다.
  //   { path: '/departments/:no', name: 'departments/no', component: DepartmentDetail },
  //   { path: '/departments/add', name: 'departments/add', component: AddDepartment },
  //   // 404 라우트
  //   { path: '/:paths(.*)*', name: 'notfound', component: NotFound}
  // ],

  // 중첩된 라우트
  // RouterView에 의해서 렌더링된 컴포넌트가 다시 RouterView를 이용해 자식 라우트에 매칭된 컴포넌트를 렌더링한다. 
  routes: [
    { path: '/login', name: 'login', component: Login },
    {
      path: '/',
      name: 'base',
      component: BaseLayout,
      children: [
        
        { 
          path: 'departments', 
          name: 'departments', 
          component: Departments 
        },
        {
          path: 'lectures/create',
          name: 'lecture-create',
          component: LectureCreate
        },
        {
          path: 'lectures/manage',
          name: 'lecture-manage',
          component: LectureItem
        },
        {
          path: 'lectures/list',
          name: 'lecture-list',
          component: LectureList
        },
        {
          path: 'members/my-info',
          name: 'myInfo',
          component: MyInfoPage
        },
        {
          path: 'members/admin',
          name: 'adminPage',
          component: AdminPage
        },
        {
          path: 'DurationOfRegistration',
          name: 'DurationOfRegistration',
          component: DurationOfRegistration
        },
        { path: 'registration',
          name: 'registration',
          component: Registrations
        },
        { path: 'registration/:memberId',
          name: 'MyRegistration',
          component: MyRegistration
        },

      ]
    }]
  })

export default router
