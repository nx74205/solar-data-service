package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.Item0198;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Item0198Repository extends JpaRepository<Item0198, LocalDateTime> {

    // Find all entries between two dates
    List<Item0198> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find latest entry
    Item0198 findTopByOrderByTimeDesc();

    // Find all entries after a specific time
    List<Item0198> findByTimeAfter(LocalDateTime time);

    // Custom query to get average value in a time range
    @Query("SELECT AVG(CAST(i.value AS double)) FROM Item0198 i WHERE i.time BETWEEN :start AND :end")
    Double getAverageValueBetween(LocalDateTime start, LocalDateTime end);
}
