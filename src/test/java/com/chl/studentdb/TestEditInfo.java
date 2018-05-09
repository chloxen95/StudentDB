package com.chl.studentdb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.chl.studentdb.service.StudentInfoEditor;
import com.chl.studentdb.service.impl.StudentInfoEditorImpl;

public class TestEditInfo {
	
	StudentInfoEditor sie  = new StudentInfoEditorImpl();
	Map<String, Map<String, String>> infomation = new HashMap<>();
	
	// @Before
	public void testBfore(){
		Map<String, String> stu1 = new HashMap<>();
		stu1.put("name", "aaa");
		stu1.put("birthday", "19950205");
		stu1.put("description", "aaa");
		stu1.put("avgscore", "99");
		infomation.put("1", stu1);
		Map<String, String> stu2 = new HashMap<>();
		stu2.put("name", "bbb");
		stu2.put("birthday", "20125522");
		stu2.put("description", "bbb");
		stu2.put("avgscore", "100");
		infomation.put("2", stu2);
	}

	@Test
	public void testInsert() {
		sie.InsertStudentInfo(infomation.get("1"));
		sie.InsertStudentInfo(infomation.get("2"));
	}
	
	@Test
	public void testDel(){
		sie.DeleteStudentInfo("1");
	}

}
