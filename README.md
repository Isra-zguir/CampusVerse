# Smart City — Intelligent Urban Management Platform

Smart City is an **intelligent and collaborative digital platform** designed to modernize urban services and improve citizens’ quality of life.

It brings together **incident management**, **public infrastructure**, **inclusive mobility**, and **citizen participation** in a single platform.

Through a **microservices architecture** and smart features, it aims to create a more **connected, sustainable, accessible, and inclusive** city.

**Public repository:** https://github.com/Isra-zguir/CampusVerse.git

---

## Team members

| Member | Role |
|--------|------|
| Sarah Guesmi | Team member |
| Nour Ammar | Team member |
| Eya Hwess | Team member |
| Eya Abdellaoui | Team member |
| Bilel Rebhi | Team member |
| Isra Zguir | Team member |

Task tracking is done with **GitHub Issues / Projects** on this repository, together with regular Git commits per member.

---

## Architecture

Each microservice owns its own database (database-per-service) and exposes a REST API. Frontends consume those APIs.

```
Citizens / Municipal agents
            │
            ▼
   Angular frontends (Frontend/)
            │  REST
            ▼
   Spring Boot microservices (Backend/)
            │
            ▼
   Database per service
```

### Microservices

| # | Microservice | Folder | Tech | Port |
|---|--------------|--------|------|------|
| 1 | Citizen & Civic Participation | `Backend/citizen-participation-service` | Spring Boot | TBD |
| 2 | **Urban Incident & Maintenance Management** | `Backend/urban-incident-service` | Spring Boot · Java 21 · H2 | **8082** |
| 3 | Smart Mobility & Traffic | `Backend/smart-mobility-service` | Spring Boot | TBD |
| 4 | EcoCity & Environmental Monitoring | `Backend/ecocity-service` | Spring Boot | TBD |
| 5 | Digital Municipal Services | `Backend/municipal-services-service` | Spring Boot | TBD |
| 6 | Smart Places & Public Facilities | `Backend/smart-places-service` | Spring Boot | TBD |
| 7 | ReLink AI — Intelligent Lost & Found | `Backend/relink-ai-service` | Spring Boot + AI | TBD |

The existing Events project (`Backend/event-service`, `Frontend/campusverse-web`) remains in the repository and is not modified by this work.

### Urban Incident & Maintenance — features

- Report an incident (description, location, photo URL)
- Categorize incidents (AI-assisted keyword classifier)
- Validate, reject or request additional information
- Prioritize (low / medium / high / critical), with an AI-assisted suggestion
- Create work orders, assign a technician, schedule a date
- Track intervention progress (pending, in progress, completed)
- Confirm resolution and close the incident
- Statistics: counts, average resolution time, completed interventions

---

## Repository structure (imposed)

```
Backend/                              one Spring Boot project per microservice
  urban-incident-service/             Urban Incident backend (this work)
Frontend/
  urban-incidents/                    Urban Incident Angular app (this work)
Documentation/
  Presentation/                       validation slides
  Diagrams/                           architecture, ER / UML diagrams
README.md
```

---

## How to launch — Urban Incident microservice

### Backend (Spring Boot)

Requirements: **Java 21** and Maven (or the included Maven Wrapper).

```bash
cd Backend/urban-incident-service
./mvnw spring-boot:run          # Windows: mvnw.cmd spring-boot:run
```

The API starts on **http://localhost:8082**

| Check | URL |
|-------|-----|
| Health | http://localhost:8082/api/incidents/health |
| List incidents | http://localhost:8082/api/incidents |
| Statistics | http://localhost:8082/api/incidents/statistics |
| H2 console | http://localhost:8082/h2-console (`jdbc:h2:mem:incidents_db`) |

The service uses an **in-memory H2** database so it compiles and **starts without extra setup**.

Example report:

```bash
curl -X POST http://localhost:8082/api/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Broken streetlight\",\"description\":\"Lamp is off at night\",\"location\":\"Avenue Habib Bourguiba\",\"neighborhood\":\"Centre\",\"category\":\"STREETLIGHT\",\"reporterId\":1}"
```

### Frontend (Angular)

Requirements: **Node.js 20.19+**

```bash
cd Frontend/urban-incidents
npm install
npm start
```

Open **http://localhost:4201**. `/api` is proxied to the backend on port 8082.

---

## How to contribute

1. Clone: `git clone https://github.com/Isra-zguir/CampusVerse.git`
2. Create a branch for your microservice (example: `feature/urban-incident-service`)
3. Work only in your folders to avoid conflicts
4. `git add` → `git commit` → `git push`
5. Open a pull request toward `main`
