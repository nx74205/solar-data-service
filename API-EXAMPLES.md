# Solar Data Service - API Beispiele

## Base URL
```
http://localhost:8080/api/solar
```

## Übersicht aller Endpunkte

### Battery In (Batterieladung) - item0197
- Werte in Zeitraum: `GET /battery/in/range?start={datetime}&end={datetime}`
- Tägliche Ladungssummen: `GET /battery/charged?start={datetime}&end={datetime}`

### Battery Out (Batterieentladung) - item0198
- Werte in Zeitraum: `GET /battery/out/range?start={datetime}&end={datetime}`
- Tägliche Entladungssummen: `GET /battery/discharged?start={datetime}&end={datetime}`

### AC Power Out (Wechselstromleistung) - item0181
- Aktueller Wert: `GET /ac-out/currentPower`
- Werte in Zeitraum: `GET /ac-power-out/range?start={datetime}&end={datetime}`
- Durchschnittswert: `GET /ac-power-out/average?start={datetime}&end={datetime}`

### Battery State of Charge (Batterieladezustand) - item0199
- Aktueller Wert (als Prozent): `GET /battery-soc/current`

### Zusammenfassung
- Stündliche Zusammenfassung: `GET /ac-out/summary?date={date}`

---

## Battery SOC (State of Charge)

### Aktuellen Ladezustand abrufen
Gibt den aktuellen Batterieladezustand als JSON mit Timestamp und Prozentwert (ohne Nachkommastellen) zurück.

```bash
curl http://localhost:8080/api/solar/battery-soc/current
```

**Beispiel-Response:**
```json
{
  "timestamp": "2025-11-13T12:45:30",
  "percent": 85
}
```

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery-soc/current" -Method Get
```

**PowerShell-Ausgabe formatiert:**
```powershell
$soc = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery-soc/current" -Method Get
Write-Host "Battery SOC: $($soc.percent)% (Stand: $($soc.timestamp))"
```

---

## AC Power Out

### Aktuellen Wert abrufen
```bash
curl http://localhost:8080/api/solar/ac-out/currentPower
```

**Beispiel-Response:**
```json
{
  "time": "2025-11-13T12:30:00",
  "value": 2500.5
}
```

### Werte in einem Zeitraum abrufen
```bash
curl "http://localhost:8080/api/solar/ac-power-out/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

### Durchschnittswert berechnen
```bash
curl "http://localhost:8080/api/solar/ac-power-out/average?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

**Beispiel-Response:**
```json
1850.75
```

**PowerShell:**
```powershell
$start = "2025-11-13T00:00:00"
$end = "2025-11-13T23:59:59"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-out/currentPower" -Method Get
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-power-out/range?start=$start&end=$end" -Method Get
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-power-out/average?start=$start&end=$end" -Method Get
```

---

## Battery In (Ladung)

### Werte in einem Zeitraum abrufen
```bash
curl "http://localhost:8080/api/solar/battery/in/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

**Beispiel-Response:**
```json
[
  {
    "time": "2025-11-13T10:00:00",
    "value": 500.0
  },
  {
    "time": "2025-11-13T11:00:00",
    "value": 750.0
  }
]
```

### Tägliche Ladungssummen abrufen
Gibt die Gesamtladungsmenge pro Tag für einen gegebenen Zeitraum zurück.

```bash
curl "http://localhost:8080/api/solar/battery/charged?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59"
```

**Beispiel-Response:**
```json
[
  {
    "date": "2025-11-01",
    "totalCharged": 8540.2
  },
  {
    "date": "2025-11-02",
    "totalCharged": 9120.5
  },
  {
    "date": "2025-11-03",
    "totalCharged": 7835.8
  }
]
```

**PowerShell:**
```powershell
$start = "2025-11-13T00:00:00"
$end = "2025-11-13T23:59:59"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/in/range?start=$start&end=$end" -Method Get

# Tägliche Ladungssummen für einen Zeitraum
$start = "2025-11-01T00:00:00"
$end = "2025-11-13T23:59:59"
$dailyCharge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?start=$start&end=$end" -Method Get
$dailyCharge | ForEach-Object { Write-Host "$($_.date): $($_.totalCharged) Wh" }
```

---

## Battery Out (Entladung)

### Werte in einem Zeitraum abrufen
```bash
curl "http://localhost:8080/api/solar/battery/out/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

### Tägliche Entladungssummen abrufen
Gibt die Gesamtentladungsmenge pro Tag für einen gegebenen Zeitraum zurück.

```bash
curl "http://localhost:8080/api/solar/battery/discharged?start=2025-11-01T00:00:00&end=2025-11-13T23:59:59"
```

**Beispiel-Response:**
```json
[
  {
    "date": "2025-11-01",
    "totalDischarged": 5420.5
  },
  {
    "date": "2025-11-02",
    "totalDischarged": 6230.8
  },
  {
    "date": "2025-11-03",
    "totalDischarged": 5875.3
  }
]
```

**PowerShell:**
```powershell
$start = "2025-11-13T00:00:00"
$end = "2025-11-13T23:59:59"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/out/range?start=$start&end=$end" -Method Get

