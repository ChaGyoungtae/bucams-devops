<template>
  <div class="lecture-container">
    <h2>내가 등록한 강의 목록</h2>

    <div v-if="lectures.length > 0" class="lecture-list">
      <div v-for="lecture in lectures" :key="lecture.lectureId" class="lecture-card">

        <!-- 수정 중인 강의 -->
        <div v-if="editingLectureId === lecture.lectureId">
          <h3>강의 수정</h3>
          <form @submit.prevent="submitEdit(lecture.lectureId)">
            <!-- 강의명 -->
            <label>강의명</label>
            <input v-model="editForm.name" placeholder="강의명" />
            <small v-if="errors.name" class="error">{{ errors.name }}</small>

            <!-- 학점 -->
            <label>학점</label>
            <input v-model.number="editForm.credit" type="number" placeholder="학점" />
            <small v-if="errors.credit" class="error">{{ errors.credit }}</small>

            <!-- 정원 -->
            <label>정원</label>
            <input v-model.number="editForm.limitCount" type="number" placeholder="정원" />
            <small v-if="errors.limitCount" class="error">{{ errors.limitCount }}</small>

            <!-- 강의 시간 -->
            <label>강의 시간</label>
            <input v-model="editForm.schedule" placeholder="예: 수 1-3, 목 2-4" />
            <small v-if="errors.schedule" class="error">{{ errors.schedule }}</small>

            <label>강의 유형</label>
            <select v-model="editForm.lectureType">
              <option value="MAJOR">전공</option>
              <option value="GENERAL">교양</option>
            </select>

            <button type="submit" class="save-btn">저장</button>
          </form>
        </div>

        <div v-else>
          <h3>{{ lecture.name }}</h3>
          <p><strong>시간:</strong> {{ lecture.schedule }}</p>
          <p><strong>학점:</strong> {{ lecture.credit }} | <strong>정원:</strong> {{ lecture.limitCount }}명</p>
          <div class="button-group">
            <button class="edit-btn" @click="editLecture(lecture)">수정</button>
            <button class="delete-btn" @click="deleteLecture(lecture.lectureId)">삭제</button>
          </div>
        </div>
      </div>
    </div>

    <p v-else class="no-lecture-msg">등록된 강의가 없습니다.</p>
  </div>
</template>

<script>
import api from '@/api/axios';

export default {
  data() {
    return {
      lectures: [],
      editingLectureId: null,
      editForm: {
        name: '',
        credit: 0,
        limitCount: 0,
        schedule: '',
        lectureType: 'MAJOR',
      },
      errors: {},
    };
  },
  mounted() {
    this.fetchMyLectures();
  },
  methods: {

    validateEditForm() {
      this.errors = {};
      if (!this.editForm.name.trim()) {
        this.errors.name = '강의명을 입력하세요.';
      }
      if (!this.editForm.schedule.trim()) {
        this.errors.schedule = '강의 시간을 입력하세요.';
      }
      if (this.editForm.credit < 1) {
        this.errors.credit = '학점은 1 이상이어야 합니다.';
      }
      if (this.editForm.limitCount < 15) {
        this.errors.limitCount = '정원은 15명 이상이어야 합니다.';
      }
      return Object.keys(this.errors).length === 0;
    },
    async fetchMyLectures() {
      try {
        const memberId = localStorage.getItem('memberId');
        if (!memberId) {
          alert('memberId가 설정되어 있지 않습니다.');
          return;
        }

        const res = await api.get('/lectures/myLectures', {
          params: { memberId },
        });
        this.lectures = res.data;
      } catch (err) {
        console.error('강의 목록 조회 실패:', err);
        alert('강의 목록을 불러오지 못했습니다.');
      }
    },

    editLecture(lecture) {
      this.editingLectureId = lecture.lectureId;
      this.editForm = {
        name: lecture.name,
        credit: lecture.credit,
        limitCount: lecture.limitCount,
        schedule: lecture.schedule,
        lectureType: lecture.lectureType,
      };
    },

    async submitEdit(lectureId) {

      if (!this.validateEditForm()) return;
      try {
        const memberId = localStorage.getItem('memberId');
        const payload = {
          professorId: Number(memberId),
          lectureId: lectureId,
          lectureName: this.editForm.name,
          credit: this.editForm.credit,
          limitCount: this.editForm.limitCount,
          schedule: this.editForm.schedule.trim(),
          lectureType: this.editForm.lectureType,
        };

        await api.post('/lectures/update', payload);
        alert('강의가 수정되었습니다.');
        this.editingLectureId = null;
        this.fetchMyLectures();
      } catch (err) {
        console.error('강의 수정 실패:', err);
        alert(err.response?.data?.message || '수정 실패');
      }
    },

    async deleteLecture(lectureId) {
      const confirmDelete = confirm('정말로 이 강의를 삭제하시겠습니까?');
      if (!confirmDelete) return;

      try {
        const memberId = localStorage.getItem('memberId');
        await api.delete(`/lectures/${lectureId}`, {
          params: { memberId },
        });
        this.lectures = this.lectures.filter(l => l.lectureId !== lectureId);
        alert('강의가 삭제되었습니다.');
      } catch (err) {
        console.error('강의 삭제 실패:', err);
        alert(err.response?.data?.message || '삭제 실패');
      }
    },
  },
};
</script>

<style scoped>
.lecture-container {
  max-width: 900px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #fff;
  border: 1px solid #dee2e6;
  border-radius: 0.75rem;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.05);
  font-family: 'Segoe UI', sans-serif;
}

.lecture-container h2 {
  font-size: 1.6rem;
  color: #343a40;
  font-weight: 600;
  margin-bottom: 1.5rem;
  text-align: center;
}

.lecture-list {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.lecture-card {
  border: 1px solid #e0e0e0;
  padding: 1.25rem;
  border-radius: 0.625rem;
  background-color: #f9f9f9;
  transition: background-color 0.2s;
}

.lecture-card:hover {
  background-color: #f1f5ff;
}

.lecture-card h3 {
  margin: 0 0 0.75rem;
  font-size: 1.15rem;
  color: #0d6efd;
  font-weight: 600;
}

.lecture-card p {
  margin: 0.25rem 0;
  font-size: 0.95rem;
  color: #333;
}

.button-group {
  margin-top: 1rem;
  display: flex;
  gap: 0.5rem;
}

.edit-btn,
.delete-btn,
.save-btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 0.5rem;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.edit-btn {
  background-color: #0d6efd;
  color: white;
}

.edit-btn:hover {
  background-color: #0a58ca;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
}

.delete-btn:hover {
  background-color: #b02a37;
}

.save-btn {
  background-color: #198754;
  color: white;
}

.save-btn:hover {
  background-color: #146c43;
}

form {
  margin-top: 1rem;
}

label {
  font-weight: 600;
  margin-top: 0.5rem;
  margin-bottom: 0.3rem;
  display: block;
  color: #495057;
}

input,
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ced4da;
  border-radius: 0.375rem;
  margin-bottom: 0.75rem;
  font-size: 0.95rem;
  box-sizing: border-box;
}

input:focus,
select:focus {
  outline: none;
  border-color: #86b7fe;
  box-shadow: 0 0 0 0.15rem rgba(13, 110, 253, 0.25);
}

.no-lecture-msg {
  text-align: center;
  font-size: 1rem;
  color: #6c757d;
  margin-top: 2rem;
  font-style: italic;
}

.error {
  color: #dc3545;
  font-size: 0.85rem;
  margin-top: -0.4rem;
  margin-bottom: 0.6rem;
  display: block;
}
</style>
