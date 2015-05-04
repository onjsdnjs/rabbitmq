package org.corenel.rabbitmqsupport.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RabbitMQExecutor {
	
	private final Map<String, Object> annoMap = new ConcurrentHashMap<String,Object>(); 
	
	public abstract void execute() throws Exception;

	protected Map<String, Object> getAnnoMap() {
		return annoMap;
	}
	
}

