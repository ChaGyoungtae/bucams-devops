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
                        <!-- <strong>강의명:</strong> {{ registration.lectureName }} -->
                    </div>
                    <div class="registration-memberId">
                        <strong>Member ID:</strong> {{ registration.memberId }}
                    </div>
                </li>
            </ul>
            <div class="button-container ms-auto">
        <!-- 삭제 버튼 -->
        <!-- <button
            @click.stop="confirmDelete(index, registration.lectureId)"
            class="btn btn-outline-danger btn-sm"
        >Delete
        </button>-->
        </div>             
            <div class="button-container ms-auto">
                <button 
                    @click.stop="deleteRegistration(index)" 
                    class="btn btn-outline-danger btn-sm">
                    Delete
                </button>
            </div>
        </div>
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

    // memberId 잘 받아오는지 확인
    // console.log(String(route.params.memberId));
    // console.log(memberId.value);

    apiClient.get(`/registration/${memberId.value}`)
    .then(response => {
        registrations.value = response.data;
    
    }).catch(error => {
        console.error("수강신청 데이터 로딩 중 오류 발생", error);
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

// 강의명 가져오기 - 보류
// onMounted(async () => {
//     // console.log(lecture);
//     // 이름 받아올려고 추가 lectureName.value = lecture.value.name;
//     apiClient.get('lectures/search')
//     .then(response => {
//         lecture.value = response.data;
//         lectureName.value = lecture.value.name;
//     })
//     .catch(error => {
//         console.error("강의 데이터 로딩 중 오류 발생:", error);
//     });
//     });



// 수강신청 내역 삭제

// const deleteRegistration = async (index) => {
//   if (confirm('삭제하시겠습니까?')) {

//     await apiClient.delete(`/registrations.value[index].lectureId`)
//     .then(alert('삭제 완료'))

//     comments.value.splice(index,1);
//   }
// };

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
