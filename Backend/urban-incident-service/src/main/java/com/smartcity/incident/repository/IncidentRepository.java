package com.smartcity.incident.repository;

import com.smartcity.incident.entity.Incident;
import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByReporterId(Long reporterId);

    List<Incident> findByCategory(IncidentCategory category);

    List<Incident> findByStatus(IncidentStatus status);

    List<Incident> findByPriority(Priority priority);
}
