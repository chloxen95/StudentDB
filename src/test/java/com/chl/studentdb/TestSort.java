package com.chl.studentdb;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.chl.studentdb.utils.RedisConnector;

import redis.clients.jedis.Jedis;

public class TestSort {
	Jedis rc = RedisConnector.GetRedisConnection();

	@Test
	public void testGetAllData() {
		System.out.println(GetAllData());
	}
	
	private Map<String, Integer> GetAllData(){
		Set<String> keys = rc.keys("*");
		Map<String, Integer> avgscores = new HashMap<>();
		for(String key: keys){
			String avgscore = rc.hget(key, "avgscore");
			avgscores.put(key, new Integer(avgscore));
		}
		return avgscores;
	}
	
	@Test
	public void testSort(){
//		Set<Entry<String, Integer>> avgscores =  GetAllData().entrySet();
//		Entry<String, Integer> a, b;
		sortMap(GetAllData());
		
	}
	
	 private void sortMap(Map<String, Integer> map){
	        //��ȡentrySet
	        Set<Map.Entry<String,Integer>> mapEntries = map.entrySet();
	        
	        System.out.println("Data in Redis: ");
	        for(Entry<String, Integer> entry : mapEntries){
	            System.out.println("key:" +entry.getKey()+"   value:"+entry.getValue() );
	        }
	        System.out.println();
	        
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
	        Map<String,Integer> linkMap = new LinkedHashMap<>();
	        for(Entry<String,Integer> newEntry :result){
	            linkMap.put(newEntry.getKey(), newEntry.getValue());            
	        }
	        System.out.println("Data Sorted: ");
	        //����entrySet()��������linkMap
	        for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
	            System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
	        }
	    }

}
