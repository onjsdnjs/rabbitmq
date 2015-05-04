package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.List;

import org.corenel.rabbitmqsupport.converters.ProducerConverter;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.BasicProperties;

@SuppressWarnings("unchecked")
public class RabbitMQProducerHandler extends RabbitMQHandler{
	
	public RabbitMQProducerHandler(RabbitMQConnectionPool pool) {
		super(pool);
	}
    
    public <E,T> void publish(List<E> listeners, BasicProperties basicProperties, T object) throws IOException {
        super.publish(listeners, basicProperties, object);
    }

    public <T> void publish(List<T> listeners, BasicProperties basicProperties, byte[] object) throws IOException {
    	super.publish(listeners, basicProperties, object);
    }
    
    public <E,T> void publish(BasicProperties basicProperties, T object, E... listeners) throws IOException {
    	super.publish(basicProperties, object, listeners);
    }
    
    public <T> void publish(BasicProperties basicProperties, byte[] object, T... listeners) throws IOException {
    	super.publish(basicProperties, object, listeners);
    }

    public <T> void publish(Channel channel, String exchange, String routingKey, BasicProperties basicProperties, T object, ProducerConverter<T> converter) throws IOException {
    	super.publish(channel, exchange, routingKey, basicProperties, object, converter);
    }
}
