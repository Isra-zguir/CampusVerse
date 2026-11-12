package com.smartcity.incident.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resolution_confirmations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolutionConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isResolved;
    private String feedback;
    private LocalDateTime confirmationDate;
    private Long citizenId;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;
}
