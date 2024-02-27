package org.scheduler.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class ShiftGenerateRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "skill_id")
    Skill skill;

    @NonNull
    @ElementCollection
    @CollectionTable(name = "required_days", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "day_of_week")
    List<DayOfWeek> requiredDays;

    @NonNull
    Integer startTime;

    @NonNull
    Integer endTime;
}

