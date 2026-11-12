# Smart City — Intelligent Urban Management Platform

Smart City is an **intelligent and collaborative digital platform** designed to modernize urban services and improve citizens’ quality of life.

It brings together **incident management**, **public infrastructure**, **inclusive mobility**, and **citizen participation** in a single platform.

Through a **microservices architecture** and smart features, it aims to create a more **connected, sustainable, accessible, and inclusive** city.

**Public repository:** https://github.com/Isra-zguir/CampusVerse.git

---

## Team members

| Member | Role |
|--------|------|
| Sarah Guesmi | Urban Incident & Maintenance Management |
| Nour Ammar | Team member |
| Eya Hwess | Team member |
| Eya Abdellaoui | Team member |
| Bilel Rebhi | Team member |
| Isra Zguir | Team member |

Task tracking is done with **GitHub Issues / Projects** on this repository, together with regular Git commits per member.

---

## Architecture

Each microservice owns its own database (database-per-service) and exposes a REST API. Angular frontends consume those APIs.

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

| # | Microservice | Backend folder | Frontend folder | Tech |
|---|--------------|----------------|-----------------|------|
| 1 | Citizen & Civic Participation | `Backend/citizen-participation-service` | `Frontend/citizen-participation` | Spring Boot · Angular |
| 2 | **Urban Incident & Maintenance Management** | `Backend/urban-incident-service` | `Frontend/urban-incidents` | Spring Boot · Java 21 · H2 · Angular · **port 8082** |
| 3 | Smart Mobility & Traffic | `Backend/smart-mobility-service` | `Frontend/smart-mobility` | Spring Boot · Angular |
| 4 | EcoCity & Environmental Monitoring | `Backend/ecocity-service` | `Frontend/ecocity` | Spring Boot · Angular |
| 5 | Digital Municipal Services | `Backend/municipal-services-service` | `Frontend/municipal-services` | Spring Boot · Angular |
| 6 | Smart Places & Public Facilities | `Backend/smart-places-service` | `Frontend/smart-places` | Spring Boot · Angular |
| 7 | ReLink AI — Intelligent Lost & Found | `Backend/relink-ai-service` | `Frontend/relink-ai` | Spring Boot + AI · Angular |

### 1. Citizen & Civic Participation

- Citizen profile management
- Neighborhood information
- Citizen proposals and public consultations
- Voting, comments and feedback
- Participation history and statistics

### 2. Urban Incident & Maintenance Management *(this branch)*

- Report an incident (photos, description, location)
- Incident categorization (AI can assist)
- Validation: accept, reject or request more information
- Prioritization: low / medium / high / critical (AI can assist)
- Work orders, technician assignment and scheduling
- Intervention progress tracking
- Resolution confirmation
- Notifications when the incident status changes
- Statistics: number of incidents, average resolution time, completed interventions

**UML entities:** Technician, Incident, WorkOrder, Intervention, ResolutionConfirmation, Notification.

### 3. Smart Mobility & Traffic

- Traffic monitoring and road closure management
- Parking availability and reservations
- Alternative routes and mobility statistics

### 4. EcoCity & Environmental Monitoring

- Monitoring stations, air quality and noise
- Thresholds, pollution alerts, dashboards and reports

### 5. Digital Municipal Services

- Service catalog, online requests, document upload
- Validation, appointments, notifications and statistics

### 6. Smart Places & Public Facilities

- Facility management, availability and reservations
- Community activities, reviews and occupancy statistics

### 7. ReLink AI — Intelligent Lost & Found

Municipal service that helps citizens recover lost items or missing animals.

- Lost / found item reporting
- AI smart matching (image analysis and semantic similarity)
- Match confidence, notifications, ownership verification
- Secure return management and reunification statistics

---

## Repository structure (required)

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
| H2 console | http://localhost:8082/h2-console (`jdbc:h2:mem:incidents_db`) |

The service uses an **in-memory H2** database so it **compiles and starts without extra setup**.

This branch contains the **project skeleton** (packages, UML entities, empty services/controllers). REST endpoints will be implemented later.

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
2. Create a branch for your microservice (example: `urban_incident_sarah`)
3. Work only in your folders to avoid conflicts
4. `git add` → `git commit` → `git push`
5. Open a pull request toward `main`
