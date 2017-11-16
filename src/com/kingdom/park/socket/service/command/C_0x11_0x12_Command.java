package com.kingdom.park.socket.service.command;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.kingdom.park.socket.service.codec.Coding;
import com.kingdom.park.socket.service.codec.ServiceDataBag;
import com.kingdom.park.socket.service.session.SessionContext;
import com.kingdom.park.socket.service.session.UserSessionWrapper;
import com.kingdom.park.socket.service.util.DownResult;
import com.kingdom.park.socket.service.util.JSONUtil;


/**
 * 心跳上传
 * @author YaoZhq
 * 第一步：校验授权码是否授权
 * 第二步：解析报文数据
 * 第三步：调用LBM上传流水
 * 第四步：下发数据至客户端
 * 第五步: 记录下发记录
 *
 */
public class C_0x11_0x12_Command extends CommandImpl {

	private static final long serialVersionUID = 4217540424946422657L;
	
	/**
	 * 报文解析
	 */
	@Override
	public Object decodeMessage(Object message) {
		try {
			ServiceDataBag bag = (ServiceDataBag) message;
			byte[] array=bag.getTextContentBytes();
			String json=new String(array,"utf-8");
			JSONObject jo=JSONObject.fromObject(json);
			if(jo==null||jo.isEmpty()){
				return null;
			}
			HashMap<String,String> mm=JSONUtil.SimpleJsonToHashMap(jo);
			array=null;
			json=null;
			return mm;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(SessionContext sessionContext, IoSession session, Object message,Boolean checkLicense) {
		if (message instanceof ServiceDataBag == false) {
			return null;
		}
		final ServiceDataBag bag = (ServiceDataBag) message;
		if (getUCmd().equals(bag.getBagCode())) {
			try{
				DownResult result=new DownResult();
				JSONArray ja=new  JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("downtime", "20170912000000");
				ja.add(jo);
				result.setResult(Result_Code_Y);
				result.setError_code(Error_Code_80000000);
				result.setError_msg(Error_Msg_80000000);
				result.setData(ja);
				HashMap<String,String> inflowParam=(HashMap<String,String>)decodeMessage(bag);
				System.out.println(inflowParam.toString());
				
				//下发下行数据
				JSONObject downResultJson=JSONObject.fromObject(result);
				byte[] array=(downResultJson==null?"":downResultJson.toString()).getBytes();
				bag.setDownData(downResultJson.toString());
				bag.setDownResult(result);
				bag.setTextContent(downResultJson.toString());
				bag.setTextContentBytes(array);
				bag.setTextLength(array.length);
				bag.setBagLength(array.length+47);
				bag.setBagCode(getLCmd());
				
				UserSessionWrapper user=sessionContext.getSessionBylincesKey(bag.getLicenseKey());
//				if(user==null||user.getSession()==null||user.getSession().isClosing()){
//					logger.info("===[C_0x11_0x12]Session丢失,下发数据失败：："+bag.getLicenseKey());
//					return null;
//				}
				bag.setUser(user);
				WriteFuture wf = sendCommand(session, bag);
				wf.addListener(new IoFutureListener<IoFuture>() {
					@Override
					public void operationComplete(IoFuture future) {
						String sendresult="0";//下发结果，失败
						if (future.isDone()) {
							//调用LBM记录成功日志
							logger.info("===[C_0x11_0x12]下发数据成功：："+bag.getBagCode()+"==授权码=="+bag.getLicenseKey()+"==下发数据=="+bag.getDownData());
							sendresult="1";//下发结果，成功
						}
						
					}
					
				});
				array=null;
				//System.gc();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}
	
	@Override
	public String getLCmd() {
		return getCodeString(L_0x12);
	}

	@Override
	public String getUCmd() {
		return getCodeString(U_0x11);
	}
	
	
	public static void main(String[] agrs){
		String s=getCodeString(L_0x30);
		System.out.println("++++++++++"+s);
		byte[] ss=Coding.hexStr2Bytes(getCodeString(L_0x30));
		System.out.println("ss===="+ss.length);
		byte[] s2=Coding.toByteArray(getCodeString(L_0x30));
		System.out.println("s2==="+s2.length);
	}
	
}
