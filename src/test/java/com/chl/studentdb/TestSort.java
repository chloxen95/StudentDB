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
	        //获取entrySet
	        Set<Map.Entry<String,Integer>> mapEntries = map.entrySet();
	        
	        System.out.println("Data in Redis: ");
	        for(Entry<String, Integer> entry : mapEntries){
	            System.out.println("key:" +entry.getKey()+"   value:"+entry.getValue() );
	        }
	        System.out.println();
	        
	        //使用链表来对集合进行排序，使用LinkedList，利于插入元素
	        List<Map.Entry<String, Integer>> result = new LinkedList<>(mapEntries);
	        //自定义比较器来比较链表中的元素
	        Collections.sort(result, new Comparator<Entry<String, Integer>>() {
	            //基于entry的值（Entry.getValue()），来排序链表
	            @Override
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2) {
	                // return o1.getValue().compareTo(o2.getValue()) ;
	            	return o2.getValue().compareTo(o1.getValue());
	            }
	        });
	        
	        //将排好序的存入到LinkedHashMap(可保持顺序)中，需要存储键和值信息对到新的映射中。
	        Map<String,Integer> linkMap = new LinkedHashMap<>();
	        for(Entry<String,Integer> newEntry :result){
	            linkMap.put(newEntry.getKey(), newEntry.getValue());            
	        }
	        System.out.println("Data Sorted: ");
	        //根据entrySet()方法遍历linkMap
	        for(Map.Entry<String, Integer> mapEntry : linkMap.entrySet()){
	            System.out.println("key:"+mapEntry.getKey()+"  value:"+mapEntry.getValue());
	        }
	    }

}
