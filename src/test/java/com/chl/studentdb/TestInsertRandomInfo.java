package com.chl.studentdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.chl.studentdb.service.StudentInfoEditor;
import com.chl.studentdb.service.impl.StudentInfoEditorImpl;
import com.chl.studentdb.utils.MysqlConnection;
import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;

public class TestInsertRandomInfo {
	
	StudentInfoEditor sie = new StudentInfoEditorImpl();

	@Test
	public void testGetData() throws SQLException {
		ResultSet rs = MysqlConnection.selectSql("select * from student");
		int i = 0;
		while (rs.next()) {
			i++;
		}
		System.out.println(rs.getMetaData().getColumnCount());
		System.out.println(i);
	}

	@Test
	public void testGetInfo() throws SQLException {
		String[] colName = { "name", "birthday", "description", "avgscore" };
		ResultSet rs = MysqlConnection.selectSql("select * from student");
		Map<String, Map<String, String>> infos = new HashMap<>();
		while (rs.next()) {
			Map<String, String> info = new HashMap<>();
			for (int i = 0; i < 4; i++) {
				info.put(colName[i], rs.getString(colName[i]));
			}
			infos.put(rs.getString("id"), info);
		}
		System.out.println(infos);
	}

	@Test
	public void testInsertToRedisFromMysql() throws SQLException {
		String[] colName = { "name", "birthday", "description", "avgscore" };
		ResultSet rs = MysqlConnection.selectSql("select * from student");
		while (rs.next()) {
			Map<String, String> info = new HashMap<>();
			for (int i = 0; i < 4; i++) {
				info.put(colName[i], rs.getString(colName[i]));
			}
			sie.InsertStudentInfo(info);
		}
	}

	@Test
	public void testGetRandom() {
		System.out.println("Get next id: " + getNextId());
		System.out.println("Get random String lenght 2: " + getRandomString(2));
		System.out.println("Get random String lenght 8: " + getRandomString(8));
		System.out.println("Get random number length 2: " + getRandomNumber(2));
		System.out.println("Get random date: " + getRandomDate());
	}

	@Test
	public void testGetRandomData() {
		Map<String, Map<String, String>> data = new HashMap<>();
		Map<String, String> detail = new HashMap<>();
		detail.put("name", getRandomString(4));
		detail.put("description", getRandomString(10));
		detail.put("birthday", getRandomDate());
		detail.put("avgscore", getRandomNumber(2));
		data.put(getNextId(), detail);
		System.out.println(data);
	}

	@Test
	public void testInsertRandomData() {
		for (int i = 0; i < 50; i++){
			Map<String, String> detail = new HashMap<>();
			detail.put("name", getRandomString(4));
			detail.put("description", getRandomString(10));
			detail.put("birthday", getRandomDate());
			detail.put("avgscore", getRandomNumber(2));
			sie.InsertStudentInfoWithId(getNextId(), detail);
		}
	}

	private Map<String, Map<String, String>> GenerateRandomData() {
		Map<String, Map<String, String>> data = new HashMap<>();
		Map<String, String> detail = new HashMap<>();
		detail.put("name", getRandomString(4));
		detail.put("description", getRandomString(10));
		detail.put("birthday", getRandomDate());
		detail.put("avgscore", getRandomNumber(2));
		data.put(getNextId(), detail);
		return data;
	}

	private String getNextId() {
		Jedis rc = RedisConnector.GetRedisConnection();
		Set<String> keys = rc.keys("*");
		long maxId = 0L;
		for (String key : keys) {
			if (new Long(key) > maxId) {
				maxId = new Long(key);
			}
		}
		return maxId + 1 + "";
	}

	private String getRandomString(int length) {
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		// 长度为几就循环几次
		for (int i = 0; i < length; ++i) {
			// 产生0-61的数字
			int number = random.nextInt(62);
			// 将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		// 将承载的字符转换成字符串
		return sb.toString();
	}

	private String getRandomNumber(int length) {
		Random random = new Random();
		int l = 9;
		for (int i = 1; i < length; i++) {
			l = l * 10 + 9;
		}
		return random.nextInt(l) + "";
	}

	private String getRandomDate() {
		Random random = new Random();
		int year = random.nextInt(18) + 1990;
		int month = random.nextInt(12);
		int day = random.nextInt(28);
		return year + "-" + (month < 10 ? "0" : "") + month + "-" + day;
	}

	@Test
	public void test() {
		Map<String, String> a = new HashMap<>();
		// Map<String, String> b = new IdentityHashMap<>();
		a.put("aa", "aa");
		a.put("aa", "aa");
		System.out.println(a.containsKey("aa"));
		System.out.println(a);
	}

}
