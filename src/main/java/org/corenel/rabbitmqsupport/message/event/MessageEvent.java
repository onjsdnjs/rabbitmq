package org.corenel.rabbitmqsupport.message.event;

import java.util.Map;

public interface MessageEvent<T> {
	
	public void onMessage(T t);

	public void AfterRegistered(T t);
	
	public void onCancel(T t);
	
	public void onCancelBySender(T t);

	public void onRecover(T t);
	
	public void onBeforeShutdown(T t);
	
	public Map<String, Object> arguments();

}
