package org.scheduler.repositories;

import org.scheduler.entities.SheduleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleStateRepository extends JpaRepository<SheduleState, Long> {
}
