<script setup lang="ts">
import { onMounted } from 'vue';
import { RouterView, RouterLink } from 'vue-router';
import { Analytics } from './composables/Analytics';
const { initTracker } = Analytics();

onMounted(() => {
  initTracker(); // Start de globale tracker
});
</script>

<template>
  <nav class="navbar">
    <span class="nav-logo">ANALYTICS</span>
    <div class="nav-links">
      <RouterLink to="/">Tracker</RouterLink>
      <RouterLink to="/dashboard">Dashboard</RouterLink>
    </div>
  </nav>

  <RouterView />
</template>

<style>
* { box-sizing: border-box; margin: 0; padding: 0; }

body {
  background: #0f0f0f;
  color: #f0f0f0;
  font-family: 'Syne', sans-serif;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 32px;
  border-bottom: 0.5px solid #2a2a2a;
}

.nav-logo {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: #00e5a0;
  letter-spacing: 0.08em;
}

.nav-links {
  display: flex;
  gap: 24px;
}

.nav-links a {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #666;
  text-decoration: none;
  letter-spacing: 0.05em;
  transition: color 0.15s;
}

.nav-links a:hover,
.nav-links a.router-link-active {
  color: #00e5a0;
}
</style>