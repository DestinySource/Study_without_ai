<script setup lang="ts">
import { onMounted, ref } from 'vue';

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

    stats.value  = await statsRes.json();
    pages.value  = await pagesRes.json();
    sources.value = await sourcesRes.json();
  } catch (e) {
    error.value = 'Backend niet bereikbaar — start je Spring Boot server.';
  } finally {
    loading.value = false;
  }
}

function maxCount(items: BarItem[]) {
  return Math.max(...items.map(i => i.count), 1);
}

onMounted(() => loadStats());
</script>

<template>
  <div class="dash">

    <!-- Topbar -->
    <div class="topbar">
      <span class="logo">ANALYTICS // DASHBOARD</span>
      <div class="topbar-right">
        <div class="live-dot">
          <span class="dot" />
          live
        </div>
        <button class="refresh-btn" @click="loadStats">↻ refresh</button>
      </div>
    </div>

    <!-- Error -->
    <div v-if="error" class="error-msg">⚠ {{ error }}</div>

    <!-- Loading -->
    <div v-if="loading" class="empty">Laden...</div>

    <template v-else-if="stats">

      <!-- Stat Cards -->
      <div class="cards">
        <div class="card green">
          <div class="card-label">Total Visits</div>
          <div class="card-value">{{ stats.totalVisits }}</div>
          <div class="card-sub">all time</div>
        </div>
        <div class="card orange">
          <div class="card-label">Bounce Rate</div>
          <div class="card-value">{{ stats.bounceRatePct.toFixed(1) }}%</div>
          <div class="card-sub">{{ stats.bounceRatePct > 50 ? 'needs attention' : 'looking good' }}</div>
        </div>
        <div class="card blue">
          <div class="card-label">Total Bounces</div>
          <div class="card-value">{{ stats.totalBounces }}</div>
          <div class="card-sub">of total visits</div>
        </div>
      </div>

      <!-- Bar Charts -->
      <div class="bottom-row">

        <!-- Top Pages -->
        <div class="panel">
          <div class="panel-title">Top Pages</div>
          <div v-if="pages.length === 0" class="empty">Nog geen data</div>
          <div v-for="item in pages" :key="item.label" class="bar-row">
            <div class="bar-label" :title="item.label">{{ item.label }}</div>
            <div class="bar-track">
              <div
                  class="bar-fill green"
                  :style="{ width: (item.count / maxCount(pages) * 100) + '%' }"
              />
            </div>
            <div class="bar-count">{{ item.count }}</div>
          </div>
        </div>

        <!-- Traffic Sources -->
        <div class="panel">
          <div class="panel-title">Traffic Sources</div>
          <div v-if="sources.length === 0" class="empty">Nog geen data</div>
          <div v-for="item in sources" :key="item.label" class="bar-row">
            <div class="bar-label" :title="item.label">{{ item.label }}</div>
            <div class="bar-track">
              <div
                  class="bar-fill blue"
                  :style="{ width: (item.count / maxCount(sources) * 100) + '%' }"
              />
            </div>
            <div class="bar-count">{{ item.count }}</div>
          </div>
        </div>

      </div>
    </template>

  </div>
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
  color: #666;
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
  color: #666;
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
  letter-spacing: 0.05em;
  transition: all 0.15s;
}

.refresh-btn:hover {
  border-color: #00e5a0;
  color: #00e5a0;
}

/* Stat Cards */
.cards {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
  margin-bottom: 20px;
}

.card {
  background: #1a1a1a;
  border: 0.5px solid #2a2a2a;
  border-radius: 12px;
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.card::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 2px;
}
.card.green::before  { background: #00e5a0; }
.card.blue::before   { background: #0099ff; }
.card.orange::before { background: #ff6b35; }

.card-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: #666;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  margin-bottom: 10px;
}

.card-value {
  font-size: 32px;
  font-weight: 700;
  line-height: 1;
  margin-bottom: 6px;
}

.card.green .card-value  { color: #00e5a0; }
.card.blue .card-value   { color: #0099ff; }
.card.orange .card-value { color: #ff6b35; }

.card-sub {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #666;
}

/* Bottom Row */
.bottom-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.panel {
  background: #1a1a1a;
  border: 0.5px solid #2a2a2a;
  border-radius: 12px;
  padding: 20px;
}

.panel-title {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: #666;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  margin-bottom: 16px;
}

/* Bar Chart */
.bar-row {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
}

.bar-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #f0f0f0;
  width: 80px;
  flex-shrink: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bar-track {
  flex: 1;
  background: #2a2a2a;
  border-radius: 3px;
  height: 6px;
  overflow: hidden;
}

.bar-fill {
  height: 6px;
  border-radius: 3px;
  transition: width 0.8s ease;
}

.bar-fill.green { background: #00e5a0; }
.bar-fill.blue  { background: #0099ff; }

.bar-count {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #666;
  width: 28px;
  text-align: right;
}

.empty {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: #666;
  text-align: center;
  padding: 20px 0;
}

.error-msg {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #ff6b35;
  margin-bottom: 16px;
}
</style>