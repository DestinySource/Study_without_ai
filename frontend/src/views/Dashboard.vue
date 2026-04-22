<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Statcard from '@/components/Statcard.vue';
import Barchartpanel from '@/components/Barchartpanel.vue';

interface SiteStats {
  totalVisits: number;
  totalBounces: number;
  bounceRatePct: number;
}

interface BarItem {
  label: string;
  count: number;
}

const stats = ref<SiteStats | null>(null);
const pages = ref<BarItem[]>([]);
const sources = ref<BarItem[]>([]);
const error = ref('');
const loading = ref(true);

async function loadStats() {
  loading.value = true;
  error.value = '';
  try {
    const [statsRes, pagesRes, sourcesRes] = await Promise.all([
      fetch('https://localhost:8443/api/analytics'),
      fetch('https://localhost:8443/api/analytics/pages'),
      fetch('https://localhost:8443/api/analytics/sources'),
    ]);

    if (!statsRes.ok || !pagesRes.ok || !sourcesRes.ok) {
      throw new Error('Server response was not ok');
    }

    stats.value = await statsRes.json();
    pages.value = await pagesRes.json();
    sources.value = await sourcesRes.json();
  } catch (e) {
    error.value = 'Backend niet bereikbaar — start je Spring Boot server.';
    console.error(e);
  } finally {
    loading.value = false;
  }
}

onMounted(() => loadStats());
</script>

<template>
  <main class="dash">
    <header class="topbar">
      <h1 class="logo">ANALYTICS // DASHBOARD</h1>
      <div class="topbar-right">
        <div class="live-dot" aria-live="polite">
          <span class="dot" />
          live
        </div>
        <button
            class="refresh-btn"
            @click="loadStats"
            :disabled="loading"
            aria-label="Ververs statistieken"
        >
          {{ loading ? '...' : '↻ refresh' }}
        </button>
      </div>
    </header>

    <div v-if="error" class="error-msg" role="alert">
      ⚠ {{ error }}
    </div>

    <div v-if="loading && !stats" class="empty">
      Data ophalen uit de database...
    </div>

    <template v-else-if="stats">

      <section class="cards" aria-label="Kernstatistieken">
        <Statcard
            label="Total Visits"
            :value="stats.totalVisits"
            subtext="all time"
            variant="green"
        />
        <Statcard
            label="Bounce Rate"
            :value="stats.bounceRatePct.toFixed(1) + '%'"
            :subtext="stats.bounceRatePct > 50 ? 'needs attention' : 'looking good'"
            variant="orange"
        />
        <Statcard
            label="Total Bounces"
            :value="stats.totalBounces"
            subtext="of total visits"
            variant="blue"
        />
      </section>

      <section class="bottom-row">
        <Barchartpanel
            title="Top Pages"
            :items="pages"
            variant="green"
        />
        <Barchartpanel
            title="Traffic Sources"
            :items="sources"
            variant="blue"
        />
      </section>

    </template>

  </main>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@400;500&family=Syne:wght@400;500;700&display=swap');

.dash {
  font-family: 'Syne', sans-serif;
  background: #0f0f0f;
  min-height: 100vh;
  padding: 32px;
  color: #f0f0f0;
}

.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 28px;
}

.logo {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: #00e5a0;
  letter-spacing: 0.08em;
  margin: 0;
}

.topbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.live-dot {
  display: flex;
  align-items: center;
  gap: 6px;
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: white; /* WCAG compliant */
}

.dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #00e5a0;
  animation: blink 2s infinite;
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50%       { opacity: 0.2; }
}

.refresh-btn {
  background: transparent;
  border: 0.5px solid #2a2a2a;
  color: WHITE;
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
  letter-spacing: 0.05em;
  transition: all 0.15s;
}

.refresh-btn:hover:not(:disabled) {
  border-color: #00e5a0;
  color: #00e5a0;
}

.refresh-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.cards {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
  margin-bottom: 20px;
}

.bottom-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.empty {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: WHITE;
  text-align: center;
  padding: 40px 0;
}

.error-msg {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #ff6b35;
  margin-bottom: 16px;
  background: rgba(255, 107, 53, 0.1);
  padding: 12px;
  border-radius: 8px;
  border: 1px solid rgba(255, 107, 53, 0.2);
}

/* Mobiele optimalisatie */
@media (max-width: 768px) {
  .cards, .bottom-row {
    grid-template-columns: 1fr;
  }
}
</style>