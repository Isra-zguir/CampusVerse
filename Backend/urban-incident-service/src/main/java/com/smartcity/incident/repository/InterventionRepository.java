package com.smartcity.incident.repository;

import com.smartcity.incident.entity.Intervention;
import com.smartcity.incident.enums.InterventionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    List<Intervention> findByIncidentId(Long incidentId);

    List<Intervention> findByTechnicianId(Long technicianId);

    long countByStatus(InterventionStatus status);
}
