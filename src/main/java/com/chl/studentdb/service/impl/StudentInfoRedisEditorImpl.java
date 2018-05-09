package com.chl.studentdb.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chl.studentdb.service.StudentInfoRedisEditor;
import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class StudentInfoRedisEditorImpl implements StudentInfoRedisEditor {

	/**
	 * 应同时修改如下数据类型
	 * Set：id 
	 * HashMap(s)：${id} 
	 * Sorted Set：avgscores 
	 */

	Jedis rc = RedisConnector.GetRedisConnection();

	{
		if (!rc.exists("id")) { // 判断List：id是否存在
			rc.sadd("id", "2013220701000");
			rc.srem("id");
		}
		if (!rc.exists("avgscores")) { // 判断Sorted Set：id是否存在
			rc.zadd("avgscores", 0.0, "2013220701000");
			rc.zrem("avgscores", "2013220701000");
		}
	}

	@Override
	public boolean InsertInfo(Map<String, String> data) throws IOException {
		Transaction t = rc.multi();
		long count = rc.scard("id");
		String id = 2013220701001l + count + "";
		
		t.sadd("id", id);
		t.hmset(id, data);
		t.zadd("avgscore", new Double(data.get("avgscore")), id);
		List<Object> lo = t.exec();
		t.close();
		if(lo != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean DeleteInfo(String id) throws IOException {
		Transaction t = rc.multi();
		t.srem("id", id);
		t.del(id);
		t.zrem("avgscore", id);
		List<Object> lo = t.exec();
		t.close();
		if(lo != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean EditInfo(String id, Map<String, String> data) throws IOException {
		Transaction t = rc.multi();
		// Set：id无需编辑
		t.hmset(id, data);
		t.zadd("avgscore", new Double(data.get("avgscore")), id);
		List<Object> lo = t.exec();
		t.close();
		if(lo != null)
			return true;
		else
			return false;
	}

	@Override
	public Map<String, Map<String, String>> GetAllInfo(){
		Set<String> idSet = rc.smembers("id");
		System.out.println(idSet);
		Map<String, Map<String, String>> data = new HashMap<>();
		for(String s: idSet){
			Map<String, String> info = rc.hgetAll(s);
			data.put(s, info);
		}
		System.out.println(data); 
		return data;
	}

	@Override
	public Map<String, Map<String, String>> GetInfoById(String id){
		Map<String, String> info = rc.hgetAll(id);
		Map<String, Map<String, String>> data = new HashMap<>();
		data.put(id, info);
		return data;
	}

	@Override
	public Set<String> SortInfoByAvgScore(){
		Set<String> idSortedSet = rc.zrevrangeByScore("avgscore", "100", "0");
		System.out.println("Sorted: " + idSortedSet);
		return idSortedSet;
	}
	
	public Set<String> SortInfoById(){
		
		return null;
	}

}
