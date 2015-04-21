package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.List;
import org.corenel.rabbitmqsupport.exception.NotSupportedException;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;

public class RabbitMQConsumerHandler extends RabbitMQHandler {

	public RabbitMQConsumerHandler(RabbitMQConnectionPool pool) {
		super(pool);
	}

    public <T> void register(List<T> listeners) throws IOException {
        
    	if (listeners == null) {
            throw new NotSupportedException("The listeners are null. Maybe you didn't create your class injecting it");
        }
        super.register(listeners);
    }

    public <T> void register(T... listeners) throws IOException {
    	
    	if (listeners == null) {
    		throw new NotSupportedException("The listeners are null. Maybe you didn't create your class injecting it");
    	}
    	super.register(listeners);
    }

}
