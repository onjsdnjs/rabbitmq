package org.corenel.rabbitmqsupport.handler;

import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;

import java.io.IOException;
import java.util.List;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.consumer.ConsumerRegister;
import org.corenel.rabbitmqsupport.consumer.DefaultConsumer;
import org.corenel.rabbitmqsupport.converters.DefaultConverter;
import org.corenel.rabbitmqsupport.converters.ProducerConverter;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

public class RabbitMQHandler extends AbstractRabbitMQHandler {
	
	public RabbitMQHandler(RabbitMQConnectionPool pool) {
		super(pool);
	}

	@Override
    public <T> void register(T... events) throws IOException {
	
		connect();
		for (T messageEvent : events) {
			
        	Channel channel = createChannel();
        	declareAndBind(channel, messageEvent);
            new DefaultConsumer<T>(getConnection(), channel, new ConsumerRegister(), messageEvent);
        }
    }

	@Override
	public <T> void register(List<T> events) throws IOException {
	
		connect();
        for (T messageEvent : events) {
        	
        	Channel channel = createChannel();
        	declareAndBind(channel, messageEvent);
            new DefaultConsumer<T>(getConnection(), channel, new ConsumerRegister(), messageEvent);
        }
    }

    public <E,T> void publish(List<E> events, BasicProperties basicProperties, T object) throws IOException {
    
    	connect();
    	Channel channel = null;
    	try {
	    	for (E messageEvent : events) {
	    		
	    		channel = createChannel();
	    		declareAndBind(channel, messageEvent);
	            
	            String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
	    		String routingKey = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).routingKey();
	    		
	    		publish(channel, exchange, routingKey, basicProperties, object, new DefaultConverter<T>());
	    	}
	    	
	    }finally {
	    	closeChannel(channel);
	        getConnectionPool().returnConnection(getConnection());
	    }
    }

    public <T> void publish(List<T> events, BasicProperties basicProperties, byte[] object) throws IOException {
    
    	connect();
    	Channel channel = null;
    	try {
	    	for (T messageEvent : events) {
	    		
	    		channel = createChannel();
	    		declareAndBind(channel, messageEvent);
	    		
	    		String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
	    		String routingKey = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).routingKey();
	    		
	    		publish(channel, exchange, routingKey, basicProperties, object);
	    	}
	    	
	    }finally {
	    	closeChannel(channel);
	    	getConnectionPool().returnConnection(getConnection());
	    }
    }

    public <E, T> void publish(BasicProperties basicProperties, T object, E... events) throws IOException {
    	
    	connect();
    	Channel channel = null;
    	try {
	    	for (E messageEvent : events) {
	    		
	    		channel = createChannel();
	    		declareAndBind(channel, messageEvent);
	    		
	    		String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
	    		String routingKey = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).routingKey();
	    		
	    		publish(channel, exchange, routingKey, basicProperties, object, new DefaultConverter<T>());
	    	}
	    	
    	}finally {
        	closeChannel(channel);
        	getConnectionPool().returnConnection(getConnection());
        }
    }

    public <T> void publish(BasicProperties basicProperties, byte[] object, T... events) throws IOException {

    	connect();
    	Channel channel = null;
    	try {
	    	for (T messageEvent : events) {
	    		
	    		channel = createChannel();
	    		declareAndBind(channel, messageEvent);
	    		
	    		String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
	    		String routingKey = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).routingKey();
	    		
	    		publish(channel, exchange, routingKey, basicProperties, object);
	    	}
	    	
    	}finally {
        	closeChannel(channel);
        	getConnectionPool().returnConnection(getConnection());
        }
    }

    public <T> void publish(Channel channel, String exchange, String routingKey, BasicProperties basicProperties, T object, ProducerConverter<T> converter) throws IOException {
    	
        byte[] bytes = converter.convert(object);
        publish(channel, exchange, routingKey, basicProperties, bytes);
    }

    @Override
    public <T> void publish(Channel channel, String exchange, String routingKey, BasicProperties basicProperties, byte[] object) throws IOException {

    	channel.basicPublish(exchange, routingKey, basicProperties, object);
    }
	
}
