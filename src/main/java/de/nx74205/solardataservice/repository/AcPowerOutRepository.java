package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.dto.DailySumsDto;
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

    @Query(value = "SELECT 'AC_POWER_OUT' as entityName, DATE_FORMAT(time, '%Y-%m-%d-%H') as date, SUM(value) as value " +
                   "FROM item0181 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY date", nativeQuery = true)
    List<DailySumsDto> getHourlySumsByDate(String date);
}
