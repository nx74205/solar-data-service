package de.nx74205.solardataservice.controller;

import de.nx74205.solardataservice.entity.Item0197;
import de.nx74205.solardataservice.entity.Item0198;
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
     * GET /api/solar/item0197/latest
     * Gibt den neuesten Wert von item0197 zurück
     */
    @GetMapping("/item0197/latest")
    public ResponseEntity<Item0197> getLatestItem0197() {
        Item0197 latest = solarDataService.getLatestItem0197();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/item0198/latest
     * Gibt den neuesten Wert von item0198 zurück
     */
    @GetMapping("/item0198/latest")
    public ResponseEntity<Item0198> getLatestItem0198() {
        Item0198 latest = solarDataService.getLatestItem0198();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/item0197/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle item0197 Werte in einem Zeitraum zurück
     */
    @GetMapping("/item0197/range")
    public ResponseEntity<List<Item0197>> getItem0197InRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Item0197> data = solarDataService.getItem0197InRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/item0198/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle item0198 Werte in einem Zeitraum zurück
     */
    @GetMapping("/item0198/range")
    public ResponseEntity<List<Item0198>> getItem0198InRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Item0198> data = solarDataService.getItem0198InRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/item0197/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt den Durchschnittswert von item0197 in einem Zeitraum zurück
     */
    @GetMapping("/item0197/average")
    public ResponseEntity<Double> getAverageItem0197(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Double average = solarDataService.getAverageItem0197(start, end);
        return average != null ? ResponseEntity.ok(average) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/solar/item0198/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt den Durchschnittswert von item0198 in einem Zeitraum zurück
     */
    @GetMapping("/item0198/average")
    public ResponseEntity<Double> getAverageItem0198(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Double average = solarDataService.getAverageItem0198(start, end);
        return average != null ? ResponseEntity.ok(average) : ResponseEntity.notFound().build();
    }
}

