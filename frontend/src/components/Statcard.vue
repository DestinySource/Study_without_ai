<script setup lang="ts">
interface Props {
  label: string;
  value: string | number;
  subtext: string;
  variant: 'green' | 'blue' | 'orange';
}

defineProps<Props>();
</script>

<template>
  <div :class="['card', variant]" role="status" :aria-labelledby="label + '-id'">
    <div :id="label + '-id'" class="card-label">{{ label }}</div>
    <div class="card-value">{{ value }}</div>
    <div class="card-sub">{{ subtext }}</div>

    <div class="corner-glow" aria-hidden="true"></div>
  </div>
</template>

<style scoped>
.card {
  background: #1a1a1a;
  border: 0.5px solid #2a2a2a;
  border-radius: 12px;
  padding: 20px;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.card::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 2px;
}

/* Varianten & Glows */
.card.green::before, .card.green .corner-glow { background: #00e5a0; }
.card.blue::before, .card.blue .corner-glow { background: #0099ff; }
.card.orange::before, .card.orange .corner-glow { background: #ff6b35; }

.corner-glow {
  position: absolute;
  bottom: -30px;
  right: -30px;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  opacity: 0.15;
  pointer-events: none;
  filter: blur(20px);
}

.card-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: white;
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
  color: white;
}
</style>