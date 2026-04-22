<script setup lang="ts">
import { computed } from 'vue';

interface BarItem {
  label: string;
  count: number;
}

const props = defineProps<{
  title: string;
  items: BarItem[];
  variant: 'green' | 'blue';
}>();

const maxCount = computed(() => {
  return Math.max(...props.items.map(i => i.count), 1);
});
</script>

<template>
  <div class="panel">
    <h2 class="panel-title">{{ title }}</h2>

    <div v-if="items.length === 0" class="empty">Nog geen data beschikbaar</div>

    <div v-else class="chart-list" role="list" :aria-label="`Grafiek voor ${title}`">
      <div v-for="item in items" :key="item.label" class="bar-row" role="listitem">
        <div class="bar-label" :title="item.label">{{ item.label }}</div>

        <div class="bar-track">
          <div
              class="bar-fill"
              :class="variant"
              :style="{ width: (item.count / maxCount * 100) + '%' }"
              role="progressbar"
              :aria-valuenow="item.count"
              :aria-valuemax="maxCount"
              aria-valuemin="0"
          />
        </div>

        <div class="bar-count">{{ item.count }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.panel {
  background: #1a1a1a;
  border: 0.5px solid #2a2a2a;
  border-radius: 12px;
  padding: 20px;
}

.panel-title {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: white;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  margin-bottom: 16px;
}

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
  height: 100%;
  border-radius: 3px;
  transition: width 0.8s ease;
}

.bar-fill.green { background: #00e5a0; }
.bar-fill.blue  { background: #0099ff; }

.bar-count {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: white;
  width: 32px;
  text-align: right;
}

.empty {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: white;
  text-align: center;
  padding: 20px 0;
}
</style>