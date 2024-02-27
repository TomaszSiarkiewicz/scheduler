package org.scheduler.repositories;

import org.scheduler.entities.ShiftGenerateRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftGenerateRuleRepository extends JpaRepository<ShiftGenerateRule, Long> {
}
