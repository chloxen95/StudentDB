package com.chl.studentdb.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.chl.studentdb.service.ManageRedisData;
import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class ManageRedisDataImpl implements ManageRedisData {
	
	Jedis rc = RedisConnector.GetRedisConnection();
	
	{
		rc = RedisConnector.GetRedisConnection();
		if (!rc.exists("id")) { // �ж�List��id�Ƿ����
			rc.sadd("id", "2013220701000");
			rc.srem("id");
		}
		if (!rc.exists("avgscores")) { // �ж�Sorted Set��id�Ƿ����
			rc.zadd("avgscores", 0.0, "2013220701000");
			rc.zrem("avgscores", "2013220701000");
		}
	}
	
	public int DelLastData() throws IOException{
		Transaction t = rc.multi();
		int num = 10;
		Set<String> keys = rc.keys("*");
		if (keys.size() == 0 || keys.size() < num) {
			System.out.println("Failed");
			return 0;
		}

		// long count = rc.scard("id");
		long maxId = getNextId();

		for (int i = 0; i < num; i++) {
			t.del(maxId - i  - 1 + "");
			t.srem("id", maxId + "");
			t.del(maxId + "");
			t.zrem("avgscore", maxId + "");
			t.exec();
		}
		t.close();
		return num;
	}
	
	public int InsertGeneratedRandomData() throws IOException {
		int count = 50;
		long nextId = getNextId();
		for (int i = 0; i < count; i++){
			Transaction t = rc.multi();
			Map<String, String> detail = new HashMap<>();
			detail.put("name", getRandomString(4));
			detail.put("description", getRandomString(10));
			detail.put("birthday", getRandomDate());
			detail.put("avgscore", getRandomNumber(2));
			t.sadd("id", nextId + i + "");
			t.hmset(nextId + i + "", detail);
			t.zadd("avgscore", new Double(detail.get("avgscore")), nextId + i + "");
			t.exec();
			t.close();
		}
		return count;
	}

	public Map<String, Map<String, String>> GenerateRandomData() throws IOException {
		Map<String, Map<String, String>> data = new HashMap<>();
		Map<String, String> detail = new HashMap<>();
		detail.put("name", getRandomString(4));
		detail.put("description", getRandomString(10));
		detail.put("birthday", getRandomDate());
		detail.put("avgscore", getRandomNumber(2));
		data.put(getNextId() + "", detail);
		return data;
	}

	public long getNextId() throws IOException {
		long count = rc.scard("id");
		if(count == 0l){
			return 2013220701001l;
		}
		return 2013220701001l + count;
	}

	public String getRandomString(int length) {
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		// ����Ϊ����ѭ������
		for (int i = 0; i < length; ++i) {
			// ����0-61������
			int number = random.nextInt(62);
			// ������������ͨ��length�γ��ص�sb��
			sb.append(str.charAt(number));
		}
		// �����ص��ַ�ת�����ַ���
		return sb.toString();
	}

	public String getRandomNumber(int length) {
		Random random = new Random();
		int l = 9;
		for (int i = 1; i < length; i++) {
			l = l * 10 + 9;
		}
		return random.nextInt(l) + "";
	}

	public String getRandomDate() {
		Random random = new Random();
		int year = random.nextInt(18) + 1990;
		int month = random.nextInt(11) + 1;
		int day = random.nextInt(27) + 1;
		return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
	}

}
