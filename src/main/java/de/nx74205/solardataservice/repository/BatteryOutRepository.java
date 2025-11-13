package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.BatteryOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatteryOutRepository extends JpaRepository<BatteryOut, LocalDateTime> {

    List<BatteryOut> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    BatteryOut findTopByOrderByTimeDesc();

    List<BatteryOut> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(b.value AS double)) FROM BatteryOut b WHERE b.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);
}

