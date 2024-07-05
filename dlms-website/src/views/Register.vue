<template>
  <div class="register">
    <h2>Register</h2>
    <form @submit.prevent="handleRegister">
      <input v-model="username" type="text" placeholder="Username" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
import { register } from '../api/user';

export default {
  name: 'RegisterForm',
  data() {
    return {
      username: '',
      email: '',
      password: '',
    };
  },
  methods: {
    async handleRegister() {
      try {
        const user = {
          username: this.username,
          email: this.email,
          password: this.password,
        };
        await register(user);
        this.$emit('registerSuccess');
        this.$router.push('/login'); // 注册成功后跳转到登录页面
      } catch (error) {
        console.error('Registration failed:', error);
      }
    },
  },
};
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: 0 auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
