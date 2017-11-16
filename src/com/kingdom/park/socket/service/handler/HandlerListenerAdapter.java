/**
 * 
 */
package com.kingdom.park.socket.service.handler;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.kingdom.park.socket.service.session.SessionContext;
import com.kingdom.park.socket.service.session.UserSessionWrapper;
import com.sun.istack.internal.logging.Logger;

/**
 * Handler适配器
 * @author YaoZhq
 */
public class HandlerListenerAdapter implements IHandlerListener {
	
	
	private static final Logger logger=Logger.getLogger(HandlerListenerAdapter.class);

	private static final long serialVersionUID = 7077210358423510025L;

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireExceptionCaught(com.kingdom.park.socket.SessionContext, org.apache.mina.core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void fireExceptionCaught(SessionContext sessionContext,
			IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireMessageReceived(com.kingdom.park.socket.SessionContext, org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void fireMessageReceived(SessionContext sessionContext,
			IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		//修改状态
//		UserSessionWrapper user=SessionStatusCache.getInstance().getUserSessionWrapperBySession(session);
//		logger.info("===[收到停车场数据]==更新session状态：：park_code"+user.getParkCode());
//		SessionStatusCache.getInstance().updateStatus(user, String.valueOf(System.currentTimeMillis()), false);
	}

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireSessionCreated(com.kingdom.park.socket.SessionContext, org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void fireSessionCreated(SessionContext sessionContext,
			IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireSessionIdle(com.kingdom.park.socket.SessionContext, org.apache.mina.core.session.IoSession, org.apache.mina.core.session.IdleStatus)
	 */
	@Override
	public void fireSessionIdle(SessionContext sessionContext,
			IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireSessionOpened(com.kingdom.park.socket.SessionContext, org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void fireSessionOpened(SessionContext sessionContext,
			IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kingdom.park.socket.IHandlerListener#fireSessionClosed(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public void fireSessionClosed(SessionContext sessionContext, IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
