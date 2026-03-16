<script setup lang="ts">
import { onMounted, ref } from 'vue';

const status = ref('Bezoek wordt gelogd...');

onMounted(async () => {
  // 1. Check eerst de URL parameters (bijv. ?source=youtube)
  const urlParams = new URLSearchParams(window.location.search);
  const sourceParam = urlParams.get('source');

  // 2. Pak de referrer (bijv. google.com).
  const browserReferrer = document.referrer;

  // 3. Logica voor de 'finalReferrer':
  // We geven de voorkeur aan de URL-parameter, anders de browser referrer, anders 'Direct'.
  let finalReferrer = 'Direct';

  if (sourceParam) {
    finalReferrer = `Source: ${sourceParam}`;
  } else if (browserReferrer) {
    finalReferrer = browserReferrer;
  }

  try {
    const response = await fetch('http://localhost:8080/api/log-visit', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        referrer: finalReferrer,
        source: sourceParam || 'Direct' // Stuur altijd iets mee
      }),
    });

    if (response.ok) {
      status.value = `Bezoek gelogd als: ${finalReferrer}`;
    } else {
      status.value = 'Fout bij het opslaan van bezoek.';
    }
  } catch (error) {
    console.error('Netwerkfout:', error);
    status.value = 'Kan geen verbinding maken met de backend.';
  }
});
</script>

<template>
  <div class="container">
    <h1>Web Analytics Tool 🚀</h1>
    <p>Status: <strong>{{ status }}</strong></p>
    <hr />
    <p>Deze pagina stuurt automatisch je herkomst door naar de Spring Boot backend.</p>
    <p class="help-text">Tip: Test met <code>?source=youtube</code> achter je URL!</p>
  </div>
</template>

<style scoped>
.container {
  font-family: sans-serif;
  max-width: 600px;
  margin: 50px auto;
  text-align: center;
}
.status {
  font-size: 1.1rem;
  color: #2c3e50;
}
.help-text {
  font-size: 0.8rem;
  color: #7f8c8d;
  margin-top: 1rem;
}
</style>