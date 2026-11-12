package com.smartcity.incident.service;

import com.smartcity.incident.dto.IncidentRequest;
import com.smartcity.incident.dto.IncidentResponse;
import com.smartcity.incident.dto.StatisticsResponse;
import com.smartcity.incident.entity.Incident;
import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.InterventionStatus;
import com.smartcity.incident.enums.Priority;
import com.smartcity.incident.exception.BusinessRuleException;
import com.smartcity.incident.exception.InvalidRequestException;
import com.smartcity.incident.exception.ResourceNotFoundException;
import com.smartcity.incident.mapper.IncidentMapper;
import com.smartcity.incident.repository.IncidentRepository;
import com.smartcity.incident.repository.InterventionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private static final List<IncidentStatus> EDITABLE =
            List.of(IncidentStatus.REPORTED, IncidentStatus.PENDING_INFO);

    private final IncidentRepository incidentRepository;
    private final InterventionRepository interventionRepository;
    private final IncidentClassifier classifier;

    @Override
    @Transactional(readOnly = true)
    public List<IncidentResponse> findAll(IncidentCategory category, IncidentStatus status,
                                          Priority priority, Long reporterId) {
        return incidentRepository.findAll().stream()
                .filter(i -> category == null || i.getCategory() == category)
                .filter(i -> status == null || i.getStatus() == status)
                .filter(i -> priority == null || i.getPriority() == priority)
                .filter(i -> reporterId == null || reporterId.equals(i.getReporterId()))
                .sorted(Comparator.comparing(Incident::getCreatedAt).reversed())
                .map(IncidentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IncidentResponse findById(Long id) {
        return IncidentMapper.toResponse(getIncident(id));
    }

    @Override
    public IncidentResponse create(IncidentRequest request) {
        Incident incident = IncidentMapper.toEntity(request);
        incident.setStatus(IncidentStatus.REPORTED);
        if (request.priority() == null) {
            incident.setPriority(classifier.suggestPriority(incident.getCategory(), request.title(), request.description()));
        }
        return IncidentMapper.toResponse(incidentRepository.save(incident));
    }

    @Override
    public IncidentResponse update(Long id, IncidentRequest request) {
        Incident incident = getIncident(id);
        assertEditable(incident);
        IncidentMapper.updateEntity(incident, request);
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public void delete(Long id) {
        Incident incident = getIncident(id);
        if (incident.getStatus() != IncidentStatus.REPORTED && incident.getStatus() != IncidentStatus.REJECTED) {
            throw new BusinessRuleException("Only reported or rejected incidents can be deleted");
        }
        incidentRepository.delete(incident);
    }

    @Override
    public IncidentResponse validate(Long id) {
        Incident incident = getIncident(id);
        if (incident.getStatus() != IncidentStatus.REPORTED && incident.getStatus() != IncidentStatus.PENDING_INFO) {
            throw new BusinessRuleException("Only reported or pending incidents can be validated");
        }
        incident.setStatus(IncidentStatus.VALIDATED);
        incident.setExtraInfoRequest(null);
        incident.setRejectionReason(null);
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse reject(Long id, String reason) {
        Incident incident = getIncident(id);
        if (incident.getStatus() == IncidentStatus.CLOSED || incident.getStatus() == IncidentStatus.RESOLVED) {
            throw new BusinessRuleException("A resolved or closed incident cannot be rejected");
        }
        if (reason == null || reason.isBlank()) {
            throw new InvalidRequestException("A rejection reason is required");
        }
        incident.setStatus(IncidentStatus.REJECTED);
        incident.setRejectionReason(reason);
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse requestInfo(Long id, String reason) {
        Incident incident = getIncident(id);
        if (incident.getStatus() != IncidentStatus.REPORTED) {
            throw new BusinessRuleException("Additional information can only be requested on reported incidents");
        }
        incident.setStatus(IncidentStatus.PENDING_INFO);
        incident.setExtraInfoRequest(reason);
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse close(Long id) {
        Incident incident = getIncident(id);
        if (incident.getStatus() != IncidentStatus.RESOLVED) {
            throw new BusinessRuleException("An incident can be closed only after the work is resolved");
        }
        incident.setStatus(IncidentStatus.CLOSED);
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse classify(Long id) {
        Incident incident = getIncident(id);
        incident.setCategory(classifier.suggestCategory(incident.getTitle(), incident.getDescription()));
        return IncidentMapper.toResponse(incident);
    }

    @Override
    public IncidentResponse prioritize(Long id) {
        Incident incident = getIncident(id);
        incident.setPriority(classifier.suggestPriority(
                incident.getCategory(), incident.getTitle(), incident.getDescription()));
        return IncidentMapper.toResponse(incident);
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticsResponse statistics() {
        List<Incident> all = incidentRepository.findAll();
        long total = all.size();
        long resolved = all.stream().filter(i ->
                i.getStatus() == IncidentStatus.RESOLVED || i.getStatus() == IncidentStatus.CLOSED).count();
        long open = total - resolved - all.stream().filter(i -> i.getStatus() == IncidentStatus.REJECTED).count();

        Double avgHours = all.stream()
                .filter(i -> i.getResolvedAt() != null)
                .mapToDouble(i -> Duration.between(i.getCreatedAt(), i.getResolvedAt()).toMinutes() / 60.0)
                .average()
                .orElse(Double.NaN);
        if (Double.isNaN(avgHours)) {
            avgHours = null;
        }

        Map<String, Long> byCategory = Arrays.stream(IncidentCategory.values())
                .collect(Collectors.toMap(Enum::name,
                        c -> all.stream().filter(i -> i.getCategory() == c).count(),
                        Long::sum, LinkedHashMap::new));
        Map<String, Long> byPriority = Arrays.stream(Priority.values())
                .collect(Collectors.toMap(Enum::name,
                        p -> all.stream().filter(i -> i.getPriority() == p).count(),
                        Long::sum, LinkedHashMap::new));
        Map<String, Long> byStatus = Arrays.stream(IncidentStatus.values())
                .collect(Collectors.toMap(Enum::name,
                        s -> all.stream().filter(i -> i.getStatus() == s).count(),
                        Long::sum, LinkedHashMap::new));

        return new StatisticsResponse(
                total,
                Math.max(open, 0),
                resolved,
                interventionRepository.countByStatus(InterventionStatus.COMPLETED),
                avgHours,
                byCategory,
                byPriority,
                byStatus);
    }

    public void markInProgress(Incident incident) {
        if (incident.getStatus() == IncidentStatus.VALIDATED) {
            incident.setStatus(IncidentStatus.IN_PROGRESS);
        }
    }

    public void markResolved(Incident incident) {
        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setResolvedAt(LocalDateTime.now());
    }

    Incident getIncident(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident " + id + " not found"));
    }

    private void assertEditable(Incident incident) {
        if (!EDITABLE.contains(incident.getStatus())) {
            throw new BusinessRuleException("Incident " + incident.getId() + " can no longer be edited");
        }
    }
}
