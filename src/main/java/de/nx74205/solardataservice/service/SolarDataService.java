package de.nx74205.solardataservice.service;

import de.nx74205.solardataservice.dto.HourlySummaryDto;
import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcOut;
import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.repository.BatteryInRepository;
import de.nx74205.solardataservice.repository.BatteryOutRepository;
import de.nx74205.solardataservice.repository.AcOutRepository;
import de.nx74205.solardataservice.repository.AcPowerOutRepository;
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

    /**
     * Get the latest value from AcOut (item0182)
     */
    public AcOut getLatestAcOut() {
        return acOutRepository.findTopByOrderByTimeDesc();
    }

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
     * Get all AcOut entries in a time range
     */
    public List<AcOut> getAcOutInRange(LocalDateTime start, LocalDateTime end) {
        return acOutRepository.findByTimeBetween(start, end);
    }

    /**
     * Get average value for BatteryOut in a time range
     */
    public Double getAverageBatteryOut(LocalDateTime start, LocalDateTime end) {
        return batteryOutRepository.getAverageValueBetween(start, end);
    }

    /**
     * Get average value for AcOut in a time range
     */
    public Double getAverageAcOut(LocalDateTime start, LocalDateTime end) {
        return acOutRepository.getAverageValueBetween(start, end);
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
     * Get average value for AcPowerOut in a time range
     */
    public Double getAverageAcPowerOut(LocalDateTime start, LocalDateTime end) {
        return acPowerOutRepository.getAverageValueBetween(start, end);
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
