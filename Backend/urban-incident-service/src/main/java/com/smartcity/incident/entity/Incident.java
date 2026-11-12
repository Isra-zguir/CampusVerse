package com.smartcity.incident.entity;

import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.IncidentStatus;
import com.smartcity.incident.enums.Priority;
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

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String photoUrl;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(length = 120)
    private String neighborhood;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IncidentCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private IncidentStatus status = IncidentStatus.REPORTED;

    @Column(nullable = false)
    private Long reporterId;

    @Column(length = 500)
    private String extraInfoRequest;

    @Column(length = 500)
    private String rejectionReason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Intervention> interventions = new ArrayList<>();

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
