package com.example.feild.encryption;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.feild.encryption.domain.service.EncryptionExampleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FeildEncryptionApplicationTests {
	
	@Autowired
	private EncryptionExampleService encryptionExampleService;

	@Test
	public void contextLoads() {
		long mobile = RandomUtils.nextLong(13000000000L, 19000000000L);
		for (int i = 0; i < 99895; i++) {
			String prefix = String.valueOf(mobile);
			encryptionExampleService.addEntity(prefix, prefix, prefix, prefix + "@xiangshang.com", "向上No." + (i + 1));
			mobile += 1;
		}
	}
}
