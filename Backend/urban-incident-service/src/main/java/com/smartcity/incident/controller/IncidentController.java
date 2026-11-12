package com.smartcity.incident.controller;

import com.smartcity.incident.dto.IncidentRequest;
import com.smartcity.incident.dto.IncidentResponse;
import com.smartcity.incident.dto.StatisticsResponse;
import com.smartcity.incident.dto.ValidationRequest;
import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;
import com.smartcity.incident.service.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping
    public List<IncidentResponse> findAll(
            @RequestParam(required = false) IncidentCategory category,
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long reporterId) {
        return incidentService.findAll(category, status, priority, reporterId);
    }

    @GetMapping("/statistics")
    public StatisticsResponse statistics() {
        return incidentService.statistics();
    }

    @GetMapping("/{id}")
    public IncidentResponse findById(@PathVariable Long id) {
        return incidentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<IncidentResponse> create(@Valid @RequestBody IncidentRequest request) {
        IncidentResponse created = incidentService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public IncidentResponse update(@PathVariable Long id, @Valid @RequestBody IncidentRequest request) {
        return incidentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/validate")
    public IncidentResponse validate(@PathVariable Long id) {
        return incidentService.validate(id);
    }

    @PatchMapping("/{id}/reject")
    public IncidentResponse reject(@PathVariable Long id, @RequestBody(required = false) ValidationRequest body) {
        return incidentService.reject(id, body == null ? null : body.reason());
    }

    @PatchMapping("/{id}/request-info")
    public IncidentResponse requestInfo(@PathVariable Long id, @RequestBody(required = false) ValidationRequest body) {
        return incidentService.requestInfo(id, body == null ? null : body.reason());
    }

    @PatchMapping("/{id}/close")
    public IncidentResponse close(@PathVariable Long id) {
        return incidentService.close(id);
    }

    @PatchMapping("/{id}/classify")
    public IncidentResponse classify(@PathVariable Long id) {
        return incidentService.classify(id);
    }

    @PatchMapping("/{id}/prioritize")
    public IncidentResponse prioritize(@PathVariable Long id) {
        return incidentService.prioritize(id);
    }
}
