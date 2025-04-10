<template>
  <div>
    <div class="table-responsive small">
      <table class="table table-striped table-sm">
        <thead>
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in tableData" :key="row.id">
            <td>{{ row.id }}</td>
            <td>{{ row.name }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axios'
import { reactive, toRefs, onMounted } from 'vue';

export default {
  setup () {
    const state = reactive({
      tableData: []
    });

    onMounted(async () => {
      try {
        const response = await axios.get('/departments'); // 백엔드 엔드포인트 주소
        state.tableData = response.data;
      } catch (error) {
        console.error('부서 데이터를 불러오는 데 실패했습니다:', error);
      }
    });

    return {
      ...toRefs(state),
    };
  }
}
</script>

<style lang="scss" scoped>
</style>
