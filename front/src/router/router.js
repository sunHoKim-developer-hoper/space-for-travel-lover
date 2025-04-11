// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import * as components from '@/components/component.js';

const routes = [
  { path: '/', component: components.Index },
  { path: '/login', component: components.Login },
  { path: '/test', component: components.Test },
  { path: "/:pathMatch(.*)*", component: components.Page404, name : 'NotFound'},
  // 필요한 다른 경로 추가
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
export {routes};