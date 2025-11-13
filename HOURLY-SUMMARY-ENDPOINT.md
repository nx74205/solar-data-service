# Hourly Summary Endpoint

## Beschreibung
Der neue REST-Endpoint `/api/solar/hourly-summary` gibt stündliche Summen für alle drei Metriken (BatteryIn, BatteryOut, AcOut) für ein bestimmtes Datum zurück.

## Endpoint

**GET** `/api/solar/hourly-summary?date=2025-11-01`

### Parameter
- `date` (required): Datum im Format `YYYY-MM-DD` (z.B. `2025-11-01`)

### Response Format
```json
[
  {
    "timestamp": "2025-11-01-00",
    "ac_out": 234.0,
    "battery_out": 123.0,
    "battery_in": 456.0
  },
  {
    "timestamp": "2025-11-01-01",
    "ac_out": 345.0,
    "battery_out": 234.0,
    "battery_in": 567.0
  }
]
```

### Beispiel-Requests

#### PowerShell
```powershell
# Lokaler Server
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/hourly-summary?date=2025-11-01" -Method Get

# Mit curl
curl "http://localhost:8080/api/solar/hourly-summary?date=2025-11-01"
```

#### Browser
```
http://localhost:8080/api/solar/hourly-summary?date=2025-11-01
```

## Funktionsweise

1. Der Endpoint akzeptiert ein Datum ohne Uhrzeit
2. Für jede Entity (BatteryIn, BatteryOut, AcOut) werden alle Werte des Tages nach Stunde gruppiert
3. Pro Stunde werden die Werte summiert
4. Das Ergebnis wird als JSON-Array zurückgegeben, sortiert nach Timestamp
5. Fehlende Stunden werden mit 0.0 gefüllt

## Technische Details

### Neue Dateien
- `HourlySummaryDto.java` - DTO für die JSON-Response
- Query-Methoden in allen drei Repositories: `getHourlySumsByDate(String date)`
- Service-Methode: `getHourlySummary(LocalDate date)`
- Controller-Endpoint: `/api/solar/hourly-summary`

### Datenbank-Queries
Die Repositories verwenden native SQL-Queries:
```sql
SELECT DATE_FORMAT(time, '%Y-%m-%d-%H') as hour, 
       SUM(value) as total 
FROM item0197 
WHERE DATE(time) = '2025-11-01' 
GROUP BY DATE_FORMAT(time, '%Y-%m-%d-%H') 
ORDER BY hour
```

## Testing

```powershell
# Starte den Server
.\mvnw.cmd spring-boot:run

# In einem anderen Terminal
curl "http://localhost:8080/api/solar/hourly-summary?date=2025-01-15"
```

