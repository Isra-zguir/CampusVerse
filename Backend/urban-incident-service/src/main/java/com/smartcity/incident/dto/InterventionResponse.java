package com.smartcity.incident.dto;

import com.smartcity.incident.enums.InterventionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InterventionResponse(
        Long id,
        Long incidentId,
        String incidentTitle,
        Long technicianId,
        LocalDate scheduledDate,
        InterventionStatus status,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
