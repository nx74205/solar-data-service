package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.AcOut;
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

    @Query(value = "SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, SUM(value) as total " +
                   "FROM item0182 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY hour", nativeQuery = true)
    List<Object[]> getHourlySumsByDate(String date);
}

