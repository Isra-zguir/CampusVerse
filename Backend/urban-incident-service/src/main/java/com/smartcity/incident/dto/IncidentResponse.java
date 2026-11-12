package com.smartcity.incident.dto;

import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;

import java.time.LocalDateTime;

public record IncidentResponse(
        Long id,
        String title,
        String description,
        String photoUrl,
        String location,
        String neighborhood,
        IncidentCategory category,
        Priority priority,
        IncidentStatus status,
        Long reporterId,
        String extraInfoRequest,
        String rejectionReason,
        int interventionCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime resolvedAt
) {
}
