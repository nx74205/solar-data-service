package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.BatteryIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatteryInRepository extends JpaRepository<BatteryIn, LocalDateTime> {

    List<BatteryIn> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    BatteryIn findTopByOrderByTimeDesc();

    List<BatteryIn> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(b.value AS double)) FROM BatteryIn b WHERE b.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);
}

