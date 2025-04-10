<template>
    <div class="login-container">
      <h2>로그인</h2>
      <form @submit.prevent="submitClick">
        <div class="form-group">
          <label for="email">이메일</label>
          <input
            type="email"
            id="email"
            required
            placeholder="you@example.com"
            v-model.trim="formData.email"
          />
        </div>
  
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input
            type="password"
            id="password"
            required
            placeholder="비밀번호 입력"
            v-model.trim="formData.password"
          />
        </div>
  
        <button type="submit">로그인</button>
  
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '@/stores/auth';
  
  const errorMessage = ref('')
  const router = useRouter()
  const authStore = useAuthStore();
  const formData = reactive({
        email: '',
        password: ''
    });

  const submitClick = () => {
    authStore.login(formData);
  };

  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 100px auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
    background-color: #fff;
  }
  
  h2 {
    text-align: center;
    margin-bottom: 1.5rem;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 600;
  }
  
  input {
    width: 100%;
    padding: 0.6rem;
    border: 1px solid #ccc;
    border-radius: 6px;
  }
  
  button {
    width: 100%;
    padding: 0.7rem;
    background-color: #3498db;
    color: white;
    font-weight: bold;
    border: none;
    border-radius: 6px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #2980b9;
  }
  
  .error {
    color: red;
    margin-top: 1rem;
    text-align: center;
  }
  </style>
  