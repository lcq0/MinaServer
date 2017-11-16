/**
 * 
 */
package com.kingdom.park.socket.service.handler;

import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.kingdom.park.socket.service.command.ICommand;
import com.kingdom.park.socket.service.session.SessionContext;
import com.kingdom.park.socket.service.session.SessionWrapper;
import com.kingdom.park.socket.service.util.IConstants;
import com.sun.istack.internal.logging.Logger;


/**
 * 
 * 处理所有接收到的消息，并分发到各个command来处理
 * 如果添加了监听器，可以在Session状态发生变化后再进一步处理
 * @author YaoZhq
 */
public class ParkSocketHandler extends IoHandlerAdapter {
	
	private static final Logger logger=Logger.getLogger(ParkSocketHandler.class);
		
	protected SessionContext sessionContext;
	
	private List<ICommand> commands = new ArrayList<ICommand>();
	private List<IHandlerListener> handlerListeners = new ArrayList<IHandlerListener>();
	
	/**
	 * 授权码License校验Command
	 */
	private ICommand cmd = null;

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("===[新增远程连接]：："+session.getRemoteAddress());
		super.sessionCreated(session);
		//sessionContext.addSession(createSessionWrapper(session));
		for (IHandlerListener h : handlerListeners) {
			h.fireSessionCreated(sessionContext, session);
		}
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {		
		super.sessionOpened(session);
		for (IHandlerListener h : handlerListeners) {
			h.fireSessionOpened(sessionContext, session);
		}
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.info("===[连接异常/断开]：："+session.getRemoteAddress());
		super.exceptionCaught(session, cause);
		for (IHandlerListener h : handlerListeners) {
			h.fireExceptionCaught(sessionContext, session);
		}
		sessionContext.removeSession(session);
		session.close(false);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		for (IHandlerListener h : handlerListeners) {
			h.fireSessionIdle(sessionContext, session, status);
		}
	}
	
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		boolean checkLicense=false;
		String s =Integer.toString(IConstants.U_0x11,16);
		//第一步：授权码License校验Command赋值
		if(cmd == null) {
			if (s.length() == 1) {
				s = "0" + s;
			}
			for (ICommand command : commands) {
				System.out.println("comcand:"+command.getUCmd());
				cmd = command;
				if(s.equals(command.getUCmd())){
					cmd = command;
				}
			}
		}
		
		//第二步：校验License是否授权,每帧数据上来都需要校验此步骤
		Object checkResult=cmd.execute(sessionContext, session, message,false);
		if(checkResult!=null){
			checkLicense=(Boolean)checkResult;
		}
		//第三步：分发到各个业务Command	
		for (ICommand command : commands) {
			//去除授权码校验
			if(!s.equals(command.getUCmd())){
				command.execute(sessionContext, session, message,checkLicense);
			}
		}	
			
		//第四步：日志记录，更新session缓存
		for (IHandlerListener h : handlerListeners) {
			h.fireMessageReceived(sessionContext, session, message);
		}
	}

	/**
	 * 广播消息
	 * 
	 * @param message
	 */
	public void broadcast(String message) {
		synchronized (sessionContext) {
			for (SessionWrapper session : sessionContext.getSessions()) {
				if (session.isConnected()) {
					session.write("广播消息：" + message); //$NON-NLS-1$
				}
			}
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		for (IHandlerListener h : handlerListeners) {
			h.fireSessionClosed(sessionContext, session);
		}
		sessionContext.removeSession(session);
//		SessionStatusCache.getInstance().removeStatus(session);
		//session.close(false);
		logger.info("===[连接关闭]：："+session.getRemoteAddress());
	}

	protected SessionWrapper createSessionWrapper(IoSession session) {
		SessionWrapper sw = new SessionWrapper(session);
		return sw;
	}
	
	public List<ICommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ICommand> commands) {
		this.commands = commands;
	}
	
	public void addHandlerListeners(IHandlerListener handlerListener) {
		this.handlerListeners.add(handlerListener);
	}
	
	public void removeHandlerListeners(IHandlerListener handlerListener) {
		this.handlerListeners.remove(handlerListener);
	}

	public List<IHandlerListener> getHandlerListeners() {
		return handlerListeners;
	}

	public void setHandlerListeners(List<IHandlerListener> handlerListeners) {
		this.handlerListeners = handlerListeners;
	}

	public SessionContext getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = SessionContext.getInstance();
	}
	
}
