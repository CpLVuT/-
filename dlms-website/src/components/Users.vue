<template>
  <div>
    <h1>用户管理</h1>
    <button @click="showCreateForm = true">添加用户</button>
    <div v-if="showCreateForm">
      <h2>添加用户</h2>
      <form @submit.prevent="createUser">
        <input v-model="newUser.username" placeholder="用户名" required />
        <input v-model="newUser.password" type="password" placeholder="密码" required />
        <input v-model="newUser.email" placeholder="邮箱" required />
        <select v-model="newUser.role" required>
          <option value="STUDENT">学生</option>
          <option value="TEACHER">教师</option>
          <option value="ADMIN">管理员</option>
        </select>
        <button type="submit">提交</button>
        <button @click="showCreateForm = false">取消</button>
      </form>
    </div>

    <ul>
      <li v-for="user in users" :key="user.id">
        {{ user.username }} - {{ user.email }} - {{ user.role }}
        <button @click="editUser(user)">编辑</button>
        <button @click="deleteUser(user.id)">删除</button>
      </li>
    </ul>

    <div v-if="showEditForm">
      <h2>编辑用户</h2>
      <form @submit.prevent="updateUser">
        <input v-model="currentUser.username" placeholder="用户名" required />
        <input v-model="currentUser.password" type="password" placeholder="密码" />
        <input v-model="currentUser.email" placeholder="邮箱" required />
        <select v-model="currentUser.role" required disabled>
          <option value="STUDENT">学生</option>
          <option value="TEACHER">教师</option>
          <option value="ADMIN">管理员</option>
        </select>
        <button type="submit">提交</button>
        <button @click="showEditForm = false">取消</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from '../api/axios';

export default {
  name: 'UserList',
  data() {
    return {
      users: [],
      showCreateForm: false,
      showEditForm: false,
      newUser: {
        username: '',
        password: '',
        email: '',
        role: 'STUDENT'
      },
      currentUser: {}
    };
  },
  methods: {
    async fetchUsers() {
      const response = await axios.get('/users');
      this.users = response.data;
    },
    async createUser() {
      await axios.post('/users', this.newUser);
      await this.fetchUsers();
      this.showCreateForm = false;
    },
    editUser(user) {
      this.currentUser = { ...user, password: '' };
      this.showEditForm = true;
    },
    async updateUser() {
      await axios.put(`/users/${this.currentUser.id}`, this.currentUser);
      await this.fetchUsers();
      this.showEditForm = false;
    },
    async deleteUser(id) {
      await axios.delete(`/users/${id}`);
      await this.fetchUsers();
    }
  },
  mounted() {
    this.fetchUsers();
  }
};
</script>
