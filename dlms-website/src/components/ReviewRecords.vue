<template>
  <div>
    <h1>审核记录</h1>
    <button @click="fetchPendingPapers">获取待审核论文</button>
    <button @click="fetchPendingPatents">获取待审核专利</button>
    <button @click="fetchPendingHorizontalProjects">获取待审核横向项目</button>
    <button @click="fetchPendingVerticalProjects">获取待审核纵向项目</button>
    <!-- Show Pending Papers -->
    <table v-if="pendingPapers.length">
      <thead>
      <tr>
        <th>ID</th>
        <th>文件名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in pendingPapers" :key="record.id">
        <td>{{ record.id }}</td>
        <td>{{ record.documentName }}</td>
        <td>
          <button @click="updatePaperReviewStatus(record.id, 'APPROVED')">批准</button>
          <button @click="updatePaperReviewStatus(record.id, 'REJECTED')">拒绝</button>
        </td>
      </tr>
      </tbody>
    </table>
    <!-- Show Pending Patents -->
    <table v-if="pendingPatents.length">
      <thead>
      <tr>
        <th>ID</th>
        <th>文件名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in pendingPatents" :key="record.id">
        <td>{{ record.id }}</td>
        <td>{{ record.documentName }}</td>
        <td>
          <button @click="updatePatentReviewStatus(record.id, 'APPROVED')">批准</button>
          <button @click="updatePatentReviewStatus(record.id, 'REJECTED')">拒绝</button>
        </td>
      </tr>
      </tbody>
    </table>
    <!-- Show Pending Horizontal Projects -->
    <table v-if="pendingHorizontalProjects.length">
      <thead>
      <tr>
        <th>ID</th>
        <th>文件名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in pendingHorizontalProjects" :key="record.id">
        <td>{{ record.id }}</td>
        <td>{{ record.documentName }}</td>
        <td>
          <button @click="updateHorizontalProjectReviewStatus(record.id, 'APPROVED')">批准</button>
          <button @click="updateHorizontalProjectReviewStatus(record.id, 'REJECTED')">拒绝</button>
        </td>
      </tr>
      </tbody>
    </table>
    <!-- Show Pending Vertical Projects -->
    <table v-if="pendingVerticalProjects.length">
      <thead>
      <tr>
        <th>ID</th>
        <th>文件名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in pendingVerticalProjects" :key="record.id">
        <td>{{ record.id }}</td>
        <td>{{ record.documentName }}</td>
        <td>
          <button @click="updateVerticalProjectReviewStatus(record.id, 'APPROVED')">批准</button>
          <button @click="updateVerticalProjectReviewStatus(record.id, 'REJECTED')">拒绝</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
// 在你的Vue组件中包含以下逻辑
import axios from '../api/axios';  // 请根据需要导入axios

export default {
  data() {
    return {
      pendingPapers: [],
      pendingPatents: [],
      pendingHorizontalProjects: [],
      pendingVerticalProjects: [],
    };
  },
  methods: {
    async fetchPendingPapers() {
      try {
        const response = await axios.get('/review/papers');
        this.pendingPapers = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async fetchPendingPatents() {
      try {
        const response = await axios.get('/review/patents');
        this.pendingPatents = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async fetchPendingHorizontalProjects() {
      try {
        const response = await axios.get('/review/horizontal-projects');
        this.pendingHorizontalProjects = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async fetchPendingVerticalProjects() {
      try {
        const response = await axios.get('/review/vertical-projects');
        this.pendingVerticalProjects = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async updatePaperReviewStatus(id, status) {
      try {
        await axios.post(`/review/papers/${id}`, { 'Review Status': status  });
        // 更新成功后的处理逻辑
      } catch (error) {
        console.error(error);
      }
    },
    async updatePatentReviewStatus(id, status) {
      try {
        await axios.post(`/review/patents/${id}`, { 'Review Status': status  });
        // 更新成功后的处理逻辑
      } catch (error) {
        console.error(error);
      }
    },
    async updateHorizontalProjectReviewStatus(id, status) {
      try {
        await axios.post(`/review/horizontal-projects/${id}`, { 'Review Status': status });
        // 更新成功后的处理逻辑
      } catch (error) {
        console.error(error);
      }
    },
    async updateVerticalProjectReviewStatus(id, status) {
      try {
        await axios.post(`/review/vertical-projects/${id}`, { 'Review Status': status  });
        // 更新成功后的处理逻辑
      } catch (error) {
        console.error(error);
      }
    },
  },
  mounted() {
    this.fetchPendingPapers();
    this.fetchPendingPatents();
    this.fetchPendingHorizontalProjects();
    this.fetchPendingVerticalProjects();
  }
};
</script>