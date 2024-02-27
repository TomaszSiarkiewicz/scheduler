package org.scheduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.Set;

@ToString
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private int minDays;
    @NonNull
    private int maxDays;
    @ManyToMany(fetch = FetchType.EAGER)
    @NonNull
    private Set<Skill> skillSet;

    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_location", joinColumns = @JoinColumn(name = "employee_id"))
    @MapKeyJoinColumn(name = "location_id")
    @Column(name = "location_value")
    private Map<Location, Integer> locations;
}
