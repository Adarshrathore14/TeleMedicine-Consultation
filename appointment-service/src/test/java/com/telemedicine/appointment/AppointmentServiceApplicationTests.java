package com.telemedicine.appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
class AppointmentServiceApplicationTest {
	private ConfigurableApplicationContext context;

	@BeforeEach
	public void setUp() {
		context = SpringApplication.run(AppointmentServiceApplication.class, "--server.port=0");
	}

	@AfterEach
	public void closeContext() {
		if (context != null) {
			context.close();
		}
	}

	@Test
	void contextLoadsSuccessfully() {
		assertNotNull(context);
		assertTrue(context.isActive());
	}

	@Test
	void applicationStartedSuccessfullyOnRandomPort() {
		assertNotNull(context.getEnvironment().getProperty("local.server.port"));
	}
}