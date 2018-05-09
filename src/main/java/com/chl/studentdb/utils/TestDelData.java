package com.chl.studentdb.utils;

import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestDelData {

	Jedis rc = RedisConnector.GetRedisConnection();

	@Test
	public void test() {
		DelLastData(30);
	}

	private void DelLastData(int num) {
		Set<String> keys = rc.keys("*");
		if (keys.size() == 0 || keys.size() < num) {
			System.out.println("Failed");
			return;
		}

		long maxId = 0L;
		for (String key : keys) {
			if (new Long(key) > maxId) {
				maxId = new Long(key);
			}
		}

		for (int i = 0; i < num; i++) {
			rc.del(maxId - i + "");
		}

	}
}
