package com.kingdom.park.socket.service.util;

import net.sf.json.JSONArray;

/** 
 * 下发响应实体
 * @author 作者:yaozq 
 * @version 创建时间：2016-9-1 下午06:33:22 
 * 
 */

public class DownResult {
	
	
	/**
	 * 业务处理成功/失败结果
	 */
	private String result;
	
	/**
	 * 异常代码(如：80000000)
	 */
	private String error_code;
	
	/**
	 * 异常说明(如：成功)
	 */
	private String error_msg;
	
	/**
	 * 业务处理结果返回数据(Json)
	 */
	private JSONArray data;

	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String errorMsg) {
		error_msg = errorMsg;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String errorCode) {
		error_code = errorCode;
	}

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}
	
}
