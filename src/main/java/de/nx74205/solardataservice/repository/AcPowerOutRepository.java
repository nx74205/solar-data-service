package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.AcPowerOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcPowerOutRepository extends JpaRepository<AcPowerOut, LocalDateTime> {

    List<AcPowerOut> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    AcPowerOut findTopByOrderByTimeDesc();

    List<AcPowerOut> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(a.value AS double)) FROM AcPowerOut a WHERE a.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, SUM(value) as total " +
                   "FROM item0181 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY hour", nativeQuery = true)
    List<Object[]> getHourlySumsByDate(String date);
}

