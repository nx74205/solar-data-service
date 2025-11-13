# AcPowerOut Repository

## Beschreibung
Neues JPA-Repository für die Datenbanktabelle `item0181`, benannt als `AcPowerOut`.

## Entity
**Datei:** `src/main/java/de/nx74205/solardataservice/entity/AcPowerOut.java`

```java
@Entity
@Table(name = "item0181")
public class AcPowerOut {
    @Id
    @Column(name = "time")
    private LocalDateTime time;
    
    @Column(name = "value")
    private Double value;
}
```

## Repository
**Datei:** `src/main/java/de/nx74205/solardataservice/repository/AcPowerOutRepository.java`

### Verfügbare Methoden
- `findByTimeBetween(LocalDateTime start, LocalDateTime end)` - Findet alle Einträge in einem Zeitraum
- `findTopByOrderByTimeDesc()` - Gibt den neuesten Eintrag zurück
- `findByTimeAfter(LocalDateTime time)` - Findet alle Einträge nach einem bestimmten Zeitpunkt
- `getAverageValueBetween(LocalDateTime start, LocalDateTime end)` - Berechnet den Durchschnitt in einem Zeitraum
- `getHourlySumsByDate(String date)` - Gibt stündliche Summen für ein bestimmtes Datum zurück

## REST-Endpoints

### 1. Neuester Wert
**GET** `/api/solar/ac-power-out/latest`

**Beispiel:**
```powershell
curl http://localhost:8080/api/solar/ac-power-out/latest
```

**Response:**
```json
{
  "time": "2025-11-13T10:30:00",
  "value": 234.5
}
```

### 2. Werte in einem Zeitraum
**GET** `/api/solar/ac-power-out/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00`

**Beispiel:**
```powershell
curl "http://localhost:8080/api/solar/ac-power-out/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00"
```

**Response:**
```json
[
  {
    "time": "2025-01-01T10:00:00",
    "value": 123.4
  },
  {
    "time": "2025-01-01T11:00:00",
    "value": 234.5
  }
]
```

### 3. Durchschnittswert
**GET** `/api/solar/ac-power-out/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00`

**Beispiel:**
```powershell
curl "http://localhost:8080/api/solar/ac-power-out/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00"
```

**Response:**
```json
178.95
```

## Service-Methoden
**Datei:** `src/main/java/de/nx74205/solardataservice/service/SolarDataService.java`

- `getLatestAcPowerOut()` - Gibt den neuesten AcPowerOut-Wert zurück
- `getAcPowerOutInRange(LocalDateTime start, LocalDateTime end)` - Gibt alle AcPowerOut-Werte in einem Zeitraum zurück
- `getAverageAcPowerOut(LocalDateTime start, LocalDateTime end)` - Gibt den Durchschnittswert zurück

## Integration mit Hourly Summary
Das neue Repository ist bereits für die Integration mit dem `/api/solar/hourly-summary` Endpoint vorbereitet und kann bei Bedarf hinzugefügt werden.

## Build-Status
- ✅ Entity erstellt
- ✅ Repository erstellt
- ✅ Service-Methoden hinzugefügt
- ✅ REST-Endpoints implementiert
- ✅ Maven Build erfolgreich

