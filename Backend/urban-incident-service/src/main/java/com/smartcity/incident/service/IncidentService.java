package com.smartcity.incident.service;

import com.smartcity.incident.dto.IncidentRequest;
import com.smartcity.incident.dto.IncidentResponse;
import com.smartcity.incident.dto.StatisticsResponse;
import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;

import java.util.List;

public interface IncidentService {

    List<IncidentResponse> findAll(IncidentCategory category, IncidentStatus status, Priority priority, Long reporterId);

    IncidentResponse findById(Long id);

    IncidentResponse create(IncidentRequest request);

    IncidentResponse update(Long id, IncidentRequest request);

    void delete(Long id);

    IncidentResponse validate(Long id);

    IncidentResponse reject(Long id, String reason);

    IncidentResponse requestInfo(Long id, String reason);

    IncidentResponse close(Long id);

    IncidentResponse classify(Long id);

    IncidentResponse prioritize(Long id);

    StatisticsResponse statistics();
}
