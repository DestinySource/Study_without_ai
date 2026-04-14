<script setup lang="ts">
import { ref } from 'vue';
import { Analytics } from '../composables/Analytics';

const { sendManualVisit } = Analytics();
const status = ref(`Pagina "${window.location.pathname}" wordt bijgehouden`);
const testResult = ref('');

async function handleTest(forceBounce: boolean) {
  testResult.value = 'Versturen...';
  try {
    const success = await sendManualVisit(forceBounce);
    testResult.value = success
        ? (forceBounce ? '✅ Bounce opgeslagen!' : '✅ Bezoek opgeslagen!')
        : '❌ Fout bij opslaan.';
  } catch {
    testResult.value = '❌ Backend niet bereikbaar.';
  }
}
</script>

<template>
  <div class="page">
    <div class="container">
      <div class="tag">TRACKER</div>
      <h1>Web Analytics</h1>
      <p class="status-line">Status: <strong>{{ status }}</strong></p>

      <div class="test-block">
        <p class="block-title">HANDMATIG TESTEN</p>
        <div class="btn-row">
          <button class="btn-visit" @click="handleTest(false)">Stuur bezoek →</button>
          <button class="btn-bounce" @click="handleTest(true)">Stuur bounce →</button>
        </div>
        <p v-if="testResult" class="result">{{ testResult }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@400;500&family=Syne:wght@400;500;700&display=swap');

.page {
  min-height: calc(100vh - 53px);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
}

.container {
  max-width: 480px;
  width: 100%;
  text-align: center;
}

.tag {
  display: inline-block;
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: #00e5a0;
  letter-spacing: 0.15em;
  border: 0.5px solid #00e5a0;
  padding: 4px 12px;
  border-radius: 4px;
  margin-bottom: 20px;
}

h1 {
  font-size: 36px;
  font-weight: 700;
  color: #f0f0f0;
  margin-bottom: 12px;
}

.status-line {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: #666;
  margin-bottom: 32px;
}

.status-line strong { color: #f0f0f0; }

.test-block {
  background: #1a1a1a;
  border: 0.5px solid #2a2a2a;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  text-align: left;
}

.block-title {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  color: #666;
  letter-spacing: 0.1em;
  margin-bottom: 8px;
}

.block-desc {
  font-size: 14px;
  color: #999;
  margin-bottom: 16px;
}

.btn-row {
  display: flex;
  gap: 10px;
}

button {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  letter-spacing: 0.05em;
  transition: all 0.15s;
  background: transparent;
}

.btn-visit {
  border: 0.5px solid #00e5a0;
  color: #00e5a0;
}

.btn-visit:hover {
  background: #00e5a0;
  color: #0f0f0f;
}

.btn-bounce {
  border: 0.5px solid #ff6b35;
  color: #ff6b35;
}

.btn-bounce:hover {
  background: #ff6b35;
  color: #0f0f0f;
}

.result {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 13px;
  color: #f0f0f0;
  margin-top: 14px;
}

.hint {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #444;
}

code { color: #0099ff; }
</style>