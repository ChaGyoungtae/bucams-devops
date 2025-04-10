<template>
  <div class="container mt-4">
    <h2 class="mb-4">전체 강의 목록</h2>

    <div v-if="message" class="alert" :class="messageTypeClass" role="alert">
      {{ message }}
    </div>

    <div class="row mb-3 g-2">
      <div class="col-md-3">
        <select class="form-select" v-model="searchOption">
          <option disabled value="">검색 기준 선택</option>
          <option value="LECTURE_NAME">강의명</option>
          <option value="LECTURE_PROFESSOR">교수명</option>
          <option value="LECTURE_DEPARTMENT">학과</option>
          <option value="LECTURE_TYPE">강의유형</option>
        </select>
      </div>
      <div class="col-md-6">
        <input type="text" class="form-control" placeholder="검색어 입력" v-model="keyword" />
      </div>
      <div class="col-md-3">
        <button class="btn btn-primary w-100" @click="fetchLectures">
          검색
        </button>
      </div>
    </div>

    <table class="table table-bordered text-center align-middle">
      <thead class="table-primary">
      <tr>
        <th>NO</th>
        <th>신청</th>
        <th>강의명</th>
        <th>교수명</th>
        <th>학과</th>
        <th>학점</th>
        <th>강의유형</th>
        <th>강의시간</th>
        <th>수강인원</th>
      </tr>
      </thead>
      <tbody>
      <tr v-if="lectures.length === 0">
        <td colspan="9" class="text-muted">강의가 없습니다.</td>
      </tr>
      <tr v-else v-for="(lecture, index) in lectures" :key="lecture.lectureId">
        <td>{{ index + 1 }}</td>
        <td>
          <button class="btn btn-sm btn-outline-primary" @click="registerLecture(lecture.lectureId)">
            신청
          </button>
        </td>
        <td>{{ lecture.name }}</td>
        <td>{{ lecture.professorName }}</td>
        <td>{{ lecture.department }}</td>
        <td>{{ lecture.credit }}</td>
        <td>{{ getLectureTypeName(lecture.lectureType) }}</td>
        <td>{{ lecture.schedule }}</td>
        <td>{{ lecture.currCount }} / {{ lecture.limitCount }}</td>
      </tr>

      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/axios'

const lectures = ref([])
const keyword = ref('')
const searchOption = ref('')


const getLectureTypeName = (type) => {
  return type === 'MAJOR' ? '전공' : type === 'GENERAL' ? '교양' : type
}

const fetchLectures = async () => {
  try {
    const params = {}
    if (keyword.value && searchOption.value) {
      params.keyword = keyword.value
      params.searchOption = searchOption.value
    }
    const res = await api.get('/lectures/search', { params })
    lectures.value = res.data

  } catch (error) {
    lectures.value = []
  }
}

const registerLecture = async (lectureId) => {
  const studentId = localStorage.getItem('memberId')

  try {
    const res = await api.post(`/lectures/registration/${lectureId}`, null, {
      params: { studentId },
    })
    const lectureName = res.data.lectureName || '해당 과목'
    alert(`과목 "${lectureName}"이 수강신청 완료되었습니다.`)
    fetchLectures()

  } catch (error) {
    const msg = error.response?.data?.message || '수강신청이 실패했습니다.'
    alert(msg)
  }
}


onMounted(fetchLectures)
</script>

<style scoped>
.table {
  font-size: 0.95rem;
}
</style>
