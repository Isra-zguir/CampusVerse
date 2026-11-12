# Urban Incidents (Angular)

Frontend for the **Urban Incident & Maintenance Management** microservice.

## Run

Requirements: Node.js 20.19+ / 22.12+ / 24+

```bash
cd Frontend/urban-incidents
npm install
npm start
```

The app starts on http://localhost:4201. Start the backend first (`Backend/urban-incident-service`, port 8082).  
`/api` is proxied to the Spring Boot service (no CORS issue in development).
