/**
 * 
 */
package com.kingdom.park.socket.service.util;


/**
 * 协议编码常量类
 * @author YaoZhq
 *
 */
public interface IConstants {
	
	
	/**
	 * 分隔符
	 */
	public static final String SPLITCHAR = "|";
	
	/**
	 * 冒号
	 */
	public static final String COLON = ":"; 
	
	/**
	 * 逗号
	 */
	public static final String COMMA =",";
	
	/**
	 * FF******FF 8字节替代符号
	 */
	public static final String FF_CHAR="FFFFFFFFFFFFFF";
	
	/**
	 * 左斜杠
	 */
	public static final String LEFTSLASH="/";
	
	/**
	 * 包尾
	 */
	 public static final String CRLF = "\r\n";
	 
	
	 /**
	  * 默认的lincesKey
	  * MD5加密的(ThirdParkServer)
	  */
	 public static final String DefaultLincesKey="f5b52501092e33deb74f7a7751b4f6a3";
	 
	 /**
	  * 资产变更同步下发方式:定时器任务扫描
	  */
	 public static final String AssetChangeSend_type_1="1";
	 
	 /**
	  * 资产变更同步下发方式：BP主动 请求
	  */
	 public static final String AssetChangeSend_type_2="2";
	 
	 /**
	  * 自助缴费结果同步下发方式:定时器任务扫描
	  */
	 public static final String SelfPaySend_type_1="1";
	 
	 /**
	  * 自助缴费结果同步下发方式：BP主动 请求
	  */
	 public static final String SelfPaySend_type_2="2";
	 
	 /**
	  * 月卡变更同步下发方式:定时器任务扫描
	  */
	 public static final String MemberCardSend_type_1="1";
	 /**
	  * 月卡变更同步下发方式: BP主动请求
	  */
	 public static final String MemberCardSend_type_2="2";
	/**********************状态协议**************************/
	
	/**转义符**/
	public static final byte d_0x24 = 0x24;
	
	/**协议头**/
	public static final short s_0x2424=0x2424;
	
	/**转义符**/
	public static final byte d_0x0d = 0x0d;
	
	/**转义符**/
	public static final byte d_0x0a = 0x0a;
	
	/**协议尾**/
	public static final short s_0x0d0a=0x0d0a;

	/**********************指令协议**************************/
	
	//上行
	/**
	 * 授权码鉴权
	 */
	public static final byte U_0x01=0x01;
	
	
	/**
	 * 心跳上传
	 */
	public static final byte U_0x11=0x11;
	
	/**
	 * 心跳应答
	 */
	public static final byte L_0x12=0x12;
	
	
	/**
	 * 车辆进场上报
	 */
	public static final byte U_0x21=0x21;
	
	/**
	 * 车辆进场上报应答
	 */
	public static final byte L_0x22=0x22;
	
	/**
	 * 车辆出场
	 */
	public static final byte U_0x23=0x23;
	
	/**
	 * 车辆出场应答
	 */
	public static final byte L_0x24=0x24;
	
	
	/**
	 * 提交日报表
	 */
	public static final byte U_0x25=0x25;
	
	/**
	 * 提交日报表应答
	 */
	public static final byte L_0x26=0x26;
	
	/**
	 * 查询车辆资产
	 */
	public static final byte U_0x27=0x27;
	
	/**
	 * 查询车辆资产应答
	 */
	public static final byte L_0x28=0x28;
	
	/**
	 * 上传并支付订单
	 */
	public static final byte U_0x29=0x29;
	
	/**
	 * 上传并支付订单应答
	 */
	public static final byte L_0x30=0x30;
	
	/**
	 * 更新入场图片路径
	 */
	public static final byte U_0x31=0x31;
	
	/**
	 * 更新入场图片路径应答
	 */
	public static final byte L_0x32=0x32;
	
	/**
	 * 查询车辆月卡会员
	 */
	public static final byte U_0x33=0x33;
	/**
	 * 查询车辆月卡会员应答
	 */
	public static final byte L_0x34=0x34;
	/**
	 * 查询停车场月卡会员
	 */
	public static final byte U_0x35=0x35;
	/**
	 * 查询停车场月卡会员应答
	 */
	public static final byte L_0x36=0x36;
	//下行
	/**
	 * 同步会员资产
	 */
	public static final byte L_0x51=0x51;
	
	/**
	 * 同步会员资产应答
	 */
	public static final byte U_0x52=0x52;
	
	/**
	 * 同步车牌绑定
	 */
	public static final byte L_0x53=0x53;
	
	/**
	 * 同步车牌绑定应答
	 */
	public static final byte U_0x54=0x54;
	
	/**
	 * 同步自助缴费
	 */
	public static final byte L_0x55=0x55;
	
	/**
	 * 同步自助缴费应答
	 */
	public static final byte U_0x56=0x56;
	
	/**
	 * 同步预约订单
	 */
	public static final byte L_0x57=0x57;
	
	/**
	 * 同步预约订单应答
	 */
	public static final byte U_0x58=0x58;
	
	
	/**
	 * 同步停车费用
	 */
	public static final byte L_0x59=0x59;
	
	
	/**
	 * 同步停车费用应答
	 */
	public static final byte U_0x60=0x60;
	

	/**
	 * 同步月卡变更
	 */
	public static final byte L_0x61=0x61;
	
	/**
	 * 同步月卡变更应答
	 */
	public static final byte U_0x62=0x62;
	
	/**********************指令协议**************************/
	
	
	/**********************业务处理结果**************************/
	/**成功*/
	public static final String Result_Code_Y="Y";
	/**失败*/
	public static final String Result_Code_N="N";
	
	public static final String Result_Code_1="1";
	
	public static final String Result_Code_0="0";
	
	/**********************业务处理结果**************************/

	/**********************异常错误代码**************************/
	public static final String Error_Code_80000000="80000000";
	public static final String Error_Msg_80000000="成功";
	
	public static final String Error_Code_80000001="80000001";
	public static final String Error_Msg_80000001="系统异常";
	
	public static final String Error_Code_80000002="80000002";
	public static final String Error_Msg_80000002="数据解析异常";
	
	public static final String Error_Code_80000003="80000003";
	public static final String Error_Msg_80000003="数据包体格式不正确";
	
	public static final String Error_Code_80000004="80000004";
	public static final String Error_Msg_80000004="请求未授权";
	
	
	//停车场流水上传
	public static final String Error_Code_80002001="80002001";
	public static final String Error_Msg_80002001="";
	
	/**********************异常错误代码**************************/

	
	
	
}
