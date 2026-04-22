import { createRouter, createWebHistory } from 'vue-router';
import AnalyticsTracker from '@/components/AnalyticsTracker.vue';
import Dashboard from '@/views/Dashboard.vue';

export default createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/',          component: AnalyticsTracker },
        { path: '/dashboard', component: Dashboard },
    ],
});