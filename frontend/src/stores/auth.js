import apiClient from "@/api/axios";
import { defineStore } from "pinia";
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";

export const useAuthStore = defineStore('auth', () => {
    const router = useRouter();

    const isLoggedIn = ref(false);

    const memberInfo = reactive({
        email: '',   
        name: '',  
        role: ''
    });

    const memberId = ref(null);

    // onMounted(() => {
    //     checkLogin(); 
    // });
    
    const login = async (loginData) => {
        console.log(loginData);

        try {
            const response = await apiClient.post('/members/login', loginData);
            console.log(response);
            memberId.value = response.data;

            localStorage.setItem('memberId', memberId.value)
            if(response.status === 200) {
                
                const userNameResponse = await apiClient.get(`/members/my-info?memberId=${memberId.value}`);

                localStorage.setItem('memberInfo', JSON.stringify({
                    email: userNameResponse.data.email,
                    name: userNameResponse.data.name,
                    role: userNameResponse.data.role
                }));

                Object.assign(memberInfo, JSON.parse(localStorage.getItem('memberInfo')));

                isLoggedIn.value = true;

                router.push({name: 'base'});
            }
        } catch (error) {
            alert('로그인이 제대로 처리되지 않았습니다.');
        }
    };

    const checkLogin = () => {
        const storedMember = localStorage.getItem('memberInfo');
        const storedMemberId = localStorage.getItem('memberId');
      
        if (storedMember && storedMemberId) {
          Object.assign(memberInfo, JSON.parse(storedMember));
          isLoggedIn.value = true;
        } else {
          isLoggedIn.value = false;
        }
      };

    const logout = async () => {
        try {
            // const memberId = localStorage.getItem('memberId');

            // const response = await apiClient.post('/logout');

            // if (response.status === 204) {
                logoutUser();
            // }
        } catch (error) {
            alert('로그아웃에 문제가 발생했습니다.');
        }
    };

    const logoutUser = () => {
        console.log(localStorage.getItem('memberId'));
        console.log(localStorage.getItem('memberInfo'));
        
        localStorage.removeItem('memberInfo');
        localStorage.removeItem('memberId');

        isLoggedIn.value = false;

        router.push({name: 'base'});
    };

    return { isLoggedIn, memberInfo, login, logout, checkLogin };
});