<template>
  <div class="authPage">
    <div class="authTabs">
      <button :class="{ active: isLogin }" @click="isLogin = true">Login</button>
      <button :class="{ active: !isLogin }" @click="isLogin = false">Register</button>
    </div>
    <div class="authForm">
      <LoginForm v-if="isLogin" @loginSuccess="handleLoginSuccess"/>
      <RegisterForm v-else @registerSuccess="handleRegisterSuccess"/>
    </div>
  </div>
</template>

<script>
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';

export default {
  name: 'AuthPage',
  components: {
    LoginForm,
    RegisterForm
  },
  data() {
    return {
      isLogin: true,
    };
  },
  methods: {
    handleLoginSuccess(data) {
      // 处理登陆成功后的逻辑，data中包含了email和token
      console.log('Login successful with data:', data);
      // 例如，你可以跳转到其他页面
      this.$router.push('/dashboard');
    },
    handleRegisterSuccess() {
      // 处理注册成功后的逻辑
      console.log('Registration successful!');
    }
  }
};
</script>

<style scoped>
.authPage {
  max-width: 400px;
  margin: 0 auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.authTabs {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.authTabs button {
  padding: 10px 20px;
  background-color: #f0f0f0;
  border: none;
  cursor: pointer;
  border-radius: 4px;
  outline: none;
}

.authTabs button.active {
  background-color: #007BFF;
  color: white;
}

.authForm {
  padding: 20px;
}
</style>
