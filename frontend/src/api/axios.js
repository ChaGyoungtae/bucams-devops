import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  timeout: 1000,
});

apiClient.interceptors.request.use(
  (config) => {
      if (config._skipInterceptor) {
          return config;
      }

      // 로컬 스토리지에서 accessToken을 가져온다.
      const memberId = localStorage.getItem('memberId');


      return config;
  },
  (error) => {
      // 비동기 코드에서 에러를 처리하거나 에러를 즉시 반환할 때 사용한다.
      return Promise.reject(error);
  }
);



export default apiClient;
