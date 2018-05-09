package com.chl.studentdb.service;

import java.io.IOException;
import java.util.Map;

public interface ManageRedisData {
	
	/**
	 * 删除最后的数据，固定10条
	 * @return 删除的数据量，应为10
	 * @throws IOException 
	 */
	public int DelLastData() throws IOException;
	
	/**
	 * 插入数据，内容随机，固定50条
	 * @return 插入的数据量，应为50
	 * @throws IOException 
	 */
	public int InsertGeneratedRandomData() throws IOException;
	
	/**
	 * 生成数据，内容随机
	 * @return 生成的数据
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GenerateRandomData() throws IOException;
	
}
