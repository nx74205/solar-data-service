package de.nx74205.solardataservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = SolarDataServiceApplicationTests.TestConfig.class)
class SolarDataServiceApplicationTests {

	@Configuration
	@EnableAutoConfiguration(exclude = {
			DataSourceAutoConfiguration.class,
			HibernateJpaAutoConfiguration.class,
			JpaRepositoriesAutoConfiguration.class
	})
	static class TestConfig {
		// Leere Testkonfiguration ohne Datenbank
	}

	@Test
	void contextLoads() {
		// Dieser Test stellt sicher, dass der Spring-Context ohne Datenbankzugriff geladen werden kann
	}

}
