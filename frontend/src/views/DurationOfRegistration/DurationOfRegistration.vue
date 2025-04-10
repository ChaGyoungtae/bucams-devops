<template>
  <div class="container py-5">
    <h2 class="text-center mb-4">수강 신청 기간 관리</h2>

    <!-- 등록 폼 -->
    <form @submit.prevent="handleSubmit" class="mb-4 d-flex flex-column gap-3 align-items-center">
      <div class="d-flex gap-3 flex-wrap justify-content-center">
        <div>
          <label for="startDate" class="form-label">시작 시간</label>
          <input type="datetime-local" id="startDate" v-model="form.startDate" class="form-control" required />
        </div>
        <div>
          <label for="endDate" class="form-label">종료 시간</label>
          <input type="datetime-local" id="endDate" v-model="form.endDate" class="form-control" required />
        </div>
      </div>
      <button type="submit" class="btn btn-primary w-25">등록</button>
    </form>

    <!-- 조회 -->
    <div v-if="duration" class="text-center mb-3">
      <h5>등록된 수강 신청 기간</h5>
      <p>{{ duration.startDate }} ~ {{ duration.endDate }}</p>
      <button class="btn btn-danger" @click="handleDelete">삭제</button>
    </div>
    <div v-else class="text-center text-muted">
      등록된 수강 신청 기간이 없습니다.
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import axios from '@/api/axios'

// form 상태
const form = reactive({
  startDate: '',
  endDate: ''
})

// 등록된 기간 상태
const duration = ref(null)
const durationId = ref(null) // 삭제할 때 필요

// 기간 등록
const handleSubmit = async () => {
  try {
    const response = await axios.post('/duration', {
      startDate: new Date(form.startDate).toISOString(),
      endDate: new Date(form.endDate).toISOString()
    })
    durationId.value = response.data
    await fetchDuration()
    form.startDate = ''
    form.endDate = ''
  } catch (error) {
    const message = error.response?.data?.message || '등록 실패'
    alert("이미 수강신청 기간이 등록되어 있습니다.")
    console.error('등록 실패:', message)
  }
}

// 기간 조회
const fetchDuration = async () => {
  try {
    const res = await axios.get('/duration')
    duration.value = res.data
    durationId.value = res.data.id
    
  } catch (error) {
    duration.value = null
    console.log('조회 실패 또는 등록된 기간 없음')
  }
}

// 기간 삭제
const handleDelete = async () => {
  if (!durationId.value) return
  try {
    await axios.delete(`/duration/${durationId.value}`)
    duration.value = null
    durationId.value = null
  } catch (error) {
    console.error('삭제 실패:', error)
  }
}

const formatDate = (iso) => {
  const date = new Date(iso)
  return date.toLocaleString()
}

// 최초 로딩 시 조회
onMounted(() => {
  fetchDuration()
  console.log('duration.value:', duration.value);
  
})
</script>

<style scoped>
.container {
  max-width: 800px;
}
</style>
