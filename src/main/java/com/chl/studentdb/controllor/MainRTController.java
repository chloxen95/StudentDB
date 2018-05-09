package com.chl.studentdb.controllor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chl.studentdb.service.StudentInfoRedisEditor;
import com.chl.studentdb.service.impl.StudentInfoRedisEditorImpl;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class MainRTController {

	StudentInfoRedisEditor sie = new StudentInfoRedisEditorImpl();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String JumpToIndex() {
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Map<String, Map<String, String>> GetAllStudentInfo(){
		Map<String, Map<String, String>> allInfo = sie.GetAllInfo();
		System.out.println(new Date() + " Info : Get All Student Info: " + allInfo.size());
		return allInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public void DeleteStudentInfo(String id) throws IOException {
		System.out.println(new Date() + " Info : Delete Student Info, ID: " + id);
		sie.DeleteInfo(id);
	}

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void InsertStudentInfo(String name, String birthday, String description, String avgscore) throws IOException {
		System.out.println(new Date() + " Info : Insert Student Info, detail: " + name + ", "
				+ birthday + ", " + description + ", " + avgscore);
		Map<String, String> data = new HashMap<>();
		data.put("name", name);
		data.put("birthday", birthday);
		data.put("description", description);
		data.put("avgscore", avgscore);
		sie.InsertInfo(data);
	}

	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public void EditStudentInfo(String id, String name, String birthday, String description, String avgscore) throws IOException {
		System.out.println(new Date() + " Info : Edit Student Info, detail: " + id + ", " 
				+ name + ", " + birthday + ", " + description + ", " + avgscore);
		Map<String, String> info = new HashMap<>();
		info.put("name", name);
		info.put("birthday", birthday);
		info.put("description", description);
		info.put("avgscore", avgscore);
		sie.EditInfo(id, info);
	}
	
	@ResponseBody
	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	public Set<String> SortInfoByAvgscore(){
		System.out.println(new Date() + " Sorted");
		return sie.SortInfoByAvgScore();
	}
}
