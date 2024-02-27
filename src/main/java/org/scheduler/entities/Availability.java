package org.scheduler.entities;

import jakarta.persistence.*;
import org.scheduler.enums.AvailabilityType;

import java.time.LocalDate;

@Entity
public class Availability {
//    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDate date;
    private AvailabilityType availabilityType;
}
