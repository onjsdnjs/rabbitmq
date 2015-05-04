package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.List;

import org.corenel.rabbitmqsupport.exception.NotSupportedException;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;

@SuppressWarnings("unchecked")
public class RabbitMQConsumerHandler extends RabbitMQHandler {

	public RabbitMQConsumerHandler(RabbitMQConnectionPool pool) {
		super(pool);
	}

    public <T> void register(List<T> messageEvents) throws IOException {
        
    	if (messageEvents == null) {
            throw new NotSupportedException("The messageEvents are null. Maybe you didn't create your class injecting it");
        }
        super.register(messageEvents);
    }

    
	public <T> void register(T... messageEvents) throws IOException {
    	
    	if (messageEvents == null) {
    		throw new NotSupportedException("The messageEvents are null. Maybe you didn't create your class injecting it");
    	}
    	super.register(messageEvents);
    }

}
