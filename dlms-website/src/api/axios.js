import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8888/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if(token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        console.log(config);
        return config;
    },
    (error) => Promise.reject(error)
);

export default instance;
