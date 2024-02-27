package org.scheduler.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class SheduleState {
    @Id
    private Long tenantId;
    private Integer publishLength;
    private Integer draftLength;
    private LocalDate firstDraftDate;
    private LocalDate lastHistoricDate;
}
