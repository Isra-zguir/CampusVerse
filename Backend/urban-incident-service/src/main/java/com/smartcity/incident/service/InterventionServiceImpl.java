package com.smartcity.incident.service;

import com.smartcity.incident.dto.InterventionRequest;
import com.smartcity.incident.dto.InterventionResponse;
import com.smartcity.incident.entity.Incident;
import com.smartcity.incident.entity.Intervention;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.InterventionStatus;
import com.smartcity.incident.exception.BusinessRuleException;
import com.smartcity.incident.exception.InvalidRequestException;
import com.smartcity.incident.exception.ResourceNotFoundException;
import com.smartcity.incident.mapper.IncidentMapper;
import com.smartcity.incident.repository.InterventionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterventionServiceImpl implements InterventionService {

    private final InterventionRepository interventionRepository;
    private final IncidentServiceImpl incidentService;

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponse> findByIncident(Long incidentId) {
        incidentService.getIncident(incidentId);
        return interventionRepository.findByIncidentId(incidentId).stream()
                .map(IncidentMapper::toResponse)
                .toList();
    }

    @Override
    public InterventionResponse create(Long incidentId, InterventionRequest request) {
        Incident incident = incidentService.getIncident(incidentId);
        if (incident.getStatus() != IncidentStatus.VALIDATED && incident.getStatus() != IncidentStatus.IN_PROGRESS) {
            throw new BusinessRuleException("A work order can be created only for a validated incident");
        }
        Intervention intervention = IncidentMapper.toEntity(request, incident);
        intervention.setStatus(InterventionStatus.PENDING);
        Intervention saved = interventionRepository.save(intervention);
        incidentService.markInProgress(incident);
        return IncidentMapper.toResponse(saved);
    }

    @Override
    public InterventionResponse updateStatus(Long interventionId, InterventionStatus status) {
        if (status == null) {
            throw new InvalidRequestException("status is required");
        }
        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention " + interventionId + " not found"));
        if (intervention.getStatus() == InterventionStatus.CANCELLED) {
            throw new BusinessRuleException("A cancelled intervention cannot change status");
        }
        intervention.setStatus(status);
        if (status == InterventionStatus.COMPLETED) {
            incidentService.markResolved(intervention.getIncident());
        } else if (status == InterventionStatus.IN_PROGRESS) {
            incidentService.markInProgress(intervention.getIncident());
        }
        return IncidentMapper.toResponse(intervention);
    }
}
