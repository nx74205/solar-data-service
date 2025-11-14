package de.nx74205.solardataservice.service;

import de.nx74205.solardataservice.dto.HourlySummaryDto;
import de.nx74205.solardataservice.dto.DailyDischargeDto;
import de.nx74205.solardataservice.dto.DailyChargeDto;
import de.nx74205.solardataservice.dto.DailyGridExportDto;
import de.nx74205.solardataservice.dto.DailyGridImportDto;
import de.nx74205.solardataservice.dto.DailySumsDto;
import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.entity.BatterySocPercent;
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
        List<DailySumsDto> batteryInData = batteryInRepository.getHourlySumsByDate(dateString);
        List<DailySumsDto> batteryOutData = batteryOutRepository.getHourlySumsByDate(dateString);
        List<DailySumsDto> acOutData = acOutRepository.getHourlySumsByDate(dateString);

        // Convert to maps for easier lookup: hour -> sum
        Map<String, Double> batteryInMap = convertDtoToMap(batteryInData);
        Map<String, Double> batteryOutMap = convertDtoToMap(batteryOutData);
        Map<String, Double> acOutMap = convertDtoToMap(acOutData);

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
        List<DailySumsDto> dailyData = batteryOutRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(dto -> new DailyDischargeDto(dto.getDate(), dto.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Get daily charge sums for battery in in a time range
     */
    public List<DailyChargeDto> getDailyChargeSums(LocalDateTime start, LocalDateTime end) {
        List<DailySumsDto> dailyData = batteryInRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(dto -> new DailyChargeDto(dto.getDate(), dto.getValue()))
                .collect(Collectors.toList());
    }


    /**
     * Get daily grid export sums in a time range
     */
    public List<DailyGridExportDto> getDailyGridExportSums(LocalDateTime start, LocalDateTime end) {
        List<DailySumsDto> dailyData = gridExportRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(dto -> new DailyGridExportDto(dto.getDate(), dto.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Get daily grid import sums in a time range
     */
    public List<DailyGridImportDto> getDailyGridImportSums(LocalDateTime start, LocalDateTime end) {
        List<DailySumsDto> dailyData = gridImportRepository.getDailySumsBetween(start, end);

        return dailyData.stream()
                .map(dto -> new DailyGridImportDto(dto.getDate(), dto.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, Double> convertDtoToMap(List<DailySumsDto> data) {
        Map<String, Double> map = new HashMap<>();
        for (DailySumsDto dto : data) {
            String hour = dto.getDate();
            Double sum = dto.getValue() != null ? dto.getValue() : 0.0;
            map.put(hour, sum);
        }
        return map;
    }
}

