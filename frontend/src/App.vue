<template>
  <div>

    <RouterView/>
  </div>
</template>

<script setup>
  import { useAuthStore } from './stores/auth';
// export default {
//   name: "App",
// };
  const authStore = useAuthStore();

  authStore.checkLogin();
</script>

<style>
</style>
