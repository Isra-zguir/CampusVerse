package com.smartcity.incident.dto;

import java.util.Map;

public record StatisticsResponse(
        long totalIncidents,
        long openIncidents,
        long resolvedIncidents,
        long completedInterventions,
        Double averageResolutionHours,
        Map<String, Long> byCategory,
        Map<String, Long> byPriority,
        Map<String, Long> byStatus
) {
}
