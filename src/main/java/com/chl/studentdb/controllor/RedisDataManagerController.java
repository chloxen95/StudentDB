package com.chl.studentdb.controllor;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chl.studentdb.service.ManageRedisData;
import com.chl.studentdb.service.impl.ManageRedisDataImpl;
import com.chl.studentdb.utils.Md5Encoder;
import com.chl.studentdb.utils.ReadProperties;

@Controller
public class RedisDataManagerController {

	ManageRedisData mrd = new ManageRedisDataImpl();

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String JumpToInsertRandom(){
		return "manage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/manager/generate", method = RequestMethod.POST)
	public String GenerateRandomData(String pw) throws IOException{
		String password = ReadProperties.getKey("password");
		if(password.equals(Md5Encoder.md5Password(pw))){
			return mrd.InsertGeneratedRandomData() + "";
		}
		else{
			return "failed";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/manager/delete", method = RequestMethod.POST)
	public String DeleteLastData(String pw) throws IOException{
		String password = ReadProperties.getKey("password");
		if(password.equals(Md5Encoder.md5Password(pw))){
			return mrd.DelLastData() + "";
		}
		else{
			return "failed";
		}
	}
	
}
