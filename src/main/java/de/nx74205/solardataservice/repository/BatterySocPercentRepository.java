package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.BatterySocPercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BatterySocPercentRepository extends JpaRepository<BatterySocPercent, LocalDateTime> {

    BatterySocPercent findTopByOrderByTimeDesc();
}

