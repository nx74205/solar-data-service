package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.AcOut;
import de.nx74205.solardataservice.dto.DailySumsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcOutRepository extends JpaRepository<AcOut, LocalDateTime> {

    List<AcOut> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    AcOut findTopByOrderByTimeDesc();

    List<AcOut> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(a.value AS double)) FROM AcOut a WHERE a.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT 'AC_OUT' as entityName, DATE_FORMAT(time, '%Y-%m-%d-%H') as date, SUM(value) as value " +
                   "FROM item0182 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY date", nativeQuery = true)
    List<DailySumsDto> getHourlySumsByDate(String date);
}
