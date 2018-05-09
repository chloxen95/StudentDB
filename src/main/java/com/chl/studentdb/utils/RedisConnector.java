package com.chl.studentdb.utils;

import com.chl.studentdb.utils.ReadProperties;

import redis.clients.jedis.Jedis;

public class RedisConnector {
	private static final String redisHost = ReadProperties.getKey("redisHost");
	private static final String redisPassword = ReadProperties.getKey("redisPassword");
	private static final String redisPort = ReadProperties.getKey("redisPort");
		
	public static Jedis GetRedisConnection(){
		Jedis rConnection = new Jedis(redisHost, new Integer(redisPort));
		rConnection.auth(redisPassword);
		return rConnection;
	}
	
}
