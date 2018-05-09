package com.chl.studentdb.controllor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chl.studentdb.service.StudentInfoEditor;
import com.chl.studentdb.service.impl.StudentInfoEditorImpl;

@Controller
@Deprecated
public class MainController {
	
	StudentInfoEditor sie = new StudentInfoEditorImpl();
	
	@RequestMapping(value = "/d", method = RequestMethod.GET)
	public String JumpToIndex() {
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllInfo", method = RequestMethod.GET)
	public Map<String, Map<String, String>> GetAllStudentInfo() {
		Map<String, Map<String, String>> allInfo = sie.GetStudentInfo();
		System.out.println(new Date() + " Info : Get All Student Info: " + allInfo.size());
		return allInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delInfo", method = RequestMethod.POST)
	public void DeleteStudentInfo(String id) {
		System.out.println(new Date() + " Info : Delete Student Info, ID: " + id);
		sie.DeleteStudentInfo(id);
	}

	@ResponseBody
	@RequestMapping(value = "/insertInfo", method = RequestMethod.POST)
	public void InsertStudentInfo(String name, String birthday, String description, String avgscore) {
		System.out.println(new Date() + " Info : Insert Student Info, detail: " + name + ", "
				+ birthday + ", " + description + ", " + avgscore);
		Map<String, String> data = new HashMap<>();
		data.put("name", name);
		data.put("birthday", birthday);
		data.put("description", description);
		data.put("avgscore", avgscore);
		sie.InsertStudentInfo(data);
	}

	@ResponseBody
	@RequestMapping(value = "/editInfo", method = RequestMethod.POST)
	public void EditStudentInfo(String id, String name, String birthday, String description, String avgscore) {
		System.out.println(new Date() + " Info : Edit Student Info, detail: " + id + ", " 
				+ name + ", " + birthday + ", " + description + ", " + avgscore);
		Map<String, String> info = new HashMap<>();
		info.put("name", name);
		info.put("birthday", birthday);
		info.put("description", description);
		info.put("avgscore", avgscore);
		Map<String, Map<String, String>> data = new HashMap<>();
		data.put(id, info);
		sie.EditStudentInfo(data);
	}
	
	@ResponseBody
	@RequestMapping(value = "/sortByAvgscore", method = RequestMethod.GET)
	public List<String> SortInfoByAvgscore(){
		System.out.println(new Date() + " Sorted");
		return sie.SortInfoByAvgscore();
	}

}
