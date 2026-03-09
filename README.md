# 📊 OpenTrace: Web Analytics Engine
> **A High-Performance Full-Stack Analytics Platform Built Without AI.**

## 🚀 Over het Project
OpenTrace is een web-analytics engine ontworpen om real-time gebruikersgedrag te tracken en te visualiseren. Dit project fungeert als een "Mastery Challenge": we bouwen dit zonder hulp van generatieve AI om diepgaande kennis van Java internals, het Maven ecosysteem, en DevOps automation te verankeren.

---

## 🏗️ De Architectuur
Het systeem bestaat uit drie hoofdcomponenten die samenwerken binnen een gecontaineriseerd netwerk.



| Laag | Technologie            | Verantwoordelijkheid |
| :--- |:-----------------------| :--- |
| **Frontend** | Vue.js + Tailwind CSS  | Dashboard voor data-visualisatie en beheer. |
| **Backend** | Java 21 + Spring Boot  | Ingestie van events, business logic en API endpoints. |
| **Database** | is nog open            |  |
| **Build Tool** | Apache Maven (pom.xml) | Dependency management en build lifecycle. |
| **DevOps** | Docker & Compose       | Omgevingsbeheer en orchestratie. |

---

## 🛠️ De "No-AI" Grondwet
Om de overgang van Junior naar Senior te forceren, houden we ons aan de volgende regels:
1. **Source of Truth:** Alleen officiële documentatie (Oracle Docs, Spring Docs, MDN) is toegestaan.
2. **Terminal Mastery:** Builds (`mvn clean package`), Docker commando's en Git operaties worden uitsluitend via de CLI uitgevoerd.
3. **Internal Understanding:** Je moet de Java Memory Model (Stack vs Heap) en de Maven Lifecycle begrijpen voordat je code commit.
4. **Manual Debugging:** Geen AI-prompts voor stacktraces. We lezen de logs zelf van onder naar boven.

---

## 📋 User Stories & Roadmap

### Fase 1: Data Ingestie (De Basis)
* **US1:** Als website-eigenaar wil ik een REST-endpoint hebben (`POST /api/v1/events`) die JSON-data accepteert (URL, Referrer, User-Agent).
* **US2:** Als systeem wil ik binnenkomende events valideren op de server-zijde met Java Bean Validation.

### Fase 2: Opslag & Analyse
* **US3:** Als systeem wil ik alle gevalideerde events opslaan in PostgreSQL met Hibernate/JPA.
* **US4:** Als analyst wil ik een endpoint hebben die de "Top 5 Pagina's" van de laatste week aggregeert via een JPQL query.

### Fase 3: Dashboard & UI
* **US5:** Als gebruiker wil ik een dashboard zien, gestyled met Tailwind CSS, dat real-time statistieken toont via Vue.js.
* **US6:** Als admin wil ik de status van mijn backend kunnen zien via de Spring Boot Actuator `/health` indicator in de UI.

---