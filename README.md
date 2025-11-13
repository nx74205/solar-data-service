# Solar Data Service

Spring Boot Service zur Verarbeitung von OpenHAB Solar-Daten aus der MariaDB-Datenbank.

## Konfiguration

Die Datenbankverbindung ist in `application.properties` konfiguriert:
- **Server**: camilla
- **Datenbank**: openhab
- **Tabellen**: item0197, item0198
- **API Port**: 8080

## Entities

### Item0197 und Item0198
Beide Entities repräsentieren OpenHAB-Datentabellen mit folgender Struktur:
- `itemId` (Long) - Primärschlüssel
- `time` (LocalDateTime) - Zeitstempel
- `value` (String) - Gemessener Wert

## Repositories

### Item0197Repository und Item0198Repository
JPA Repositories mit folgenden Methoden:
- `findByTimeBetween(start, end)` - Alle Einträge in einem Zeitraum
- `findTopByOrderByTimeDesc()` - Neuester Eintrag
- `findByTimeAfter(time)` - Alle Einträge nach einem Zeitpunkt
- `getAverageValueBetween(start, end)` - Durchschnittswert in einem Zeitraum

## Service

### SolarDataService
Service-Klasse mit Beispiel-Methoden:
- `getLatestItem0197()` / `getLatestItem0198()` - Neueste Werte
- `getItem0197InRange(start, end)` / `getItem0198InRange(start, end)` - Werte im Zeitraum
- `getAverageItem0197(start, end)` / `getAverageItem0198(start, end)` - Durchschnittswerte

## REST API Endpoints

### Item0197 Endpoints

**Neuesten Wert abrufen:**
```
GET http://localhost:8080/api/solar/item0197/latest
```

**Werte in einem Zeitraum abrufen:**
```
GET http://localhost:8080/api/solar/item0197/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
```

**Durchschnittswert berechnen:**
```
GET http://localhost:8080/api/solar/item0197/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
```

### Item0198 Endpoints

**Neuesten Wert abrufen:**
```
GET http://localhost:8080/api/solar/item0198/latest
```

**Werte in einem Zeitraum abrufen:**
```
GET http://localhost:8080/api/solar/item0198/range?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
```

**Durchschnittswert berechnen:**
```
GET http://localhost:8080/api/solar/item0198/average?start=2025-01-01T00:00:00&end=2025-01-02T00:00:00
```

## Verwendung (Programmatisch)

```java
@Autowired
private SolarDataService solarDataService;

// Neueste Werte abrufen
Item0197 latest = solarDataService.getLatestItem0197();

// Daten aus den letzten 24 Stunden
LocalDateTime now = LocalDateTime.now();
LocalDateTime yesterday = now.minusDays(1);
List<Item0197> data = solarDataService.getItem0197InRange(yesterday, now);

// Durchschnittswert berechnen
Double average = solarDataService.getAverageItem0197(yesterday, now);
```

## Starten der Anwendung

Über die IDE (IntelliJ IDEA):
- Öffnen Sie `SolarDataServiceApplication.java`
- Klicken Sie auf den grünen Start-Button

Oder über Maven:
```cmd
mvnw.cmd spring-boot:run
```

Die API ist dann verfügbar unter: `http://localhost:8080`

## Hinweise

⚠️ **Wichtig**: Falls die tatsächliche Tabellenstruktur von OpenHAB abweicht, müssen die Entity-Klassen entsprechend angepasst werden. Überprüfen Sie die Spaltennamen und -typen in der Datenbank.

Die häufigsten OpenHAB-Spalten sind:
- `Time` oder `time`
- `Value` oder `value`
- `ItemId` oder `itemid` oder als Auto-Increment ID