# Tägliche Entladungssummen für einen Zeitraum
$start = "2025-11-01T00:00:00"
$end = "2025-11-13T23:59:59"
$dailyDischarge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?start=$start&end=$end" -Method Get
$dailyDischarge | ForEach-Object { Write-Host "$($_.date): $($_.totalDischarged) Wh" }
```

---

## Hourly Summary (Stündliche Zusammenfassung)

Gibt stündliche Summen für AcOut, BatteryOut und BatteryIn für ein bestimmtes Datum zurück.

### Zusammenfassung für ein Datum abrufen
```bash
curl "http://localhost:8080/api/solar/ac-out/summary?date=2025-11-13"
```

**Beispiel-Response:**
```json
[
  {
    "timestamp": "2025-11-13-00",
    "ac_out": 234.0,
    "battery_out": 123.0,
    "battery_in": 456.0
  },
  {
    "timestamp": "2025-11-13-01",
    "ac_out": 189.5,
    "battery_out": 98.2,
    "battery_in": 512.3
  }
]
```

**PowerShell:**
```powershell
$date = "2025-11-13"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-out/summary?date=$date" -Method Get
```

---

## Hinweise

- **Zeitformat:** ISO 8601 Format `YYYY-MM-DDTHH:MM:SS`
- **Datumsformat:** ISO 8601 Format `YYYY-MM-DD`
- **HTTP Methode:** Alle Endpoints verwenden GET
- **Rückgabeformat:** JSON
- **Encoding:** UTF-8

## Häufige Anwendungsfälle

### Tagesübersicht abrufen
```powershell
# Stündliche Zusammenfassung für heute
$today = (Get-Date).ToString("yyyy-MM-dd")
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-out/summary?date=$today" -Method Get
```

### Aktuelle Werte Dashboard
```powershell
# Aktueller Batterieladezustand
$soc = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery-soc/current" -Method Get
Write-Host "Battery SOC: $($soc.percent)% (Stand: $($soc.timestamp))"

# Aktuelle AC Leistung
$acPower = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-out/currentPower" -Method Get
Write-Host "AC Power: $($acPower.value)W at $($acPower.time)"
```

### Durchschnittswerte der letzten 24 Stunden
```powershell
$end = Get-Date
$start = $end.AddHours(-24)
$startStr = $start.ToString("yyyy-MM-ddTHH:mm:ss")
$endStr = $end.ToString("yyyy-MM-ddTHH:mm:ss")

$avgAcPower = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-power-out/average?start=$startStr&end=$endStr" -Method Get

Write-Host "Durchschnittliche AC-Leistung (24h): $avgAcPower W"
```

### Tägliche Entladungssummen der letzten 7 Tage
```powershell
$end = Get-Date
$start = $end.AddDays(-7)
$startStr = $start.ToString("yyyy-MM-ddTHH:mm:ss")
$endStr = $end.ToString("yyyy-MM-ddTHH:mm:ss")

$dailyDischarge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?start=$startStr&end=$endStr" -Method Get

Write-Host "`nTägliche Batterie-Entladung (letzte 7 Tage):"
$totalWeek = 0
$dailyDischarge | ForEach-Object {
    Write-Host "  $($_.date): $([math]::Round($_.totalDischarged, 2)) Wh"
    $totalWeek += $_.totalDischarged
}
Write-Host "Gesamt (7 Tage): $([math]::Round($totalWeek, 2)) Wh"
Write-Host "Durchschnitt/Tag: $([math]::Round($totalWeek / 7, 2)) Wh"
```

### Tägliche Ladungssummen der letzten 7 Tage
```powershell
$end = Get-Date
$start = $end.AddDays(-7)
$startStr = $start.ToString("yyyy-MM-ddTHH:mm:ss")
$endStr = $end.ToString("yyyy-MM-ddTHH:mm:ss")

$dailyCharge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?start=$startStr&end=$endStr" -Method Get

Write-Host "`nTägliche Batterie-Ladung (letzte 7 Tage):"
$totalWeek = 0
$dailyCharge | ForEach-Object {
    Write-Host "  $($_.date): $([math]::Round($_.totalCharged, 2)) Wh"
    $totalWeek += $_.totalCharged
}
Write-Host "Gesamt (7 Tage): $([math]::Round($totalWeek, 2)) Wh"
Write-Host "Durchschnitt/Tag: $([math]::Round($totalWeek / 7, 2)) Wh"
```

### Batterie-Effizienz berechnen (Ladung vs Entladung)
```powershell
$end = Get-Date
$start = $end.AddDays(-7)
$startStr = $start.ToString("yyyy-MM-ddTHH:mm:ss")
$endStr = $end.ToString("yyyy-MM-ddTHH:mm:ss")

$dailyCharge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?start=$startStr&end=$endStr" -Method Get
$dailyDischarge = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?start=$startStr&end=$endStr" -Method Get

$totalCharged = ($dailyCharge | Measure-Object -Property totalCharged -Sum).Sum
$totalDischarged = ($dailyDischarge | Measure-Object -Property totalDischarged -Sum).Sum
$efficiency = ($totalDischarged / $totalCharged) * 100

Write-Host "`nBatterie-Effizienz (7 Tage):"
Write-Host "Gesamt geladen: $([math]::Round($totalCharged, 2)) Wh"
Write-Host "Gesamt entladen: $([math]::Round($totalDischarged, 2)) Wh"
Write-Host "Effizienz: $([math]::Round($efficiency, 2))%"
```



