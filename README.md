# Solar Data Service

Ein Spring Boot REST-Service zum Abrufen und Auswerten von Solar- und Batteriedaten aus einer MariaDB-Datenbank.

## Übersicht

Dieser Service bietet REST-Endpunkte zum Abrufen von:
- Batterieladung (Battery In)
- Batterieentladung (Battery Out)
- Batterieladezustand (State of Charge)
- AC-Leistungsausgabe
- Netzeinspeisung (Grid Export)
- Netzbezug (Grid Import)
- Stündliche Zusammenfassungen

## Voraussetzungen

- Java 17 oder höher
- Maven 3.6+
- MariaDB-Datenbank mit entsprechenden Tabellen

## Installation & Start

### Mit Maven Wrapper

```bash
# Projekt bauen
./mvnw clean install

# Tests ausführen
./mvnw test

# Anwendung starten
./mvnw spring-boot:run
```

### Mit Maven

```bash
# Projekt bauen
mvn clean install

# Tests ausführen
mvn test

# Anwendung starten
mvn spring-boot:run
```

## Konfiguration

Die Datenbankkonfiguration befindet sich in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://kermittt:3306/openhab
spring.datasource.username=openhab
spring.datasource.password=openhab
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

Passen Sie diese Einstellungen an Ihre Datenbankumgebung an.

## API-Endpunkte

Der Service läuft standardmäßig auf Port 8080. Die Basis-URL ist:
```
http://localhost:8080/api/solar
```

Eine detaillierte Dokumentation aller Endpunkte und Beispiele finden Sie in [API-EXAMPLES.md](API-EXAMPLES.md).

### Schnellübersicht

| Endpunkt | Beschreibung |
|----------|--------------|
| `GET /battery-soc/current` | Aktueller Batterieladezustand in Prozent |
| `GET /ac-out/currentPower` | Aktuelle AC-Leistung |
| `GET /battery/charged?date={date}` | Tägliche Batterieladung |
| `GET /battery/discharged?date={date}` | Tägliche Batterieentladung |
| `GET /battery/charged/range?start={datetime}&end={datetime}` | Ladungsdaten im Zeitraum |
| `GET /battery/discharged/range?start={datetime}&end={datetime}` | Entladungsdaten im Zeitraum |
| `GET /grid/export?date={date}` | Tägliche Netzeinspeisung |
| `GET /grid/import?date={date}` | Täglicher Netzbezug |
| `GET /ac-out/summary?date={date}` | Stündliche Zusammenfassung |

## Projekt-Struktur

```
solar-data-service/
├── src/
│   ├── main/
│   │   ├── java/de/nx74205/solardataservice/
│   │   │   ├── SolarDataServiceApplication.java
│   │   │   ├── controller/          # REST-Controller
│   │   │   ├── dto/                  # Data Transfer Objects
│   │   │   ├── entity/               # JPA-Entitäten
│   │   │   ├── repository/           # Spring Data Repositories
│   │   │   └── service/              # Business Logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/de/nx74205/solardataservice/
│           ├── SolarDataServiceApplicationTests.java
│           └── controller/           # Controller-Tests
├── API-EXAMPLES.md
├── README.md
└── pom.xml
```

## Datenbank-Schema

Der Service greift auf folgende MariaDB-Tabellen zu:

- `item0037` - Battery In (Batterieladung)
- `item0038` - Battery Out (Batterieentladung)
- `item0057` - Battery SOC Percent (Batterieladezustand)
- `item0033` - AC Power Out (AC-Leistung)
- `item0031` - Grid Export (Netzeinspeisung)
- `item0032` - Grid Import (Netzbezug)
- `item0034` - AC Out

Alle Tabellen haben die gleiche Struktur:
- `time` (TIMESTAMP) - Primärschlüssel
- `value` (DOUBLE) - Messwert

## Tests

Die Tests verwenden eine In-Memory H2-Datenbank für Unit- und Integrationstests. Sie benötigen keine laufende MariaDB-Instanz zum Ausführen der Tests.

```bash
# Alle Tests ausführen
./mvnw test

# Spezifischen Test ausführen
./mvnw test -Dtest=SolarDataControllerTest
```

## Entwicklung

### Anforderungen
- IntelliJ IDEA oder Eclipse mit Spring Boot Support
- Lombok-Plugin (für IDE)

### Build

```bash
# JAR erstellen
./mvnw clean package

# JAR ohne Tests erstellen
./mvnw clean package -DskipTests
```

Das generierte JAR befindet sich in `target/solar-data-service-1.0.0.jar`.

### Ausführen des JAR

```bash
java -jar target/solar-data-service-1.0.0.jar
```

## Lizenz

Dieses Projekt ist für den persönlichen Gebrauch bestimmt.

## Autor

nx74205
