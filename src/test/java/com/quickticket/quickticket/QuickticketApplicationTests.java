package com.quickticket.quickticket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuickticketApplicationTests {

	@Test
	void contextLoads() {
        assertEquals(1 + 1, 2);
	}

}
