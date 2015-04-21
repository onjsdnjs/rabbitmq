package org.corenel.rabbitmqsupport.executor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.corenel.rabbitmqsupport.configurations.RabbitMQConfiguration;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.corenel.rabbitmqsupport.handler.RabbitMQProducerHandler;
import org.corenel.rabbitmqsupport.message.Message;
import org.corenel.rabbitmqsupport.message.event.MessageEvent;
import org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component("rabbitMQProducer")
public class RabbitMQProducerExecutor implements RabbitMQExecutor, InitializingBean {
	
	@Resource(name="rabbitMQConnectionPool")
    private RabbitMQConnectionPool connectionPool;
	
	public void execute() throws Exception {
		
		List<MessageEvent<Message>> listeners = new ArrayList<MessageEvent<Message>>();
		listeners.add(new RabbitMessageEvent<Message>());
		
		RabbitMQProducerHandler rabbitMQProducerHandler = new RabbitMQProducerHandler(connectionPool);
		rabbitMQProducerHandler.publish(listeners, null, "test message".getBytes());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		connectionPool.initialize(RabbitMQConfiguration.class);
		
	}
}

