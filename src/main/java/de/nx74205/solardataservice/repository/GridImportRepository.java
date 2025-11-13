package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.GridImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GridImportRepository extends JpaRepository<GridImport, LocalDateTime> {

    List<GridImport> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    GridImport findTopByOrderByTimeDesc();

    List<GridImport> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(g.value AS double)) FROM GridImport g WHERE g.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, SUM(value) as total " +
                   "FROM item0184 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY hour", nativeQuery = true)
    List<Object[]> getHourlySumsByDate(String date);

    @Query(value = "SELECT DATE(time) as date, SUM(value) as total " +
                   "FROM item0184 " +
                   "WHERE time BETWEEN :start AND :end " +
                   "GROUP BY DATE(time) " +
                   "ORDER BY date", nativeQuery = true)
    List<Object[]> getDailySumsBetween(LocalDateTime start, LocalDateTime end);
}

