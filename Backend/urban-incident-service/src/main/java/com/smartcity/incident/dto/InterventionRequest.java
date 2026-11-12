package com.smartcity.incident.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InterventionRequest(

        @NotNull(message = "technicianId is required")
        Long technicianId,

        LocalDate scheduledDate,

        @Size(max = 1000, message = "notes must be at most 1000 characters")
        String notes
) {
}
