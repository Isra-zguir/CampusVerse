package com.smartcity.incident.dto;

import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidentRequest(

        @NotBlank(message = "title is required")
        @Size(max = 150, message = "title must be at most 150 characters")
        String title,

        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,

        @Size(max = 500, message = "photoUrl must be at most 500 characters")
        String photoUrl,

        @NotBlank(message = "location is required")
        @Size(max = 200, message = "location must be at most 200 characters")
        String location,

        @Size(max = 120, message = "neighborhood must be at most 120 characters")
        String neighborhood,

        @NotNull(message = "category is required")
        IncidentCategory category,

        Priority priority,

        @NotNull(message = "reporterId is required")
        Long reporterId
) {
}
