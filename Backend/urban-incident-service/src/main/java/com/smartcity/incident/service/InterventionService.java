package com.smartcity.incident.service;

import com.smartcity.incident.dto.InterventionRequest;
import com.smartcity.incident.dto.InterventionResponse;
import com.smartcity.incident.enums.InterventionStatus;

import java.util.List;

public interface InterventionService {

    List<InterventionResponse> findByIncident(Long incidentId);

    InterventionResponse create(Long incidentId, InterventionRequest request);

    InterventionResponse updateStatus(Long interventionId, InterventionStatus status);
}
