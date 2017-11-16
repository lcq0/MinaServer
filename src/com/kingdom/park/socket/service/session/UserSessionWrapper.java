/**
 * 
 */
package com.kingdom.park.socket.service.session;

import java.util.HashMap;

import org.apache.mina.core.session.IoSession;


/**
 * 停车场用户连接信息
 * @author YaoZhq
 * 
 * 
 * ·················数据帧格式····················································
 * 包头标识	协议指令码	包长		授权码(license)	文本长度		文本内容 		校验码	包尾标识
 *	2		   1		 4		 32		 		  4			  N			  2	    	2
 * ··············································································
 * session中license校验规则：
 * 1.从数据包头中取出授权码存放至UserSessionWrapper的license字段中，如果是平台接入，则包头中license为平台license；
 * 	 如果是停车场接入，则包头中license为停车场license；
 * 2.如果[文本内容]中的license与包头中的一致，则为停车场接入，且上传的数据为该停车场数据，；
 * 3.如果[文本内容]中的license与包头中的不一致，则为平台接入，且上传的数据为该平台下属的停车场数据，
 *   [文本内容]中的license为停车场的license，在每一帧数据上传上来时，将[文本内容]中的license添加至licenseMap中，
 *   以保留平台下属的停车场与改session的对应关系。
 * 
 * 
 * 
 * 
 *
 */
public class UserSessionWrapper extends SessionWrapper {
	
	/**
	 * 停车场ParkCode
	 */
	private String parkCode;
	
	/**
	 * 停车场CustId
	 */
	private String custId;
	
	/**
	 * 停车场(或平台)授权码
	 */
	private String license;
	
	/**
	 * 停车场名称
	 */
	private String parkName;
	
	/**
	 * 授权码有效期开始时间(格式yyyyMMddHHmmss 如：20160101230059)
	 */
	private String licenseStartTime;
	
	/**
	 * 授权码有效期结束时间(格式yyyyMMddHHmmss 如：20160101230059)
	 */
	private String licenseEndTime;
	
	
	/**
	 * 平台下属停车场keyMap集合(若为停车场接入，则此集合为空)
	 */
	private HashMap<String, String> licenseMap=new HashMap<String, String>();
	

	public UserSessionWrapper(IoSession session) {
		super(session);
	}


	public String getParkCode() {
		return parkCode;
	}


	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}


	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getLicense() {
		return license;
	}


	public void setLicense(String license) {
		this.license = license;
	}


	public String getParkName() {
		return parkName;
	}


	public void setParkName(String parkName) {
		this.parkName = parkName;
	}


	public String getLicenseStartTime() {
		return licenseStartTime;
	}


	public void setLicenseStartTime(String licenseStartTime) {
		this.licenseStartTime = licenseStartTime;
	}


	public String getLicenseEndTime() {
		return licenseEndTime;
	}


	public void setLicenseEndTime(String licenseEndTime) {
		this.licenseEndTime = licenseEndTime;
	}


	public HashMap<String, String> getLicenseMap() {
		return licenseMap;
	}


	public void setLicenseMap(HashMap<String, String> licenseMap) {
		this.licenseMap = licenseMap;
	}
	
	
	
}
