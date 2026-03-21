# Frontend stack: commands & uitleg
Een overzicht van de geïnstalleerde libraries in onze Vue 3 project, wat ze doen en wanneer je ze gebruikt.

---

## 1. Tailwind CSS

**Installatie:**
```bash
npm install -D tailwindcss @tailwindcss/vite
```

**Wat is het?**
<br>Een CSS framework waarmee je pagina's stylet via kant-en-klare klassen die je direct in je HTML schrijft, zoals kleur,
spacing en lettergrootte. Je hoeft zelf geen aparte CSS bestanden bij te houden.

**Wanneer gebruik je het?**
<br>Voor alle visuele styling van je project. layouts.
---

## 2. PrimeVue

**Installatie:**
```bash
npm install primevue @primevue/themes
```

**Wat is het?**
<br>Een bibliotheek met kant-en-klare UI componenten voor Vue, zoals datatables, dropdowns, datepickers, modals en knoppen. 
In unstyled mode kun je alles zelf opmaken met Tailwind zodat het bij de kleuren van de pagina past.

**Wanneer gebruik je het?**
<br>Wanneer je UI elementen nodig hebt die je niet zelf wilt bouwen, zoals een tabel met sorteren en filteren, 
of een datumkiezer voor een filterperiode.

---

## 3. Apache ECharts

**Installatie:**
```bash
npm install echarts vue-echarts
```

**Wat is het?**
<br>Een bibliotheek voor datavisualisatie. Hiermee maak je interactieve grafieken zoals lijngrafieken, staafdiagrammen, 
taartdiagrammen en heatmaps. Het heeft meer functionaliteiten dan Chart.js.

**Wanneer gebruik je het?**
<br>Wanneer je analytics data visueel wilt weergeven, bijvoorbeeld bezoekers over tijd of populairste pagina's.

---

## 4. Pinia

**Installatie:**
```bash
npm install pinia
```

**Wat is het?**
De officiële state management library voor Vue. Het is een centrale plek waar je data opslaat die meerdere pagina's 
of componenten tegelijk nodig hebben.

**Wanneer gebruik je het?**
Wanneer data gedeeld moet worden tussen verschillende onderdelen van je app, zoals gefetchte analytics data, een 
geselecteerde datumfilter of inloggegevens van een gebruiker.

---

## 5. Vue Router

**Installatie:**
```bash
npm install vue-router
```

**Wat is het?**
<br>De officiële routing library voor Vue. Het zorgt ervoor dat je tussen pagina's kunt navigeren zonder dat de browser 
herladen wordt.

**Wanneer gebruik je het?**
<br>Als de app meer dan één pagina heeft, zoals een dashboardpagina, een rapportenpagina en een instellingenpagina.
