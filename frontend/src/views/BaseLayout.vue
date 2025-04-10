<template>
  <Header></Header>
  <!-- 
  요청된 URI 경로와 router 객체의 routes 배열에 있는 경로를 비교하여 
  매칭된 라우트의 컴포넌트를 마운트한다. 
  -->
  <RouterView></RouterView>
</template>

<script setup>
  import Header from '@/components/common/Header.vue';
</script>