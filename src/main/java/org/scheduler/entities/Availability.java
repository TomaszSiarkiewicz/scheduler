package org.scheduler.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.scheduler.enums.AvailabilityType;

import java.time.LocalDate;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Availability {
//    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @NonNull
    private LocalDate date;
    @NonNull
    private AvailabilityType availabilityType;


}
