<template>
    <div class="container mt-5">
      <div class="card">
        <div class="card-header bg-info text-white">
          <h4 class="mb-0">내 정보</h4>
        </div>
        <div class="card-body">
          <table class="table table-striped">
            <tbody>
              <tr>
                <th scope="row">이름</th>
                <td>{{ member.name }}</td>
              </tr>
              <tr>
                <th scope="row">학번</th>
                <td>{{ member.no }}</td>
              </tr>
              <tr>
                <th scope="row">이메일</th>
                <td>{{ member.email }}</td>
              </tr>
              <tr>
                <th scope="row">전화번호</th>
                <td>{{ member.phone }}</td>
              </tr>
              <tr>
                <th scope="row">상태</th>
                <td>{{ getStatusLabel(member.status) }}</td>
              </tr>
              <tr>
                <th scope="row">역할</th>
                <td>{{ getRoleLabel(member.role) }}</td>
              </tr>
              <tr>
                <th scope="row">소속 학과</th>
                <td>{{ member.departmentName }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import apiClient from '@/api/axios';
  
  const member = ref({
    name: '',
    no: '',
    email: '',
    phone: '',
    status: '',
    role: '',
    departmentName: ''
  })
  
  const memberId = localStorage.getItem('memberId');

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

  onMounted(async () => {
    try {
      const response = await apiClient.get(`/members/my-info?memberId=${memberId}`)
      
      member.value = response.data
    } catch (error) {
        alert('회원 정보를 찾지 못했습니다.');
    }
  })
  </script>
  