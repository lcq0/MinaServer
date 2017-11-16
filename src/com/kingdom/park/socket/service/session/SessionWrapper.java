package com.kingdom.park.socket.service.session;

import java.net.SocketAddress;
import java.util.Set;

import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.core.write.WriteRequestQueue;


/**
 * Session的包装类
 * @author YaoZhq
 */
public class SessionWrapper {
	private IoSession session;
	private long sessionId;
	/** IP地址 **/
	private String ip;
	/** 端口 **/
	private int port;
	
	public SessionWrapper(IoSession session) {
		this.session = session;
		this.sessionId = session.getId();
		initAddress();
	}
	
	private void initAddress() {
		String[] ads = getAddress();
		if (ads.length == 2) {
			this.ip = ads[0].trim();
			this.port = Integer.parseInt(ads[1].trim());
		}
	}

	public String[] getAddress() {
		String address = session.getRemoteAddress() == null ? ""
				: session.getRemoteAddress().toString();
		address = address.replaceAll("/", ""); //$NON-NLS-1$//$NON-NLS-2$
		return address.split(":");
	}
	//自己的方法-----------------------------------------------
	public boolean sessionEquals(long sessionId) {
		return this.sessionId == sessionId;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getSessionName() {
		return "默认用户";
	}
	
	public Object getUserObject() {
		return this;
	}
	//代理方法-------------------------------------------------
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public CloseFuture close(boolean immediately) {
		return session.close(immediately);
	}
	public boolean containsAttribute(Object key) {
		return session.containsAttribute(key);
	}
	public Object getAttribute(Object key, Object defaultValue) {
		return session.getAttribute(key, defaultValue);
	}
	public Object getAttribute(Object key) {
		return session.getAttribute(key);
	}
	public Set<Object> getAttributeKeys() {
		return session.getAttributeKeys();
	}
	public int getBothIdleCount() {
		return session.getBothIdleCount();
	}
	public CloseFuture getCloseFuture() {
		return session.getCloseFuture();
	}
	public IoSessionConfig getConfig() {
		return session.getConfig();
	}
	public long getCreationTime() {
		return session.getCreationTime();
	}
	public Object getCurrentWriteMessage() {
		return session.getCurrentWriteMessage();
	}
	public WriteRequest getCurrentWriteRequest() {
		return session.getCurrentWriteRequest();
	}
	public IoFilterChain getFilterChain() {
		return session.getFilterChain();
	}
	public IoHandler getHandler() {
		return session.getHandler();
	}
	public long getId() {
		return session.getId();
	}
	public int getIdleCount(IdleStatus status) {
		return session.getIdleCount(status);
	}
	public long getLastBothIdleTime() {
		return session.getLastBothIdleTime();
	}
	public long getLastIdleTime(IdleStatus status) {
		return session.getLastIdleTime(status);
	}
	public long getLastIoTime() {
		return session.getLastIoTime();
	}
	public long getLastReaderIdleTime() {
		return session.getLastReaderIdleTime();
	}
	public long getLastReadTime() {
		return session.getLastReadTime();
	}
	public long getLastWriterIdleTime() {
		return session.getLastWriterIdleTime();
	}
	public long getLastWriteTime() {
		return session.getLastWriteTime();
	}
	public SocketAddress getLocalAddress() {
		return session.getLocalAddress();
	}
	public long getReadBytes() {
		return session.getReadBytes();
	}
	public double getReadBytesThroughput() {
		return session.getReadBytesThroughput();
	}
	public int getReaderIdleCount() {
		return session.getReaderIdleCount();
	}
	public long getReadMessages() {
		return session.getReadMessages();
	}
	public double getReadMessagesThroughput() {
		return session.getReadMessagesThroughput();
	}
	public SocketAddress getRemoteAddress() {
		return session.getRemoteAddress();
	}
	public long getScheduledWriteBytes() {
		return session.getScheduledWriteBytes();
	}
	public int getScheduledWriteMessages() {
		return session.getScheduledWriteMessages();
	}
	public IoService getService() {
		return session.getService();
	}
	public SocketAddress getServiceAddress() {
		return session.getServiceAddress();
	}
	public TransportMetadata getTransportMetadata() {
		return session.getTransportMetadata();
	}
	public WriteRequestQueue getWriteRequestQueue() {
		return session.getWriteRequestQueue();
	}
	public int getWriterIdleCount() {
		return session.getWriterIdleCount();
	}
	public long getWrittenBytes() {
		return session.getWrittenBytes();
	}
	public double getWrittenBytesThroughput() {
		return session.getWrittenBytesThroughput();
	}
	public long getWrittenMessages() {
		return session.getWrittenMessages();
	}
	public double getWrittenMessagesThroughput() {
		return session.getWrittenMessagesThroughput();
	}
	public boolean isBothIdle() {
		return session.isBothIdle();
	}
	public boolean isClosing() {
		return session.isClosing();
	}
	public boolean isConnected() {
		return session.isConnected();
	}
	public boolean isIdle(IdleStatus status) {
		return session.isIdle(status);
	}
	public boolean isReaderIdle() {
		return session.isReaderIdle();
	}
	public boolean isReadSuspended() {
		return session.isReadSuspended();
	}
	public boolean isWriterIdle() {
		return session.isWriterIdle();
	}
	public boolean isWriteSuspended() {
		return session.isWriteSuspended();
	}
	public ReadFuture read() {
		return session.read();
	}
	public boolean removeAttribute(Object key, Object value) {
		return session.removeAttribute(key, value);
	}
	public Object removeAttribute(Object key) {
		return session.removeAttribute(key);
	}
	public boolean replaceAttribute(Object key, Object oldValue, Object newValue) {
		return session.replaceAttribute(key, oldValue, newValue);
	}
	public void resumeRead() {
		session.resumeRead();
	}
	public void resumeWrite() {
		session.resumeWrite();
	}
	public Object setAttribute(Object key, Object value) {
		return session.setAttribute(key, value);
	}
	public Object setAttribute(Object key) {
		return session.setAttribute(key);
	}
	public Object setAttributeIfAbsent(Object key, Object value) {
		return session.setAttributeIfAbsent(key, value);
	}
	public Object setAttributeIfAbsent(Object key) {
		return session.setAttributeIfAbsent(key);
	}
	public void setCurrentWriteRequest(WriteRequest currentWriteRequest) {
		session.setCurrentWriteRequest(currentWriteRequest);
	}
	public void suspendRead() {
		session.suspendRead();
	}
	public void suspendWrite() {
		session.suspendWrite();
	}
	public void updateThroughput(long currentTime, boolean force) {
		session.updateThroughput(currentTime, force);
	}
	public WriteFuture write(Object message, SocketAddress destination) {
		return session.write(message, destination);
	}
	public WriteFuture write(Object message) {
		return session.write(message);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (sessionId ^ (sessionId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionWrapper other = (SessionWrapper) obj;
		if (sessionId != other.sessionId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.valueOf(session.getId());
	}
}
