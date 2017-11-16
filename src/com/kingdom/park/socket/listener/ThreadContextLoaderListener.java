package com.kingdom.park.socket.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @author CQling
 * @version 2017-7-19下午5:46:51
 * TODO
 */
public class ThreadContextLoaderListener extends ContextLoaderListener {

	@SuppressWarnings("rawtypes")
	Class t_class = ThreadContextLoaderListener.class;
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		System.out.println("执行contextInitialized");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		com.kingdom.park.socket.service.util.LoggerUtil.info(t_class,"执行contextDestroyed");
		super.contextDestroyed(event);
	}
	
}
