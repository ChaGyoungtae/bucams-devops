<template>
    <div class="container mt-5">
      <h2>관리자 - 회원 관리</h2>
  
      <div class="card my-4">
        <div class="card-header bg-primary text-white">회원 등록</div>
        <div class="card-body">
          <form @submit.prevent="registerMember">
            <div class="mb-3">
              <label class="form-label">이름</label>
              <input v-model="newMember.name" type="text" class="form-control" required />
            </div>
            <div class="mb-3">
              <label class="form-label">이메일</label>
              <input v-model="newMember.email" type="email" class="form-control" required />
            </div>
            <div class="mb-3">
              <label class="form-label">비밀번호</label>
              <input v-model="newMember.password" type="password" class="form-control" required />
            </div>
            <div class="mb-3">
              <label class="form-label">역할</label>
              <select v-model="newMember.role" class="form-select" required>
                <option value="STUDENT">학생</option>
                <option value="PROFESSOR">교수</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label">학번/교번</label>
              <input v-model="newMember.no" type="text" class="form-control" required />
            </div>
            
            <div class="mb-3">
              <label class="form-label">전화번호</label>
              <input v-model="newMember.phone" type="text" class="form-control" required />
            </div>
            
            <div class="mb-3">
              <label class="form-label">학과</label>
              <select v-model="newMember.departmentId" class="form-select" required>
                <option :value="null" disabled>학과를 선택하세요</option>
                <option v-for="dept in departments" :key="dept.id" :value="dept.id">
                  {{ dept.name }}
                </option>
              </select>
            </div>
            
            <div class="mb-3">
              <label class="form-label">상태</label>
              <select v-model="newMember.status" class="form-select" required>
                <option value="ACTIVE">재학/재직 중</option>
                <option value="INACTIVE">휴학</option>
                <option value="DELETED">퇴직/졸업</option>
              </select>
            </div>
            <button class="btn btn-success" type="submit">회원 등록</button>
          </form>
        </div>
      </div>
  
      <!-- 회원 목록 -->
      <div class="card">
        <div class="card-header bg-secondary text-white">회원 목록</div>
        <div class="card-body">
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>이름</th>
                <th>이메일</th>
                <th>역할</th>
                <th>학번/교번</th>
                <th>전화번호</th>
                <th>상태</th>
                <th>학과</th>
                <th>수강 학점</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="member in members" :key="member.id">
                <td>{{ member.name }}</td>
                <td>{{ member.email }}</td>
                <td>{{ getRoleLabel(member.role) }}</td>
                <td>{{ member.no }}</td>
                <td>{{ member.phone }}</td>
                <td>{{ getStatusLabel(member.status) }}</td>
                <td>{{ member.departmentName }}</td>
                <td>{{ member.currentCredits }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>


    <div class="list-group">
      <div class="list-group-item list-group-item-action d-flex gap-3 py-3" 
          v-for="(registration, index) in registrations" :key="index">
          <ul style="list-style-type: none; margin: 0; padding: 0;">
              <li class="registration-item">
                  <div class="registration-registeredAt">
                      <strong>Registered At </strong> {{ registration.registeredAt }}
                  </div>
                  <div class="registration-lectureId">
                      <strong>Lecture ID:</strong> {{ registration.lectureId }}
                  </div>
                  <div class="registration-memberId">
                      <strong>Member ID:</strong> {{ registration.memberId }}
                  </div>
              </li>
          </ul>
      </div>
  </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import apiClient from '@/api/axios'
import { useRoute } from 'vue-router'

const getRoleLabel = (role) => {
  switch (role) {
    case 'STUDENT':
      return '학생'
    case 'PROFESSOR':
      return '교수'
    case 'ADMIN':
      return '관리자'
    default:
      return role
  }
}

const getStatusLabel = (status) => {
  switch (status) {
    case 'ACTIVE':
      return '재학/재직 중'
    case 'INACTIVE':
      return '휴학'
    case 'DELETED':
      return '퇴직/졸업'
    default:
      return status
  }
}
  
  const members = ref([])
  const newMember = ref({
  name: '',
  email: '',
  password: '',
  role: null,
  no: '',
  phone: '',
  status: 'ACTIVE',
  departmentId: null
})
  
  const departments = ref([])

// 학과 목록 불러오기
const fetchDepartments = async () => {
  try {
    const response = await apiClient.get('/departments')
    console.log(response);
    
    departments.value = response.data
  } catch (error) {
    alert('학과 목록을 불러오는 데 실패했습니다.')
  }
}

  // 회원 목록 조회
  const fetchMembers = async () => {
    try {
      const response = await apiClient.get('/members/show-all') // 전체 회원 목록 API
      members.value = response.data
    } catch (error) {
      alert('회원 목록을 불러오는 데 실패했습니다.')
    }
  }
  
  // 회원 등록 요청
  const registerMember = async () => {
    try {
      const response = await apiClient.post('/members/register', newMember.value)
      alert('회원을 등록하셨습니다.')
      fetchMembers() // 등록 후 목록 다시 불러오기

      Object.assign(newMember.value, {
        name: '',
        email: '',
        password: '',
        role: null,
        no: '',
        phone: '',
        status: 'ACTIVE',
        departmentId: null
      })
    } catch (error) {
      alert('회원 등록에 실패하셨습니다.')
    }
  }

  const registrations = ref([]);
const lecture = ref([]);
const member = ref([]);
const lectureName = ref('');
const memberId = ref('');
const route = useRoute();
const memberName = ref('');

// 수강신청 내역 가져오기
onMounted(async() => {
  


  apiClient.get('/registration')
  .then(response => {
      registrations.value = response.data;
  
  }).catch(error => {
      console.error("댓글 데이터 로딩 중 오류 발생", error);
  });

});
  
  onMounted(() => {
  fetchDepartments()
  fetchMembers()
})
  </script>
  
  <style>
.list-group {
  border: transparent;
  padding: 4px; /* 이미지와 테두리 사이 여백 */
  border-radius: 0%; 
}
</style>