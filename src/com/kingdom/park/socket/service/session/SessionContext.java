package com.kingdom.park.socket.service.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.mina.core.session.IoSession;

/**
 * Session容器，缓存所有已经建立了连接的Session信息
 * @author YaoZhq
 * 
 */
public class SessionContext implements Serializable {
	private static final long serialVersionUID = -2057255948012084359L;
	
	 /** 单例实例变量 */
	private static SessionContext instance = null;
    /**
     * 缓存服务器与客户端之间的会话
     */
    private final Map<Long, UserSessionWrapper> sessions = Collections.synchronizedMap(new HashMap<Long, UserSessionWrapper>());

    
	 /**
	  * 获取单例对象实例
	  * 同步方法，实现线程互斥访问，保证线程安全。
	  * 
	  * @return 单例对象
	  */
  public static synchronized SessionContext getInstance() {
      if (instance == null) { 
           instance = new SessionContext();
      }
       return instance;
   }
    
    public boolean sessionContains(UserSessionWrapper session) {
    	return sessions.containsKey(session.getSessionId());
    }

    public boolean sessionContains(Long sessionId) {
    	return sessions.containsKey(sessionId);
    }
    
    public Collection<UserSessionWrapper> getSessions() {
    	/**
    	 * 重新包装一下再返回，以免在做迭代时抛出java.util.ConcurrentModificationException
    	 * **/
    	Collection<UserSessionWrapper> sws = new ArrayList<UserSessionWrapper>(sessions.values());
		return sws;
	}
    
    public void addSession(UserSessionWrapper session) {
        synchronized (sessions) {
        	if (sessions.containsKey(session.getSessionId()) == false)
        		sessions.put(session.getSessionId(), session);
        }
    }
    
    public UserSessionWrapper findSession(Long sessionId) {
    	return sessions.get(sessionId);
    }
    
    public void removeSession(IoSession session) {
        synchronized (sessions) {
        	Set<UserSessionWrapper> temp = new HashSet<UserSessionWrapper>();
        	for (UserSessionWrapper sw : sessions.values()) {
        		if (session.equals(sw.getSession())) {
        			temp.add(sw);
        		}
        	}
        	if (temp.isEmpty()) return;
        	for (UserSessionWrapper sw : temp) {
        		sessions.remove(sw.getSessionId());
        	}
        }
    }
    
    public void dispose() {
    	sessions.clear();
    }
    

	/**
	 * 从session容器中找到对应连接的停车场
	 * @param parkcode
	 * @return
	 */
    public UserSessionWrapper getSessionByParkCode(String parkcode){
    	try {
    		Collection<UserSessionWrapper> sws=getSessions();
    		for(UserSessionWrapper u:sws){
    			String park_code=u.getParkCode();
    			if(parkcode.equals(park_code)){
    				return u;
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    
	/**
	 * 从session容器中找到对应连接的停车场
	 * @param lincesKey
	 * @return
	 */
    public UserSessionWrapper getSessionBylincesKey(String lincesKey){
    	try {
    		Collection<UserSessionWrapper> sws=getSessions();
    		for(UserSessionWrapper u:sws){
    			//第一步：先从包头linces字段中 查找,是否存在
    			String linces=u.getLicense();
    			//第二步：如果包头linces字段匹配，则查找完毕
    			if(lincesKey.equals(linces)){
    				return u;
    			}
    			//第三步：如果包头linces字段查找不匹配，则可能为平台接入，继续查找下属lincesMap中是否包含
    			HashMap<String,String> mm=u.getLicenseMap();
    			if(mm.containsKey(lincesKey)){
    				return u;
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
