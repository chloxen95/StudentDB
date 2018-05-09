package com.chl.studentdb.transation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class TestTransation {
	
	Jedis rc = null;
	Transaction t = null;
	String id = "1";
	Map<String, String> stu1 = new HashMap<>();
	Map<String, Map<String, String>> infomation = new HashMap<>();
	List<Object> lo = null;
	
	@Before
	public void beforeTest(){
		rc = RedisConnector.GetRedisConnection();
		//t = rc.multi();
		stu1.put("name", "aaa");
		stu1.put("birthday", "19950205");
		stu1.put("description", "aaa");
		stu1.put("avgscore", "99");
		infomation.put(id, stu1);
		System.out.println("Before: " + infomation);
	}
	
	@After
	public void afterTest(){
		//lo = t.exec();
		//System.out.println("After: " + lo);
	}
	
	@Test
	public void test() {
		t.sadd("id", "123456");
		t.zadd("avgscore", 20.00d, "123456");
		List<Object> lo = t.exec();
		System.out.println(lo);
	}
	
	@Test
	public void testInsertData(){
		t.sadd("id", id);
		t.zadd("avgscore", new Double(stu1.get("avgscore")), id);
		t.hmset(id, stu1);
	}
	
	@Test
	public void testDelData(){
		t.srem("id", id);
		t.del(id);
		t.zrem("avgscore", id);
	}
	
	@Test
	public void testGetAllData(){
		Set<String> idSet = rc.smembers("id");
		System.out.println(idSet);
		Map<String, Map<String, String>> data = new HashMap<>();
		for(String s: idSet){
			Map<String, String> info = rc.hgetAll(s);
			data.put(s, info);
		}
		System.out.println(data);
	}
	
	@Test
	public void testSortInfoByAvgScore(){
		Set<String> idSortedSet = rc.zrevrangeByScore("avgscore", "100", "0");
		System.out.println("Sorted: " + idSortedSet);
		
	}

}
