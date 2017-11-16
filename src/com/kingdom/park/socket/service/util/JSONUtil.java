package com.kingdom.park.socket.service.util;

import java.util.HashMap;
import java.util.Iterator;
import net.sf.json.JSONObject;

/** 
 * Json工具类
 * @author 作者:yaozq 
 * @version 创建时间：2016-9-1 下午04:42:58 
 * 
 */

public class JSONUtil {

	
	/**
	 * 简单Json对象转HashMap<String,String>
	 * @param json 简单Json对象
	 * @return HashMap<JSON_key,Json_value>
	 */
	public static HashMap<String,String> SimpleJsonToHashMap(JSONObject json){
		try{
			if(json==null||json.isEmpty()){
				return null;
			}
			HashMap<String,String> result=new HashMap<String,String>();
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
	            String key = (String) iterator.next();
		        String value = json.getString(key);
		        result.put(key, value);
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
