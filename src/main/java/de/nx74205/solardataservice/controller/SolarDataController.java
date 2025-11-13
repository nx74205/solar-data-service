package de.nx74205.solardataservice.controller;

import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.service.SolarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solar")
@RequiredArgsConstructor
public class SolarDataController {

    private final SolarDataService solarDataService;

    /**
     * GET /api/solar/battery/in/latest
     * Gibt den neuesten Wert von BatteryIn zurück
     */
    @GetMapping("/battery/in/latest")
    public ResponseEntity<BatteryIn> getLatestBatteryIn() {
        BatteryIn latest = solarDataService.getLatestBatteryIn();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/battery/out/latest
     * Gibt den neuesten Wert von BatteryOut zurück
     */
    @GetMapping("/battery/out/latest")
    public ResponseEntity<BatteryOut> getLatestBatteryOut() {
        BatteryOut latest = solarDataService.getLatestBatteryOut();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

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
     * GET /api/solar/battery/in/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt den Durchschnittswert von BatteryIn in einem Zeitraum zurück
     */
    @GetMapping("/battery/in/average")
    public ResponseEntity<Double> getAverageBatteryIn(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Double average = solarDataService.getAverageBatteryIn(start, end);
        return average != null ? ResponseEntity.ok(average) : ResponseEntity.notFound().build();
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
}
