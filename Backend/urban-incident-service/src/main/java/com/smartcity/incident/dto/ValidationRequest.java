package com.smartcity.incident.dto;

import jakarta.validation.constraints.Size;

public record ValidationRequest(
        @Size(max = 500) String reason
) {
}
