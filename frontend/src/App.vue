<script setup lang="ts">
import { onMounted, ref } from 'vue';

const status = ref('Bezoek wordt gelogd...');

onMounted(async () => {
  // Pak de referrer (bijv. google.com). Als er geen is, noemen we het 'Direct'.
  const referrer = document.referrer || 'Direct';

  try {
    const response = await fetch('http://localhost:8080/api/log-visit', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ referrer: referrer }),
    });

    if (response.ok) {
      status.value = 'Bezoek succesvol opgeslagen in de database!';
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
  </div>
</template>

<style scoped>
.container {
  font-family: sans-serif;
  max-width: 600px;
  margin: 50px auto;
  text-align: center;
}
</style>