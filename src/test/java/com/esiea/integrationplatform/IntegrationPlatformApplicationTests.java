package com.esiea.integrationplatform;

import com.esiea.integrationplatform.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class IntegrationPlatformApplicationTests {

	@Test
	void contextLoads() {
	}

}
