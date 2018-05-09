package com.chl.studentdb.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 数据的增删改查和排序，完全使用Redis
 * @author chlox
 *
 */
public interface StudentInfoRedisEditor {

	/**
	 * 插入数据
	 * @return
	 * @throws IOException 
	 */
	public boolean InsertInfo(Map<String, String> data) throws IOException;
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public boolean DeleteInfo(String id) throws IOException;
	
	/**
	 * 编辑数据
	 * @param id
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public boolean EditInfo(String id, Map<String, String> data) throws IOException;
	
	/**
	 * 获取所有数据
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GetAllInfo();
	
	/**
	 * 根据ID获取其中一条数据
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GetInfoById(String id);
	
	/**
	 * 根据平均分排序
	 * @return
	 * @throws IOException 
	 */
	public Set<String> SortInfoByAvgScore();
	
	
}
