package com.etraveli.cardcost;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("Smoke test")
class CardcostApplicationTests {

	@Test
	void contextLoads() {
	  Assertions.assertEquals(1, 1);
	}

}
