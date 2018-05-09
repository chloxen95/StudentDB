package com.chl.studentdb.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.chl.studentdb.service.StudentInfoEditor;
import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;

@Deprecated
public class StudentInfoEditorImpl implements StudentInfoEditor {

	Jedis rc = RedisConnector.GetRedisConnection();

	@Override
	public void InsertStudentInfo(Map<String, String> infomation) {
		// TODO Auto-generated method stub
		Set<String> keys = rc.keys("*");
		long maxId = 0L;
		for (String key : keys) {
			if (new Long(key) > maxId) {
				maxId = new Long(key);
			}
		}
		maxId += 1;
		rc.hmset(maxId + "", infomation);
	}

	public void InsertStudentInfoWithId(String id, Map<String, String> infomation) {
		// TODO Auto-generated method stub
		rc.hmset(id, infomation);
	}

	@Override
	public void DeleteStudentInfo(String id) {
		// TODO Auto-generated method stub
		rc.del(id);
	}

	@Override
	public void EditStudentInfo(Map<String, Map<String, String>> infomation) {
		// TODO Auto-generated method stub
		for (Entry<String, Map<String, String>> info : infomation.entrySet()) {
			rc.hmset(info.getKey(), info.getValue());
		}
	}

	@Override
	public Map<String, Map<String, String>> GetStudentInfo() {
		// TODO Auto-generated method stub
		Set<String> ids = rc.keys("*");
		Map<String, Map<String, String>> infomation = new HashMap<>();
		for (String id : ids) {
			Map<String, String> info = rc.hgetAll(id);
			infomation.put(id, info);
		}
		return infomation;
	}

	@Override
	public List<String> SortInfoByAvgscore() {
		// TODO Auto-generated method stub
		Set<String> keys = rc.keys("*");
		Map<String, Integer> avgscores = new HashMap<>();
		for(String key: keys){
			String avgscore = rc.hget(key, "avgscore");
			avgscores.put(key, new Integer(avgscore));
		}
		Set<Map.Entry<String,Integer>> mapEntries = avgscores.entrySet();
                
        //ʹ���������Լ��Ͻ�������ʹ��LinkedList�����ڲ���Ԫ��
        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
        //�Զ���Ƚ������Ƚ������е�Ԫ��
        Collections.sort(result, new Comparator<Entry<String, Integer>>() {
            //����entry��ֵ��Entry.getValue()��������������
            @Override
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2) {
                // return o1.getValue().compareTo(o2.getValue()) ;
            	return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        //���ź���Ĵ��뵽LinkedHashMap(�ɱ���˳��)�У���Ҫ�洢����ֵ��Ϣ�Ե��µ�ӳ���С�
        List<String> idSortedByAvgscore = new LinkedList<>();
        for(Entry<String,Integer> newEntry :result){
        	idSortedByAvgscore.add(newEntry.getKey());
        }
		return idSortedByAvgscore;
	}


}
