package de.nx74205.solardataservice.service;

import de.nx74205.solardataservice.dto.HourlySummaryDto;
import de.nx74205.solardataservice.dto.DailyDischargeDto;
import de.nx74205.solardataservice.dto.DailyChargeDto;
import de.nx74205.solardataservice.dto.DailyGridExportDto;
import de.nx74205.solardataservice.dto.DailyGridImportDto;
import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcOut;
import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.entity.BatterySocPercent;
import de.nx74205.solardataservice.entity.GridExport;
import de.nx74205.solardataservice.entity.GridImport;
import de.nx74205.solardataservice.repository.BatteryInRepository;
import de.nx74205.solardataservice.repository.BatteryOutRepository;
import de.nx74205.solardataservice.repository.AcOutRepository;
import de.nx74205.solardataservice.repository.AcPowerOutRepository;
import de.nx74205.solardataservice.repository.BatterySocPercentRepository;
import de.nx74205.solardataservice.repository.GridExportRepository;
import de.nx74205.solardataservice.repository.GridImportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolarDataService {

    private final BatteryInRepository batteryInRepository;
    private final BatteryOutRepository batteryOutRepository;
    private final AcOutRepository acOutRepository;
    private final AcPowerOutRepository acPowerOutRepository;
    private final BatterySocPercentRepository batterySocPercentRepository;
    private final GridExportRepository gridExportRepository;
    private final GridImportRepository gridImportRepository;

    /**
     * Get all BatteryIn entries in a time range
     */
    public List<BatteryIn> getBatteryInInRange(LocalDateTime start, LocalDateTime end) {
        return batteryInRepository.findByTimeBetween(start, end);
    }

    /**
     * Get all BatteryOut entries in a time range
     */
    public List<BatteryOut> getBatteryOutInRange(LocalDateTime start, LocalDateTime end) {
        return batteryOutRepository.findByTimeBetween(start, end);
    }

    /**
     * Get the latest value from AcPowerOut
     */
    public AcPowerOut getLatestAcPowerOut() {
        return acPowerOutRepository.findTopByOrderByTimeDesc();
    }

    /**
     * Get all AcPowerOut entries in a time range
     */
    public List<AcPowerOut> getAcPowerOutInRange(LocalDateTime start, LocalDateTime end) {
        return acPowerOutRepository.findByTimeBetween(start, end);
    }

    /**
     * Get the latest value from BatterySocPercent
     */
    public BatterySocPercent getLatestBatterySocPercent() {
        return batterySocPercentRepository.findTopByOrderByTimeDesc();
    }

    /**
     * Get hourly summary for a specific date
     */
    public List<HourlySummaryDto> getHourlySummary(LocalDate date) {
        String dateString = date.toString();

        // Get hourly sums from all three repositories
        List<Object[]> batteryInData = batteryInRepository.getHourlySumsByDate(dateString);
        List<Object[]> batteryOutData = batteryOutRepository.getHourlySumsByDate(dateString);
        List<Object[]> acOutData = acOutRepository.getHourlySumsByDate(dateString);

        // Convert to maps for easier lookup: hour -> sum
        Map<String, Double> batteryInMap = convertToMap(batteryInData);
        Map<String, Double> batteryOutMap = convertToMap(batteryOutData);
        Map<String, Double> acOutMap = convertToMap(acOutData);

        // Get all unique hours
        Set<String> allHours = new TreeSet<>();
        allHours.addAll(batteryInMap.keySet());
        allHours.addAll(batteryOutMap.keySet());
        allHours.addAll(acOutMap.keySet());

        // Build result list
        return allHours.stream()
                .map(hour -> new HourlySummaryDto(
                        hour,
                        acOutMap.getOrDefault(hour, 0.0),
                        batteryOutMap.getOrDefault(hour, 0.0),
                        batteryInMap.getOrDefault(hour, 0.0)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get daily discharge sums for battery out in a time range
     */
    public List<DailyDischargeDto> getDailyDischargeSums(LocalDateTime start, LocalDateTime end) {
        List<Object[]> dailyData = batteryOutRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(row -> {
                    String date = row[0].toString();
                    Double total = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                    return new DailyDischargeDto(date, total);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get daily charge sums for battery in in a time range
     */
    public List<DailyChargeDto> getDailyChargeSums(LocalDateTime start, LocalDateTime end) {
        List<Object[]> dailyData = batteryInRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(row -> {
                    String date = row[0].toString();
                    Double total = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                    return new DailyChargeDto(date, total);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get the latest value from GridExport
     */
    public GridExport getLatestGridExport() {
        return gridExportRepository.findTopByOrderByTimeDesc();
    }

    /**
     * Get all GridExport entries in a time range
     */
    public List<GridExport> getGridExportInRange(LocalDateTime start, LocalDateTime end) {
        return gridExportRepository.findByTimeBetween(start, end);
    }

    /**
     * Get average value for GridExport in a time range
     */
    public Double getAverageGridExport(LocalDateTime start, LocalDateTime end) {
        return gridExportRepository.getAverageValueBetween(start, end);
    }

    /**
     * Get the latest value from GridImport
     */
    public GridImport getLatestGridImport() {
        return gridImportRepository.findTopByOrderByTimeDesc();
    }

    /**
     * Get all GridImport entries in a time range
     */
    public List<GridImport> getGridImportInRange(LocalDateTime start, LocalDateTime end) {
        return gridImportRepository.findByTimeBetween(start, end);
    }

    /**
     * Get average value for GridImport in a time range
     */
    public Double getAverageGridImport(LocalDateTime start, LocalDateTime end) {
        return gridImportRepository.getAverageValueBetween(start, end);
    }

    /**
     * Get daily grid export sums in a time range
     */
    public List<DailyGridExportDto> getDailyGridExportSums(LocalDateTime start, LocalDateTime end) {
        List<Object[]> dailyData = gridExportRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(row -> {
                    String date = row[0].toString();
                    Double total = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                    return new DailyGridExportDto(date, total);
                })
                .collect(Collectors.toList());
    }

    /**
     * Get daily grid import sums in a time range
     */
    public List<DailyGridImportDto> getDailyGridImportSums(LocalDateTime start, LocalDateTime end) {
        List<Object[]> dailyData = gridImportRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(row -> {
                    String date = row[0].toString();
                    Double total = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                    return new DailyGridImportDto(date, total);
                })
                .collect(Collectors.toList());
    }

    private Map<String, Double> convertToMap(List<Object[]> data) {
        Map<String, Double> map = new HashMap<>();
        for (Object[] row : data) {
            String hour = (String) row[0];
            Double sum = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            map.put(hour, sum);
        }
        return map;
    }
}
