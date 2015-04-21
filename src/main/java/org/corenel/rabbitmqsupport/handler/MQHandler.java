package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.List;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.BasicProperties;

public interface MQHandler {
	
	public <T> void register(T... listeners) throws IOException;
	
	public <T> void register(List<T> listeners) throws IOException;
	
	public <T> void publish(Channel channel, String exchange, String routingKey, BasicProperties basicProperties, byte[] object) throws IOException;
	
}
