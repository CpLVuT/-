<template>
  <div class="dashboard">
    <AppSidebar />
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import AppSidebar from '../components/Siderbar.vue';

export default {
  name: 'AppDashboard', // 修改组件名称为多词名称
  components: {
    AppSidebar
  }
};
</script>

<style scoped>
.dashboard {
  display: flex;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>
