/**
 * 
 */
package com.kingdom.park.socket.service.handler;

import java.io.Serializable;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.kingdom.park.socket.service.session.SessionContext;

/**
 * 监听Sessioin的状态并做出相应的处理
 * @author YaoZhq
 */
public interface IHandlerListener extends Serializable {

	public void fireSessionCreated(SessionContext sessionContext, IoSession session) throws Exception;

	public void fireSessionOpened(SessionContext sessionContext, IoSession session) throws Exception;

	public void fireExceptionCaught(SessionContext sessionContext, IoSession session) throws Exception;

	public void fireSessionIdle(SessionContext sessionContext, IoSession session, IdleStatus status) throws Exception;

	public void fireMessageReceived(SessionContext sessionContext, IoSession session, Object message) throws Exception;
	
	public void fireSessionClosed(SessionContext sessionContext, IoSession session) throws Exception;
}
