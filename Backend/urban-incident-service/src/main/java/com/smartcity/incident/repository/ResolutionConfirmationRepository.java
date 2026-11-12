package com.smartcity.incident.repository;

import com.smartcity.incident.entity.ResolutionConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolutionConfirmationRepository extends JpaRepository<ResolutionConfirmation, Long> {
}
