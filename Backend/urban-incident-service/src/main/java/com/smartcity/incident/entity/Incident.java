package com.smartcity.incident.entity;

import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;
import com.smartcity.incident.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private IncidentCategory category;

    @ElementCollection
    @CollectionTable(name = "incident_photos", joinColumns = @JoinColumn(name = "incident_id"))
    @Column(name = "photo_uri")
    @Builder.Default
    private List<String> photoUris = new ArrayList<>();

    private Double latitude;
    private Double longitude;
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private Long citizenId;

    @OneToMany(mappedBy = "incident")
    @Builder.Default
    private List<WorkOrder> workOrders = new ArrayList<>();

    @OneToMany(mappedBy = "incident")
    @Builder.Default
    private List<ResolutionConfirmation> resolutionConfirmations = new ArrayList<>();
}
