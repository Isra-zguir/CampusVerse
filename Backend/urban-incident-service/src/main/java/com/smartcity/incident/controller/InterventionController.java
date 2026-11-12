package com.smartcity.incident.controller;

import com.smartcity.incident.dto.InterventionRequest;
import com.smartcity.incident.dto.InterventionResponse;
import com.smartcity.incident.enums.InterventionStatus;
import com.smartcity.incident.service.InterventionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class InterventionController {

    private final InterventionService interventionService;

    @GetMapping("/api/incidents/{incidentId}/interventions")
    public List<InterventionResponse> findByIncident(@PathVariable Long incidentId) {
        return interventionService.findByIncident(incidentId);
    }

    @PostMapping("/api/incidents/{incidentId}/interventions")
    public ResponseEntity<InterventionResponse> create(
            @PathVariable Long incidentId,
            @Valid @RequestBody InterventionRequest request) {
        InterventionResponse created = interventionService.create(incidentId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/interventions/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/api/interventions/{id}/status")
    public InterventionResponse updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, InterventionStatus> body) {
        InterventionStatus status = body.get("status");
        return interventionService.updateStatus(id, status);
    }
}
