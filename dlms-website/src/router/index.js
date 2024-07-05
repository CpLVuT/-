import { createRouter, createWebHistory } from 'vue-router';
import AuthPage from '../views/AuthPage.vue';
import AppDashboard from '../views/Dashboard.vue';
import VerticalProjects from '../components/VerticalProjects.vue';
import HorizontalProjects from '../components/HorizontalProjects.vue';
import Papers from '../components/Papers.vue';
import Patents from '../components/Patents.vue';
import ReviewRecords from '../components/ReviewRecords.vue';
import Users from '../components/Users.vue'; // 需要创建这个组件

const routes = [
    { path: '/auth', component: AuthPage },
    {
        path: '/dashboard',
        component: AppDashboard,
        children: [
            { path: 'vertical-projects', component: VerticalProjects },
            { path: 'horizontal-projects', component: HorizontalProjects },
            { path: 'papers', component: Papers },
            { path: 'patents', component: Patents },
            { path: 'review-records', component: ReviewRecords   },
            { path: 'users', component: Users },
        ]
    },
    { path: '/', redirect: '/auth' },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
