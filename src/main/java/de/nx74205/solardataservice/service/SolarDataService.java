package de.nx74205.solardataservice.service;

import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcOut;
import de.nx74205.solardataservice.repository.BatteryInRepository;
import de.nx74205.solardataservice.repository.BatteryOutRepository;
import de.nx74205.solardataservice.repository.AcOutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolarDataService {

    private final BatteryInRepository batteryInRepository;
    private final BatteryOutRepository batteryOutRepository;
    private final AcOutRepository acOutRepository;

    /**
     * Get the latest value from BatteryIn
     */
    public BatteryIn getLatestBatteryIn() {
        return batteryInRepository.findTopByOrderByTimeDesc();
    }

    /**
     * Get the latest value from BatteryOut
     */
    public BatteryOut getLatestBatteryOut() {
        return batteryOutRepository.findTopByOrderByTimeDesc();
    }

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
     * Get average value for BatteryIn in a time range
     */
    public Double getAverageBatteryIn(LocalDateTime start, LocalDateTime end) {
        return batteryInRepository.getAverageValueBetween(start, end);
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
}
