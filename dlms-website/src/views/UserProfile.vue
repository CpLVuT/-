<template>
  <div class="user-profile">
    <h2>User Profile</h2>
    <form @submit.prevent="updateProfile">
      <input v-model="user.username" type="text" placeholder="Username" required />
      <input v-model="user.email" type="email" placeholder="Email" required />
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script>
import { getUserProfile, updateUserProfile } from '../api/user';
import { mapState } from 'vuex';

export default {
  name: 'AppUserProfile',
  data() {
    return {
      user: {},
    };
  },
  computed: {
    ...mapState(['user']),
  },
  async mounted() {
    try {
      this.user = await getUserProfile();
    } catch (error) {
      console.error('Failed to fetch user profile:', error);
    }
  },
  methods: {
    async updateProfile() {
      try {
        await updateUserProfile(this.user);
        alert('Profile updated successfully');
      } catch (error) {
        console.error('Update failed:', error);
      }
    },
  },
};
</script>

<style scoped>
.user-profile {
  max-width: 400px;
  margin: 0 auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
