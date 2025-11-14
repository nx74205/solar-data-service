package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.dto.DailySumsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatteryOutRepository extends JpaRepository<BatteryOut, LocalDateTime> {

    List<BatteryOut> findByTimeBetween(LocalDateTime start, LocalDateTime end);


    @Query(value = "SELECT 'BATTERY_OUT' as entityName, DATE_FORMAT(time, '%Y-%m-%d-%H') as date, SUM(value) as value " +
                   "FROM item0198 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY date", nativeQuery = true)
    List<DailySumsDto> getHourlySumsByDate(String date);

    @Query(value = "SELECT 'BATTERY_OUT' as entityName, DATE_FORMAT(DATE(time), '%Y-%m-%d') as date, SUM(value) as value " +
                   "FROM item0198 " +
                   "WHERE time BETWEEN :start AND :end " +
                   "GROUP BY DATE(time) " +
                   "ORDER BY date", nativeQuery = true)
    List<DailySumsDto> getDailySumsBetween(LocalDateTime start, LocalDateTime end);
}
