<template>
  <div class="sidebar">
    <ul>
      <li v-for="item in menuItems" :key="item.path">
        <router-link :to="item.path">{{ item.name }}</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AppSidebar', // 修改组件名称为多词名称
  data() {
    return {
      menuItems: [
        { name: "纵向课题", path: "/dashboard/vertical-projects" },
        { name: "横向课题", path: "/dashboard/horizontal-projects" },
        { name: "论文", path: "/dashboard/papers" },
        { name: "专利", path: "/dashboard/patents" },
        { name: "审核记录", path: "/dashboard/review-records" },
        { name: "用户管理", path: "/dashboard/users" }
      ]
    };
  }
};
</script>

<style scoped>
.sidebar {
  width: 200px;
  height: 100vh;
  background-color: #f8f9fa;
  padding: 20px;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  margin: 10px 0;
}

.sidebar a {
  text-decoration: none;
  color: #333;
  font-weight: bold;
}

.sidebar a:hover {
  color: #007bff;
}
</style>
