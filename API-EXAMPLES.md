# Solar Data Service - API Beispiele

## Base URL
```
http://localhost:8080/api/solar
```

## Übersicht aller Endpunkte

### Battery In (Batterieladung) - item0197
- Werte in Zeitraum: `GET /battery/in/range?start={datetime}&end={datetime}`
- Tägliche Ladungssummen (pro Tag): `GET /battery/charged?date={date}`

### Battery Out (Batterieentladung) - item0198
- Werte in Zeitraum: `GET /battery/out/range?start={datetime}&end={datetime}`
- Tägliche Entladungssummen (pro Tag): `GET /battery/discharged?date={date}`

### AC Power Out (Wechselstromleistung) - item0181
- Aktueller Wert: `GET /ac-out/currentPower`
- Werte in Zeitraum: `GET /ac-power-out/range?start={datetime}&end={datetime}`

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
curl "http://localhost:8080/api/solar/ac-out/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

---

## Battery In (Ladung)

### Werte in einem Zeitraum abrufen
```bash
curl "http://localhost:8080/api/solar/battery/charged/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
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

### Tägliche Ladungssummen abrufen (für ein Datum)
Gibt die Gesamtladungsmenge für einen bestimmten Tag zurück.

```bash
curl "http://localhost:8080/api/solar/battery/charged?date=2025-11-01"
```

**Beispiel-Response:**
```json
[
  {
    "date": "2025-11-01",
    "totalCharged": 8540.2
  }
]
```

**PowerShell (einzelnes Datum):**
```powershell
$date = "2025-11-01"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?date=$date" -Method Get
```

---

## Battery Out (Entladung)

### Werte in einem Zeitraum abrufen
```bash
curl "http://localhost:8080/api/solar/battery/discharged/range?start=2025-11-13T00:00:00&end=2025-11-13T23:59:59"
```

### Tägliche Entladungssummen abrufen (für ein Datum)
Gibt die Gesamtentladungsmenge für einen bestimmten Tag zurück.

```bash
curl "http://localhost:8080/api/solar/battery/discharged?date=2025-11-01"
```

**Beispiel-Response:**
```json
{
  "date": "2025-11-01",
  "totalDischarged": 5420.5
}
```

**PowerShell (einzelnes Datum):**
```powershell
$date = "2025-11-01"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?date=$date" -Method Get
```

---

## Grid Export (Netzeinspeisung)

### Tägliche Netzeinspeisung abrufen
Gibt die Gesamtmenge der ins Netz eingespeisten Energie für einen bestimmten Tag zurück.

```bash
curl "http://localhost:8080/api/solar/grid/export?date=2025-11-01"
```

**Beispiel-Response:**
```json
{
  "date": "2025-11-01",
  "totalExported": 3250.5
}
```

**PowerShell (einzelnes Datum):**
```powershell
$date = "2025-11-01"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/grid/export?date=$date" -Method Get
```

---

## Grid Import (Netzbezug)

### Täglichen Netzbezug abrufen
Gibt die Gesamtmenge der aus dem Netz bezogenen Energie für einen bestimmten Tag zurück.

```bash
curl "http://localhost:8080/api/solar/grid/import?date=2025-11-01"
```

**Beispiel-Response:**
```json
{
  "date": "2025-11-01",
  "totalImported": 2150.3
}
```

**PowerShell (einzelnes Datum):**
```powershell
$date = "2025-11-01"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/grid/import?date=$date" -Method Get
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

# Beispiel: AC Power Range
$acPowerData = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/ac-out/range?start=$startStr&end=$endStr" -Method Get
Write-Host "Anzahl der AC-Power-Werte (24h): $($acPowerData.Count)"
```

### Tägliche Entladungssummen der letzten 7 Tage
```powershell
$end = Get-Date
$start = $end.AddDays(-6)
$dates = @()
for ($d = $start.Date; $d -le $end.Date; $d = $d.AddDays(1)) {
    $dates += $d.ToString("yyyy-MM-dd")
}

$totalWeek = 0
foreach ($date in $dates) {
    $resp = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?date=$date" -Method Get
    if ($resp -and $resp.Count -gt 0) {
        $val = $resp[0].totalDischarged
        Write-Host "$date: $([math]::Round($val, 2)) Wh"
        $totalWeek += $val
    } else {
        Write-Host "$date: no data"
    }
}
Write-Host "Gesamt (7 Tage): $([math]::Round($totalWeek, 2)) Wh"
Write-Host "Durchschnitt/Tag: $([math]::Round($totalWeek / 7, 2)) Wh"
```

### Tägliche Ladungssummen der letzten 7 Tage
```powershell
$end = Get-Date
$start = $end.AddDays(-6)
$dates = @()
for ($d = $start.Date; $d -le $end.Date; $d = $d.AddDays(1)) {
    $dates += $d.ToString("yyyy-MM-dd")
}

$totalWeek = 0
foreach ($date in $dates) {
    $resp = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?date=$date" -Method Get
    if ($resp -and $resp.Count -gt 0) {
        $val = $resp[0].totalCharged
        Write-Host "$date: $([math]::Round($val, 2)) Wh"
        $totalWeek += $val
    } else {
        Write-Host "$date: no data"
    }
}
Write-Host "Gesamt (7 Tage): $([math]::Round($totalWeek, 2)) Wh"
Write-Host "Durchschnitt/Tag: $([math]::Round($totalWeek / 7, 2)) Wh"
```

### Batterie-Effizienz berechnen (Ladung vs Entladung) für die letzten 7 Tage
```powershell
$end = Get-Date
$start = $end.AddDays(-6)
$dates = @()
for ($d = $start.Date; $d -le $end.Date; $d = $d.AddDays(1)) {
    $dates += $d.ToString("yyyy-MM-dd")
}

$totalCharged = 0
$totalDischarged = 0
foreach ($date in $dates) {
    try {
        $c = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/charged?date=$date" -Method Get
        $totalCharged += $c.totalCharged
    } catch {}
    try {
        $d = Invoke-RestMethod -Uri "http://localhost:8080/api/solar/battery/discharged?date=$date" -Method Get
        $totalDischarged += $d.totalDischarged
    } catch {}
}
if ($totalCharged -gt 0) {
    $efficiency = ($totalDischarged / $totalCharged) * 100
    Write-Host "Gesamt geladen: $([math]::Round($totalCharged, 2)) Wh"
    Write-Host "Gesamt entladen: $([math]::Round($totalDischarged, 2)) Wh"
    Write-Host "Effizienz: $([math]::Round($efficiency, 2))%"
} else {
    Write-Host "Keine Ladedaten vorhanden"
}
```

## Hinweise

- **Zeitformat:** ISO 8601 Format `YYYY-MM-DDTHH:MM:SS`
- **Datumsformat:** ISO 8601 Format `YYYY-MM-DD`
- **HTTP Methode:** Alle Endpoints verwenden GET
- **Rückgabeformat:** JSON
- **Encoding:** UTF-8
