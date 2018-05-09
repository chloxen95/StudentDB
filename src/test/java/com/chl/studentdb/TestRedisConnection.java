package com.chl.studentdb;

import org.junit.Test;

import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;

public class TestRedisConnection {

	@Test
	public void test() {
		Jedis rc = RedisConnector.GetRedisConnection();
		rc.set("ab", "aa");
	}

}
