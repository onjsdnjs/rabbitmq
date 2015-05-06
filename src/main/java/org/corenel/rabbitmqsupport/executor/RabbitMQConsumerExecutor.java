package org.corenel.rabbitmqsupport.executor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.configurations.RabbitMQConfiguration;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.corenel.rabbitmqsupport.handler.MQHandler;
import org.corenel.rabbitmqsupport.handler.RabbitMQConsumerHandler;
import org.corenel.rabbitmqsupport.message.Message;
import org.corenel.rabbitmqsupport.message.event.MessageEvent;
import org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent;
import org.corenel.rabbitmqsupport.util.AddRuntimeAnnotation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component("rabbitMQConsumer")
public class RabbitMQConsumerExecutor extends RabbitMQExecutor implements InitializingBean {
	
	@Resource(name="rabbitMQConnectionPool")
    private RabbitMQConnectionPool connectionPool;
	
	public void execute() throws Exception {
		
		String[] annoFiled1 = new String[]{"exchange","type","queue","routingKey"};
		Object[] annoValues1 = new Object[]{"exchange01","direct","queue01","routingKey01"};
		RabbitMessageEvent<Message> messageEvent1 = (RabbitMessageEvent<Message>)AddRuntimeAnnotation.addAnnotationDynamic
													(getAnnoMap(),"org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent", RabbitMQConfig.class.getName(), annoFiled1, annoValues1);

		List<MessageEvent<Message>> messageEvents = new ArrayList<MessageEvent<Message>>();
		messageEvents.add(messageEvent1);
		
		MQHandler rabbitMQConsumerHandler = new RabbitMQConsumerHandler(connectionPool);
		rabbitMQConsumerHandler.register(messageEvents);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		connectionPool.initialize(RabbitMQConfiguration.class);
		
	}
}

