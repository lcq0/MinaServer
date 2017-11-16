package com.kingdom.park.socket.service.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	@SuppressWarnings("rawtypes")
	public static void info(Class clazz,String info){
		Logger logger = Logger.getLogger(clazz);
		logger.info(info);
	}
	@SuppressWarnings("rawtypes")
	public static void error(Class clazz,String info,Exception e){
		Logger logger = Logger.getLogger(clazz);
		logger.error(info, e);
	}
}
