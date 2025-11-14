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
     * GET /api/solar/battery/charge/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle BatteryIn Werte in einem Zeitraum zurück
     */
    @GetMapping("/battery/charged/range")
    public ResponseEntity<List<BatteryIn>> getBatteryInInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<BatteryIn> data = solarDataService.getBatteryInInRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/battery/charged?date=2025-11-01
     * Gibt die tägliche Gesamtladungsmenge der Batterie für das angegebene Datum zurück
     * Beispiel-Response:
     * {
     *   "date": "2025-11-01",
     *   "totalCharged": 8540.2
     * }
     */
    @GetMapping("/battery/charged")
    public ResponseEntity<DailyChargeDto> getDailyCharge(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<DailyChargeDto> dailyCharge = solarDataService.getDailyChargeSums(start, end);
        if (dailyCharge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyCharge.get(0));
    }

    /**
     * GET /api/solar/battery/discharge/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
     * Gibt alle BatteryOut Werte in einem Zeitraum zurück
     */
    @GetMapping("/battery/discharged/range")
    public ResponseEntity<List<BatteryOut>> getBatteryOutInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<BatteryOut> data = solarDataService.getBatteryOutInRange(start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/solar/battery/discharged?date=2025-11-01
     * Gibt die tägliche Gesamtentladungsmenge der Batterie für das angegebene Datum zurück
     * Beispiel-Response:
     * {
     *   "date": "2025-11-01",
     *   "totalDischarged": 5420.5
     * }
     */
    @GetMapping("/battery/discharged")
    public ResponseEntity<DailyDischargeDto> getDailyDischarge(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<DailyDischargeDto> dailyDischarge = solarDataService.getDailyDischargeSums(start, end);
        if (dailyDischarge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyDischarge.get(0));
    }

    /**
     * GET /api/solar/grid/export?date=2025-11-01
     * Gibt die tägliche Grid-Export-Menge für das angegebene Datum zurück
     * Beispiel-Response:
     * {
     *   "date": "2025-11-01",
     *   "totalExported": 3250.5
     * }
     */
    @GetMapping("/grid/export")
    public ResponseEntity<DailyGridExportDto> getDailyGridExport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<DailyGridExportDto> dailyGridExport = solarDataService.getDailyGridExportSums(start, end);
        if (dailyGridExport.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyGridExport.get(0));
    }

    /**
     * GET /api/solar/grid/import?date=2025-11-01
     * Gibt die tägliche Grid-Import-Menge für das angegebene Datum zurück
     * Beispiel-Response:
     * {
     *   "date": "2025-11-01",
     *   "totalImported": 2150.3
     * }
     */
    @GetMapping("/grid/import")
    public ResponseEntity<DailyGridImportDto> getDailyGridImport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<DailyGridImportDto> dailyGridImport = solarDataService.getDailyGridImportSums(start, end);
        if (dailyGridImport.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyGridImport.get(0));
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
    @GetMapping("/ac-out/range")
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
