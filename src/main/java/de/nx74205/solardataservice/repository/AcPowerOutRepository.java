package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.AcPowerOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcPowerOutRepository extends JpaRepository<AcPowerOut, LocalDateTime> {

    List<AcPowerOut> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    AcPowerOut findTopByOrderByTimeDesc();
}
