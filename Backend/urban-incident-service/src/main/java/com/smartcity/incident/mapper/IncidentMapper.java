package com.smartcity.incident.mapper;

import com.smartcity.incident.dto.IncidentRequest;
import com.smartcity.incident.dto.IncidentResponse;
import com.smartcity.incident.dto.InterventionRequest;
import com.smartcity.incident.dto.InterventionResponse;
import com.smartcity.incident.entity.Incident;
import com.smartcity.incident.entity.Intervention;
import com.smartcity.incident.enums.Priority;

public final class IncidentMapper {

    private IncidentMapper() {
    }

    public static Incident toEntity(IncidentRequest request) {
        Incident incident = new Incident();
        updateEntity(incident, request);
        return incident;
    }

    public static void updateEntity(Incident incident, IncidentRequest request) {
        incident.setTitle(request.title());
        incident.setDescription(request.description());
        incident.setPhotoUrl(request.photoUrl());
        incident.setLocation(request.location());
        incident.setNeighborhood(request.neighborhood());
        incident.setCategory(request.category());
        incident.setPriority(request.priority() != null ? request.priority() : Priority.MEDIUM);
        incident.setReporterId(request.reporterId());
    }

    public static IncidentResponse toResponse(Incident incident) {
        int count = incident.getInterventions() == null ? 0 : incident.getInterventions().size();
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getPhotoUrl(),
                incident.getLocation(),
                incident.getNeighborhood(),
                incident.getCategory(),
                incident.getPriority(),
                incident.getStatus(),
                incident.getReporterId(),
                incident.getExtraInfoRequest(),
                incident.getRejectionReason(),
                count,
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                incident.getResolvedAt());
    }

    public static Intervention toEntity(InterventionRequest request, Incident incident) {
        return Intervention.builder()
                .incident(incident)
                .technicianId(request.technicianId())
                .scheduledDate(request.scheduledDate())
                .notes(request.notes())
                .build();
    }

    public static InterventionResponse toResponse(Intervention intervention) {
        return new InterventionResponse(
                intervention.getId(),
                intervention.getIncident().getId(),
                intervention.getIncident().getTitle(),
                intervention.getTechnicianId(),
                intervention.getScheduledDate(),
                intervention.getStatus(),
                intervention.getNotes(),
                intervention.getCreatedAt(),
                intervention.getUpdatedAt());
    }
}
