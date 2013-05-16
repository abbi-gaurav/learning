package com.test.util.log;

import org.mortbay.log.Logger;

public class MyLogger implements Logger {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("myLogger");
	
	public void debug(String msg, Throwable th) {
		// TODO Auto-generated method stub
		logger.debug(msg, th);
	}

	public void debug(String msg, Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		StringBuffer sb = generateSBFromLog(msg, arg0, arg1);
		logger.debug(sb.toString());
	}

	protected StringBuffer generateSBFromLog(String msg, Object arg0,
			Object arg1) {
		StringBuffer sb = new StringBuffer(msg);
		if(arg0 != null)
			sb.append(arg0.toString());
		if(arg1 != null)
			sb.append(arg1.toString());
		System.out.println(sb.toString());
		return sb;
	}

	public Logger getLogger(String name) {
		// TODO Auto-generated method stub
		return this;
	}

	public void info(String msg, Object arg0, Object arg1) {
		StringBuffer sb = generateSBFromLog(msg, arg0, arg1);
		logger.debug(sb.toString());

	}

	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return System.getProperty("DEBUG") != null;
	}

	public void setDebugEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		System.setProperty("DEBUG", "true");
	}

	public void warn(String msg, Throwable th) {
		// TODO Auto-generated method stub
		logger.info(msg, th);
	}

	public void warn(String msg, Object arg0, Object arg1) {
		StringBuffer sb = generateSBFromLog(msg, arg0, arg1);
		logger.debug(sb.toString());
	}

}
