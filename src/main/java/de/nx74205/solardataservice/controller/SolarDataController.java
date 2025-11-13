package de.nx74205.solardataservice.controller;

import de.nx74205.solardataservice.dto.HourlySummaryDto;
import de.nx74205.solardataservice.dto.BatterySocDto;
import de.nx74205.solardataservice.dto.DailyDischargeDto;
import de.nx74205.solardataservice.dto.DailyChargeDto;
import de.nx74205.solardataservice.dto.DailyGridExportDto;
import de.nx74205.solardataservice.dto.DailyGridImportDto;
import de.nx74205.solardataservice.entity.BatteryIn;
import de.nx74205.solardataservice.entity.BatteryOut;
import de.nx74205.solardataservice.entity.AcPowerOut;
import de.nx74205.solardataservice.entity.BatterySocPercent;
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
     * GET /api/solar/battery/charged?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59
     * Gibt die tägliche Gesamtladungsmenge der Batterie für den angegebenen Zeitraum zurück
     * Beispiel-Response:
     * [
     *   {
     *     "date": "2025-11-01",
     *     "totalCharged": 8540.2
     *   },
     *   {
     *     "date": "2025-11-02",
     *     "totalCharged": 9120.5
     *   }
     * ]
     */
    @GetMapping("/battery/charged")
    public ResponseEntity<List<DailyChargeDto>> getDailyCharge(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<DailyChargeDto> dailyCharge = solarDataService.getDailyChargeSums(start, end);
        return ResponseEntity.ok(dailyCharge);
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
     * GET /api/solar/battery/discharged?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59
     * Gibt die tägliche Gesamtentladungsmenge der Batterie für den angegebenen Zeitraum zurück
     * Beispiel-Response:
     * [
     *   {
     *     "date": "2025-11-01",
     *     "totalDischarged": 5420.5
     *   },
     *   {
     *     "date": "2025-11-02",
     *     "totalDischarged": 6230.8
     *   }
     * ]
     */
    @GetMapping("/battery/discharged")
    public ResponseEntity<List<DailyDischargeDto>> getDailyDischarge(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<DailyDischargeDto> dailyDischarge = solarDataService.getDailyDischargeSums(start, end);
        return ResponseEntity.ok(dailyDischarge);
    }

    /**
     * GET /api/solar/grid/export?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59
     * Gibt die tägliche Grid-Export-Menge für den angegebenen Zeitraum zurück
     * Beispiel-Response:
     * [
     *   {
     *     "date": "2025-11-01",
     *     "totalExported": 3250.5
     *   },
     *   {
     *     "date": "2025-11-02",
     *     "totalExported": 4120.8
     *   }
     * ]
     */
    @GetMapping("/grid/export")
    public ResponseEntity<List<DailyGridExportDto>> getDailyGridExport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<DailyGridExportDto> dailyGridExport = solarDataService.getDailyGridExportSums(start, end);
        return ResponseEntity.ok(dailyGridExport);
    }

    /**
     * GET /api/solar/grid/import?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59
     * Gibt die tägliche Grid-Import-Menge für den angegebenen Zeitraum zurück
     * Beispiel-Response:
     * [
     *   {
     *     "date": "2025-11-01",
     *     "totalImported": 2150.3
     *   },
     *   {
     *     "date": "2025-11-02",
     *     "totalImported": 1980.7
     *   }
     * ]
     */
    @GetMapping("/grid/import")
    public ResponseEntity<List<DailyGridImportDto>> getDailyGridImport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<DailyGridImportDto> dailyGridImport = solarDataService.getDailyGridImportSums(start, end);
        return ResponseEntity.ok(dailyGridImport);
    }

    /**
     * GET /api/solar/ac-out/summary?date=2025-11-01
     * Gibt stündliche Summen für BatteryIn, BatteryOut und AcOut für ein bestimmtes Datum zurück
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
    @GetMapping("/ac-out/summary")
    public ResponseEntity<List<HourlySummaryDto>> getHourlySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<HourlySummaryDto> summary = solarDataService.getHourlySummary(date);
        return ResponseEntity.ok(summary);
    }

    /**
     * GET /api/solar/ac-out/currentPower
     * Gibt den aktuellsten Wert von AcPowerOut zurück
     */
    @GetMapping("/ac-out/currentPower")
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
     * GET /api/solar/battery-soc/current
     * Gibt den aktuellen Battery State of Charge (SOC) als JSON mit Timestamp und Prozentwert zurück
     */
    @GetMapping("/battery-soc/current")
    public ResponseEntity<BatterySocDto> getCurrentBatterySoc() {
        BatterySocPercent latest = solarDataService.getLatestBatterySocPercent();
        if (latest != null && latest.getValue() != null && latest.getTime() != null) {
            Integer socPercent = (int) Math.round(latest.getValue());
            BatterySocDto dto = new BatterySocDto(latest.getTime(), socPercent);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
