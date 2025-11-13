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

    @Query(value = "SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, SUM(value) as total " +
                   "FROM item0198 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY hour", nativeQuery = true)
    List<Object[]> getHourlySumsByDate(String date);

    @Query(value = "SELECT DATE(time) as date, SUM(value) as total " +
                   "FROM item0198 " +
                   "WHERE time BETWEEN :start AND :end " +
                   "GROUP BY DATE(time) " +
                   "ORDER BY date", nativeQuery = true)
    List<Object[]> getDailySumsBetween(LocalDateTime start, LocalDateTime end);
}
