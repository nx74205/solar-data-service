package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.Item0197;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Item0197Repository extends JpaRepository<Item0197, LocalDateTime> {

    // Find all entries between two dates
    List<Item0197> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find latest entry
    Item0197 findTopByOrderByTimeDesc();

    // Find all entries after a specific time
    List<Item0197> findByTimeAfter(LocalDateTime time);

    // Custom query to get average value in a time range
    @Query("SELECT AVG(CAST(i.value AS double)) FROM Item0197 i WHERE i.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);
}

