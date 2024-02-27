package org.scheduler.entities;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class ShiftGenerateRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    Skill skill;

    @ElementCollection
    @CollectionTable(name = "required_days", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "day_of_week")
    List<DayOfWeek> requiredDays;

    Integer startTime;
    Integer endTime;
}

