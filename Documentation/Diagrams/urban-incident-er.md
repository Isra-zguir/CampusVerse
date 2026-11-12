# Urban Incident service — data model

```mermaid
erDiagram
    INCIDENT ||--o{ INTERVENTION : "has"
    INCIDENT {
        long id PK
        string title
        string description
        string photoUrl
        string location
        string neighborhood
        enum category
        enum priority
        enum status
        long reporterId
        string extraInfoRequest
        string rejectionReason
        datetime createdAt
        datetime updatedAt
        datetime resolvedAt
    }
    INTERVENTION {
        long id PK
        long incidentId FK
        long technicianId
        date scheduledDate
        enum status
        string notes
        datetime createdAt
        datetime updatedAt
    }
```

**Incident status:** REPORTED → PENDING_INFO / VALIDATED / REJECTED → IN_PROGRESS → RESOLVED → CLOSED

**Intervention status:** PENDING → IN_PROGRESS → COMPLETED (or CANCELLED)
