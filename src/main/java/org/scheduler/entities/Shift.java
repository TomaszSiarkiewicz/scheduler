package org.scheduler.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Shift {
    @Id
//    @PlanningId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill requiredSkill;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    //    @PlanningVariable
    private Employee employee;
}
