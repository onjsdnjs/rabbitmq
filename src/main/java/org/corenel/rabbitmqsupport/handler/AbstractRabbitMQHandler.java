package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.corenel.rabbitmqsupport.message.event.MessageEvent;
import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;
import com.rabbitmq.client.AMQP.Queue.BindOk;
import com.rabbitmq.client.Channel;

@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AbstractRabbitMQHandler extends RabbitMQManager implements MQHandler{
	
	public AbstractRabbitMQHandler(RabbitMQConnectionPool pool) {
		super(pool);
	}

	protected void declareAndBind(Channel channel, Object... messages) throws IOException {
	
		exchangeDeclare(channel, messages);
		queueDeclare(channel, messages);
		bind(channel, messages);
		
	}
	
	private <T> void exchangeDeclare(Channel channel, T... messages) throws IOException {
		
    	for (T messageEvent : messages) {
    	
    		String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
    		String type = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).type();
    		
    		channel.exchangeDeclare(exchange, type);
        }
	}
	
	private <T> void queueDeclare(Channel channel, T... messages) throws IOException {
		
		for (T messageEvent : messages) {
			
			String queue = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).queue();
			boolean durable = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).durable();
			boolean exclusive = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exclusive();
			boolean autoDelete = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).autoDelete();
			
			channel.queueDeclare(queue, durable, exclusive, autoDelete, ((MessageEvent)messageEvent).arguments());
		}
	}

	private <T> BindOk bind(Channel channel, T... messages) throws IOException {
		
		for (T messageEvent : messages) {
			
			String queue = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).queue();
			String exchange = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).exchange();
			String routingKey = fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).routingKey();
			
			return channel.queueBind(queue, exchange, routingKey);
		}
		
		return null;
	}
}
