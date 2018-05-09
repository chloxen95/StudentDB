package com.chl.studentdb.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * ���ݵ���ɾ�Ĳ��������ȫʹ��Redis
 * @author chlox
 *
 */
public interface StudentInfoRedisEditor {

	/**
	 * ��������
	 * @return
	 * @throws IOException 
	 */
	public boolean InsertInfo(Map<String, String> data) throws IOException;
	
	/**
	 * ɾ������
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public boolean DeleteInfo(String id) throws IOException;
	
	/**
	 * �༭����
	 * @param id
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public boolean EditInfo(String id, Map<String, String> data) throws IOException;
	
	/**
	 * ��ȡ��������
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GetAllInfo();
	
	/**
	 * ����ID��ȡ����һ������
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GetInfoById(String id);
	
	/**
	 * ����ƽ��������
	 * @return
	 * @throws IOException 
	 */
	public Set<String> SortInfoByAvgScore();
	
	
}
