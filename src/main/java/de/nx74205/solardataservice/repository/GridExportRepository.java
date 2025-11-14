package de.nx74205.solardataservice.repository;

import de.nx74205.solardataservice.entity.GridExport;
import de.nx74205.solardataservice.dto.DailySumsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GridExportRepository extends JpaRepository<GridExport, LocalDateTime> {


    @Query(value = "SELECT 'GRID_EXPORT' as entityName, DATE_FORMAT(DATE(time), '%Y-%m-%d') as date, SUM(value) as value " +
                   "FROM item0183 " +
                   "WHERE time BETWEEN :start AND :end " +
                   "GROUP BY DATE(time) " +
                   "ORDER BY date", nativeQuery = true)
    List<DailySumsDto> getDailySumsBetween(LocalDateTime start, LocalDateTime end);
}
