import { createStore } from 'vuex';
import { getUserProfile } from '../api/user';

export default createStore({
    state: {
        user: null,
        isAuthenticated: !!localStorage.getItem('token'),
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
        setAuthenticated(state, isAuthenticated) {
            state.isAuthenticated = isAuthenticated;
        },
    },
    actions: {
        async fetchUser({ commit }) {
            try {
                const user = await getUserProfile();
                commit('setUser', user);
                commit('setAuthenticated', true);
            } catch (error) {
                console.error('Failed to fetch user profile:', error);
                commit('setAuthenticated', false);
            }
        },
        login({ commit }, { email, token }) {
            localStorage.setItem('token', token);
            commit('setAuthenticated', true);
            commit('setUser', { email });
        },
        logout({ commit }) {
            localStorage.removeItem('token');
            commit('setAuthenticated', false);
            commit('setUser', null);
        },
    },
    getters: {
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
        user(state) {
            return state.user;
        },
    },
});
