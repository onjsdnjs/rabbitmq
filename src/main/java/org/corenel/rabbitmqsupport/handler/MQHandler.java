package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.List;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.BasicProperties;

@SuppressWarnings("unchecked")
public interface MQHandler {
	
	public <T> void register(T... listeners) throws IOException;
	
	public <T> void register(List<T> listeners) throws IOException;
	
	public <E,T> void publish(List<E> listeners, BasicProperties basicProperties, T object) throws IOException;
	
	public <T> void publish(List<T> listeners, BasicProperties basicProperties, byte[] object) throws IOException;
	
	public <E,T> void publish(BasicProperties basicProperties, T object, E... listeners) throws IOException;
	
	public <T> void publish(BasicProperties basicProperties, byte[] object, T... listeners) throws IOException;
	
	public <T> void publish(Channel channel, String exchange, String routingKey, BasicProperties basicProperties, byte[] object) throws IOException;
	
}
