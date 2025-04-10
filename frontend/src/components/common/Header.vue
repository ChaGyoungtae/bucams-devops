<template>
  <header class="py-4 px-3 px-lg-5 text-bg-dark">
    <div class="container-fluid">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
          <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
            <use xlink:href="#bootstrap"></use>
          </svg>
        </a>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle px-2 text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              내 정보
            </a>
            <ul class="dropdown-menu">
              <li>
                <RouterLink :to="{ name: 'myInfo' }" class="dropdown-item">내 정보 보기</RouterLink>
              </li>
              <li v-if="authStore.memberInfo.role === 'ADMIN'">
                <RouterLink :to="{ name: 'adminPage' }" class="dropdown-item text-danger fw-bold">관리자 페이지</RouterLink>
              </li>
            </ul>
          </li>
          <li>
            <RouterLink :to=" { name: 'lecture-list' }" class="nav-link px-3 text-white">
              수강 신청
            </RouterLink>
          </li>
          <li>
            <RouterLink :to="{ name: 'departments' }" class="nav-link px-3 text-white">
              학과 전체 조회
            </RouterLink>
          </li>
          <li v-if="authStore.memberInfo.role === 'PROFESSOR'">
            <RouterLink :to="{ name: 'lecture-create' }" class="nav-link px-3 text-white">
              강의 생성
            </RouterLink>
          </li>
          <li v-if="authStore.memberInfo.role === 'PROFESSOR'">
            <RouterLink :to="{ name: 'lecture-manage' }" class="nav-link px-3 text-white">
              강의 관리
            </RouterLink>
          </li>
          <li v-if="authStore.memberInfo.role === 'ADMIN' && memberId">
            <RouterLink :to="{ name: 'MyRegistration', params: { memberId } }" class="nav-link px-3 text-white">나의 수강신청 조회</RouterLink>
          </li>
          <li v-if="authStore.memberInfo.role === 'ADMIN'">
            <RouterLink :to="{ name: 'DurationOfRegistration' }" class="nav-link px-3 text-white">
              수강 신청 기간 관리
            </RouterLink>
          </li>

          <!-- 수강신청 기간 확인 버튼 -->
          <button type="button" class="btn btn-dark ms-3" data-bs-toggle="modal" data-bs-target="#exampleModal">
            수강 신청 기간
          </button>
        </ul>

        <!-- <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
          <input
            type="search"
            class="form-control form-control-dark text-bg-dark"
            placeholder="Search..."
            aria-label="Search"
          />
        </form> -->

        <div class="text-end">
          <template v-if="authStore.isLoggedIn">
            <button @click="handleLogout" class="btn btn-outline-light me-2">Logout</button>
          </template>
          <template v-else>
            <RouterLink :to="{ name: 'login' }" class="btn btn-outline-light me-2">Login</RouterLink>
          </template>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body" v-if="state.tableData">
            {{ state.tableData.startDate }} ~ {{ state.tableData.endDate }}
          </div>
          <div class="modal-body" v-else>
            수강 신청 기간이 아닙니다.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { onMounted, reactive, ref, onBeforeUnmount } from 'vue'
import axios from '@/api/axios'
import { useAuthStore } from '../../stores/auth'

const authStore = useAuthStore()

const memberId = localStorage.getItem('memberId')

const handleLogout = () => {
  authStore.logout()
}

const state = reactive({
  tableData: null,
})

const getDuration = async () => {
  try {
    const response = await axios.get('/duration')
    state.tableData = response.data || null
    console.log('불러온 데이터:', state.tableData)
  } catch (error) {
    console.error('수강 신청 기간 불러오기 실패:', error.response?.data || error.message)
  }
}

onMounted(() => {
  const modalElement = document.getElementById('exampleModal')
  if (modalElement) {
    modalElement.addEventListener('show.bs.modal', getDuration)
  }
})

onBeforeUnmount(() => {
  const modalElement = document.getElementById('exampleModal')
  if (modalElement) {
    modalElement.removeEventListener('show.bs.modal', getDuration)
  }
})
</script>

<style lang="scss" scoped>
header {
  // 헤더를 조금 더 두툼하게
  min-height: 80px;
  font-size: 1rem;

  .nav-link {
    font-size: 1.1rem;
    padding: 0.75rem 1rem;
  }

  .btn {
    font-size: 1rem;
    padding: 0.6rem 1.2rem;
  }
}
</style>
