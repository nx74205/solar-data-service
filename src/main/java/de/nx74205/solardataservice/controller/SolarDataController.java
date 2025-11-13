package de.nx74205.solardataservice.controller;

import de.nx74205.solardataservice.dto.HourlySummaryDto;
import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.service.SolarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solar")
@RequiredArgsConstructor
public class SolarDataController {

    private final SolarDataService solarDataService;

    /**
     * GET /api/solar/battery/in/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle BatteryIn Werte in einem Zeitraum zurück
     */
    @GetMapping("/battery/in/range")
    public ResponseEntity<List<BatteryIn>> getBatteryInInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<BatteryIn> data = solarDataService.getBatteryInInRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/battery/out/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle BatteryOut Werte in einem Zeitraum zurück
     */
    @GetMapping("/battery/out/range")
    public ResponseEntity<List<BatteryOut>> getBatteryOutInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<BatteryOut> data = solarDataService.getBatteryOutInRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/battery/out/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt den Durchschnittswert von BatteryOut in einem Zeitraum zurück
     */
    @GetMapping("/battery/out/average")
    public ResponseEntity<Double> getAverageBatteryOut(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Double average = solarDataService.getAverageBatteryOut(start, end);
        return average != null ? ResponseEntity.ok(average) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/hourly-summary?date=2025-11-01
     * Gibt stündliche Summen für BatteryIn, BatteryOut und AcOut für ein bestimmtes Datum zurück
     *
     * Beispiel-Response:
     * [
     *   {
     *     "timestamp": "2025-11-01-00",
     *     "ac_out": 234.0,
     *     "battery_out": 123.0,
     *     "battery_in": 456.0
     *   }
     * ]
     */
    @GetMapping("/hourly-summary")
    public ResponseEntity<List<HourlySummaryDto>> getHourlySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<HourlySummaryDto> summary = solarDataService.getHourlySummary(date);
        return ResponseEntity.ok(summary);
    }

    /**
     * GET /api/solar/ac-power-out/current
     * Gibt den aktuellsten Wert von AcPowerOut zurück
     */
    @GetMapping("/ac-power-out/current")
    public ResponseEntity<AcPowerOut> getCurrentAcPowerOut() {
        AcPowerOut latest = solarDataService.getLatestAcPowerOut();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/ac-power-out/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle AcPowerOut Werte in einem Zeitraum zurück
     */
    @GetMapping("/ac-power-out/range")
    public ResponseEntity<List<AcPowerOut>> getAcPowerOutInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<AcPowerOut> data = solarDataService.getAcPowerOutInRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/ac-power-out/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt den Durchschnittswert von AcPowerOut in einem Zeitraum zurück
     */
    @GetMapping("/ac-power-out/average")
    public ResponseEntity<Double> getAverageAcPowerOut(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Double average = solarDataService.getAverageAcPowerOut(start, end);
        return average != null ? ResponseEntity.ok(average) : ResponseEntity.notFound().build();
    }
}
