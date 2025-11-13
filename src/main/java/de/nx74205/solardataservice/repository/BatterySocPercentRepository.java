package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.BatterySocPercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatterySocPercentRepository extends JpaRepository<BatterySocPercent, LocalDateTime> {

    List<BatterySocPercent> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    BatterySocPercent findTopByOrderByTimeDesc();

    List<BatterySocPercent> findByTimeAfter(LocalDateTime time);

    @Query("SELECT AVG(CAST(b.value AS double)) FROM BatterySocPercent b WHERE b.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, AVG(value) as average " +
                   "FROM item0199 " +
                   "WHERE DATE(time) = :date " +
                   "GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') " +
                   "ORDER BY hour", nativeQuery = true)
    List<Object[]> getHourlyAveragesByDate(String date);
}

