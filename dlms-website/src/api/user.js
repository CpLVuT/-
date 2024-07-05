import axios from './axios';

export function login(email, password) {
    return axios.post('/auth/login', { email, password });
}

export function register(user) {
    return axios.post('/auth/register', user);
}

export function getUserProfile() {
    return axios.get('/user/profile');
}

export function updateUserProfile(user) {
    return axios.put('/user/profile', user);
}
