package com.neperia.mySpotify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class MySpotifyApplicationTests {

	@Test
	void contextLoads() {
		Duration d1 = Duration.ofMinutes(25).plusSeconds(30);
		System.out.println(d1.toString().replace("PT", ""));
	}

}
