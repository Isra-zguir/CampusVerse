package com.smartcity.incident.entity;

import com.smartcity.incident.enums.AvailabilityStatus;
import com.smartcity.incident.enums.TechnicianSpecialty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "technicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private TechnicianSpecialty specialization;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @OneToMany(mappedBy = "technician")
    @Builder.Default
    private List<WorkOrder> workOrders = new ArrayList<>();

    @OneToMany(mappedBy = "technician")
    @Builder.Default
    private List<Intervention> interventions = new ArrayList<>();
}
