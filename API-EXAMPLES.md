# Solar Data Service - API Beispiele

## Base URL
```
http://localhost:8080
```

## Item0197 Endpoints

### Neuesten Wert abrufen
```bash
curl http://localhost:8080/api/solar/item0197/latest
```

### Werte der letzten 24 Stunden abrufen
```bash
curl "http://localhost:8080/api/solar/item0197/range?start=2025-11-11T00:00:00&end=2025-11-12T00:00:00"
```

### Durchschnittswert der letzten 24 Stunden
```bash
curl "http://localhost:8080/api/solar/item0197/average?start=2025-11-11T00:00:00&end=2025-11-12T00:00:00"
```

## Item0198 Endpoints

### Neuesten Wert abrufen
```bash
curl http://localhost:8080/api/solar/item0198/latest
```

### Werte der letzten 24 Stunden abrufen
```bash
curl "http://localhost:8080/api/solar/item0198/range?start=2025-11-11T00:00:00&end=2025-11-12T00:00:00"
```

### Durchschnittswert der letzten 24 Stunden
```bash
curl "http://localhost:8080/api/solar/item0198/average?start=2025-11-11T00:00:00&end=2025-11-12T00:00:00"
```

## PowerShell Beispiele

### Neuesten Wert abrufen
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/item0197/latest" -Method Get
```

### Werte in einem Zeitraum
```powershell
$start = "2025-11-11T00:00:00"
$end = "2025-11-12T00:00:00"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/item0197/range?start=$start&end=$end" -Method Get
```

### Durchschnittswert
```powershell
$start = "2025-11-11T00:00:00"
$end = "2025-11-12T00:00:00"
Invoke-RestMethod -Uri "http://localhost:8080/api/solar/item0197/average?start=$start&end=$end" -Method Get
```

## Hinweise

- Zeitformat: ISO 8601 Format `YYYY-MM-DDTHH:MM:SS`
- Alle Endpoints verwenden HTTP GET Methode
- Rückgabeformat: JSON

