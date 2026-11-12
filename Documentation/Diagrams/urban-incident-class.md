# Urban Incident & Maintenance — UML class diagram

Entities and associations used by `Backend/urban-incident-service`.

```
Technician 1 --assigns--> 0..* WorkOrder
Technician 1 --performs--> 0..* Intervention
Incident 1 --generates--> 0..* WorkOrder
WorkOrder 1 --has--> 0..* Intervention
Incident 1 --receives--> 0..* ResolutionConfirmation
Incident ----sends notifications----> Notification
```

| Class | Main attributes |
|-------|-----------------|
| Technician | id, fullName, email, phone, specialization, availabilityStatus |
| Incident | id, title, description, category, photoUris, latitude, longitude, submissionDate, status, priority, severity, citizenId |
| WorkOrder | id, title, description, priority, status, plannedDate, startTime, estimatedDuration, incidentId, technicianId |
| Intervention | id, startDate, endDate, workDescription, result, workOrderId, technicianId |
| ResolutionConfirmation | id, isResolved, feedback, confirmationDate, citizenId, incidentId |
| Notification | id, recipientId, recipientType, message, notificationType, isRead, createdAt |
