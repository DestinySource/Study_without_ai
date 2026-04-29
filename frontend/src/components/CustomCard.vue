<script setup lang="ts">
import { ref, computed } from 'vue';

const props = defineProps<{
  tabs: { label: string; value: string }[];
  activeTab: string;
  items: any[];
  valueLabel?: string;
}>();

defineEmits(['update:activeTab', 'filter', 'details']);

const isAscending = ref(false);

const sortedItems = computed(() => {
  if (!props.items) return [];
  return [...props.items].sort((a, b) => {
    const valA = a.value ?? 0;
    const valB = b.value ?? 0;
    return isAscending.value ? valA - valB : valB - valA;
  });
});

const toggleSort = () => { isAscending.value = !isAscending.value; };

const getPercentage = (value: number) => {
  const max = Math.max(...props.items.map(i => i.value || 0), 1);
  return (value / max) * 100;
};
</script>

<template>
  <div class="custom-card">
    <div class="card-header">
      <div class="tabs">
        <button
            v-for="tab in tabs"
            :key="tab.value"
            @click="$emit('update:activeTab', tab.value)"
            :class="{ active: activeTab === tab.value }"
        >
          {{ tab.label }}
        </button>
      </div>
      <div class="value-label" @click="toggleSort" style="cursor: pointer; user-select: none;">
        {{ valueLabel }}
        <span class="sort-icon" :style="{ display: 'inline-block', transform: isAscending ? 'rotate(180deg)' : 'rotate(0deg)' }">
          ↓
        </span>
      </div>
    </div>

    <div class="items-container">
      <div v-for="item in sortedItems" :key="item.label" class="item-row">
        <div
            class="progress-bg"
            :style="{ width: getPercentage(item.value) + '%' }"
        ></div>

        <div class="item-left">
          <slot name="icon" :item="item">
            <img v-if="item.icon" :src="item.icon" class="icon" />
            <div v-else class="icon-placeholder"></div>
          </slot>
          <span class="label-text">{{ item.label }}</span>
        </div>

        <div class="item-right">
          <span class="value-text">{{ item.value.toLocaleString() }}</span>
          <button class="filter-btn" @click.stop="$emit('filter', item)">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M3 4h18l-7 9v6l-4 3v-9z" />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <div class="card-footer">
      <button class="details-btn" @click="$emit('details')">
        <span class="expand-icon">⤢</span> DETAILS
      </button>
    </div>
  </div>
</template>



<style scoped>
.custom-card {
  background: #18181b; /* Donkere zinc-950 kleur */
  border: 1px solid #27272a;
  border-radius: 12px;
  padding: 16px;
  width: 100%;
  box-sizing: border-box;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  border-bottom: 1px solid #27272a;
  padding-bottom: 12px;
}

.tabs {
  display: flex;
  gap: 16px;
}

.tabs button {
  background: none;
  border: none;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  padding: 0;
  transition: color 0.2s;
}

.tabs button.active {
  color: #00e5a0;
}

.value-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  text-transform: uppercase;
}

.items-container {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.item-row {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 12px;
  border-radius: 6px;
  overflow: hidden;
  transition: background 0.2s;
}

.item-row:hover {
  background: rgba(255, 255, 255, 0.05);
}

.progress-bg {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background: rgba(59, 130, 246, 0.1); /* Zachte blauwe gloed */
  z-index: 0;
  transition: width 0.8s cubic-bezier(0.4, 0, 0.2, 1);
}

.item-left, .item-right {
  display: flex;
  align-items: center;
  z-index: 1; /* Zorgt dat tekst boven de progress balk staat */
}

.item-left { gap: 12px; }
.item-right { gap: 16px; }

.icon {
  width: 32px;
  height: 32px;
  object-fit: contain;
}

.icon-placeholder {
  width: 18px;
  height: 18px;
  background: #3f3f46;
  border-radius: 40%;
}

.label-text {
  font-size: 14px;
  color: #e4e4e7;
}

.value-text {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 14px;
  color: white;
}

.filter-btn {
  background: none;
  border: none;
  color: #52525b;
  cursor: pointer;
  padding: 4px;
  opacity: 0;
  transition: opacity 0.2s, color 0.2s;
}

.item-row:hover .filter-btn {
  opacity: 1;
}

.filter-btn:hover {
  color: white;
}

.card-footer {
  margin-top: 16px;
  padding-top: 12px;
  border-top: 1px solid #27272a;
  display: flex;
  justify-content: center;
}

.details-btn {
  background: none;
  border: none;
  color: #52525b;
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.1em;
  cursor: pointer;
  transition: color 0.2s;
}

.details-btn:hover {
  color: white;
}
</style>