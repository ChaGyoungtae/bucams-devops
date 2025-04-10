<template>
  <div class="lecture-form">
    <h2>강의 등록</h2>
    <form @submit.prevent="submitLecture">
      <div class="form-group">
        <label for="lectureName">강의명</label>
        <input id="lectureName" v-model="lectureName" placeholder="예: 자바스크립트 기초" />
        <small v-if="errors.lectureName" class="error">{{ errors.lectureName }}</small>
      </div>

      <div class="form-group">
        <label for="credit">학점</label>
        <input id="credit" v-model.number="credit" type="number" placeholder="예: 3" />
        <small v-if="errors.credit" class="error">{{ errors.credit }}</small>
      </div>

      <div class="form-group">
        <label for="limitCount">수강 정원</label>
        <input id="limitCount" v-model.number="limitCount" type="number" placeholder="예: 40" />
        <small v-if="errors.limitCount" class="error">{{ errors.limitCount }}</small>
      </div>

      <div class="form-group">
        <label for="scheduleInput">강의 시간</label>
        <input id="scheduleInput" v-model="scheduleInput" placeholder="예: 수 1-3, 목 4-6" />
        <small v-if="errors.scheduleInput" class="error">{{ errors.scheduleInput }}</small>
      </div>

      <div class="form-group">
        <label for="lectureType">강의 유형</label>
        <select id="lectureType" v-model="lectureType">
          <option value="MAJOR">전공</option>
          <option value="GENERAL">교양</option>
        </select>
      </div>

      <button type="submit">강의 등록</button>
    </form>
  </div>
</template>

<script>
import api from '@/api/axios';

export default {
  data() {
    return {
      lectureName: '',
      credit: 0,
      limitCount: 0,
      scheduleInput: '',
      lectureType: 'MAJOR',
      errors: {},
    };
  },
  methods: {
    validateForm() {
      this.errors = {};
      if (!this.lectureName.trim()) {
        this.errors.lectureName = '강의명을 입력하세요.';
      }
      if (!this.scheduleInput.trim()) {
        this.errors.scheduleInput = '강의 시간을 입력하세요.';
      }
      if (this.credit < 1) {
        this.errors.credit = '학점은 1 이상이어야 합니다.';
      }
      if (this.limitCount < 15) {
        this.errors.limitCount = '정원은 15명 이상이어야 합니다.';
      }

      return Object.keys(this.errors).length === 0;
    },
    async submitLecture() {

      if (!this.validateForm()) return;

      const memberId = localStorage.getItem('memberId');
      const payload = {
        professorId: Number(memberId),
        lectureName: this.lectureName,
        credit: this.credit,
        limitCount: this.limitCount,
        schedule: this.scheduleInput.trim(),
        lectureType: this.lectureType,
      };

      try {
        const res = await api.post('/lectures', payload);
        alert('강의 등록 성공');
        this.$emit('lecture-added', res.data);
        this.$router.push({ name: 'lecture-manage' });
      } catch (err) {
        alert(err.response?.data?.message || '등록 실패');
      }
    },
  },
};
</script>

<style scoped>
.lecture-form {
  max-width: 600px;
  margin: 3rem auto;
  padding: 2rem 2.5rem;
  background-color: #ffffff;
  border-radius: 16px;
  font-family: 'Segoe UI', 'Pretendard', sans-serif;
}

h2 {
  text-align: center;
  font-size: 1.75rem;
  margin-bottom: 2rem;
  font-weight: 600;
  color: #0d6efd;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  margin-bottom: 1.4rem;
}

label {
  font-weight: 500;
  color: #495057;
  font-size: 0.95rem;
}

input,
select {
  padding: 0.65rem 0.9rem;
  border: 1px solid #ced4da;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.2s ease;
  background-color: #f8f9fa;
}

input:focus,
select:focus {
  outline: none;
  background-color: #ffffff;
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.18rem rgba(13, 110, 253, 0.2);
}

button {
  width: 100%;
  padding: 0.75rem;
  background-color: #0d6efd;
  border: none;
  border-radius: 10px;
  color: #ffffff;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.25s ease;
}

button:hover {
  background-color: #0b5ed7;
}
.error {
  color: #dc3545;
  font-size: 0.85rem;
  margin-top: 0.2rem;
}

@media (max-width: 576px) {
  .lecture-form {
    padding: 1.5rem;
  }

  input,
  select {
    font-size: 0.95rem;
  }

  button {
    font-size: 1rem;
  }


}
</style>
