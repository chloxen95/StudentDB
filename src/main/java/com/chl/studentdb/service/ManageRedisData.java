package com.chl.studentdb.service;

import java.io.IOException;
import java.util.Map;

public interface ManageRedisData {
	
	/**
	 * ɾ���������ݣ��̶�10��
	 * @return ɾ������������ӦΪ10
	 * @throws IOException 
	 */
	public int DelLastData() throws IOException;
	
	/**
	 * �������ݣ�����������̶�50��
	 * @return �������������ӦΪ50
	 * @throws IOException 
	 */
	public int InsertGeneratedRandomData() throws IOException;
	
	/**
	 * �������ݣ��������
	 * @return ���ɵ�����
	 * @throws IOException 
	 */
	public Map<String, Map<String, String>> GenerateRandomData() throws IOException;
	
}
