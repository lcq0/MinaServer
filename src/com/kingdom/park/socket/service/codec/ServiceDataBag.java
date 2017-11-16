package com.kingdom.park.socket.service.codec;

import java.io.Serializable;

import com.kingdom.park.socket.service.session.UserSessionWrapper;
import com.kingdom.park.socket.service.util.DownResult;

public class ServiceDataBag extends DataBag implements Serializable {
	private static final long serialVersionUID = -4262821712872027511L;
	
	//下发数据
	/**
	 * 业务处理结果实体
	 */
	private DownResult downResult;
	
	/**
	 * 下发数据包
	 */
	private String downData;
	
	/**
	 * 要下发的客户端信息
	 */
	private UserSessionWrapper user;
	
	
	
	public DownResult getDownResult() {
		return downResult;
	}

	public void setDownResult(DownResult downResult) {
		this.downResult = downResult;
	}

	public String getDownData() {
		return downData;
	}

	public void setDownData(String downData) {
		this.downData = downData;
	}

	public UserSessionWrapper getUser() {
		return user;
	}

	public void setUser(UserSessionWrapper user) {
		this.user = user;
	}

}
