package com.chl.studentdb.service;

import java.util.List;
import java.util.Map;

/**
 * Insert, Delete, Edit, Find
 * @author chloxen95
 *
 */
@Deprecated
public interface StudentInfoEditor {
	
	/**
	 * Insert student info
	 * @param name
	 * @param birthday
	 * @param description
	 * @param avgscore
	 */
	public void InsertStudentInfo(Map<String, String> infomation);	
	
	/**
	 * Insert student info with id
	 * @param id
	 * @param infomation
	 */
	public void InsertStudentInfoWithId(String id, Map<String, String> infomation);
	
	/**
	 * Delete student info
	 * @param id
	 */
	public void DeleteStudentInfo(String id);
	
	/**
	 * Edit student info
	 * @param id
	 * @param field
	 * @param value
	 */
	public void EditStudentInfo(Map<String, Map<String, String>> infomation);
	
	/**
	 * Get student info
	 */
	public Map<String, Map<String, String>> GetStudentInfo();
	
	/**
	 * Sort Info By Avgscore
	 * @return
	 */
	public List<String> SortInfoByAvgscore();
}
