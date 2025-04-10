
<template>
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
import apiClient from '@/api/axios.js';
import { ref, onMounted } from 'vue';
import { useRoute,useRouter  } from 'vue-router';

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

// 멤버 이름 가져오기 - 보류
// onMounted(async () => {
//     // console.log(lecture);
//     // 이름 받아올려고 추가 lectureName.value = lecture.value.name;
//     apiClient.get('members/my-info')
//       .then(response => {
//         member.value = response.data;
//         memberName.value = member.value.name;
//       })
//       .catch(error => {
//         console.error("멤버 데이터 로딩 중 오류 발생:", error);
//       });
//     });





</script>

<style>
.list-group {
  border: transparent;
  padding: 4px; /* 이미지와 테두리 사이 여백 */
  border-radius: 0%; 
}
</style>
