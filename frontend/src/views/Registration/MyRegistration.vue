<template>
    <div class="container mt-4">
      <h3 class="mb-3">수강 신청 목록</h3>
      <table class="table table-striped table-hover align-middle text-center">
        <thead class="table-dark">
          <tr>
            <th>#</th>
            <th>신청 날짜</th>
            <th>강의명</th>
            <th>교수명</th>
            <th>시간표</th>
            <th>전공 유형</th>
            <th>학점</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(registration, index) in registrations" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <td>{{ registration.registeredAt }}</td>
            <td>{{ registration.lectureName }}</td>
            <td>{{ registration.professorName }}</td>
            <td>{{ registration.schedule }}</td>
            <td>{{ registration.type }}</td>
            <td>{{ registration.credit }}</td>
            <td>
              <button
                @click.stop="deleteRegistration(index)"
                class="btn btn-outline-danger btn-sm"
              >
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  

<script setup>
import apiClient from '@/api/axios.js';
import { ref, onMounted } from 'vue';
import { useRoute,useRouter  } from 'vue-router';

const registrations = ref([]);
const memberId = ref('');
const route = useRoute();

const lecture = ref([]);
const member = ref([]);
const lectureName = ref('');
const memberName = ref('');

// 수강신청 내역 가져오기
onMounted(async() => {
    
    memberId.value = String(route.params.memberId);

    apiClient.get(`/registration/${memberId.value}`)
    .then(response => {
        registrations.value = response.data;
    
    }).catch(error => {
        console.error("수강신청 데이터 로딩 중 오류 발생", error);
    });

});


const deleteRegistration = async (index) => {
    const registrationId = registrations.value[index].registrationId;

    const isConfirmed = window.confirm("수강신청 내역을 삭제하시겠습니까?");
    if (isConfirmed) {
        try {
            console.log(`Deleting registration with Registration ID: ${registrationId}`);
            await apiClient.delete(`/registration/${registrationId}`);
            
            // 성공적으로 삭제된 경우 로컬 데이터에서 제거
            registrations.value.splice(index, 1);
            alert("수강신청 내역이 삭제되었습니다.");
        } catch (error) {
            console.error("삭제 중 오류 발생:", error);
        }
    } else {
        alert("삭제를 취소했습니다.");
    }
};



</script>

<style>
.list-group {
    border: transparent;
    padding: 4px; /* 이미지와 테두리 사이 여백 */
    border-radius: 0%; 
}
</style>
