package org.corenel.rabbitmqsupport.executor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.configurations.RabbitMQConfiguration;
import org.corenel.rabbitmqsupport.converters.ConsumerConverter;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.corenel.rabbitmqsupport.handler.RabbitMQConsumerHandler;
import org.corenel.rabbitmqsupport.message.Message;
import org.corenel.rabbitmqsupport.message.event.MessageEvent;
import org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent;
import org.corenel.rabbitmqsupport.util.AddRuntimeAnnotation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component("rabbitMQConsumer")
public class RabbitMQConsumerExecutor implements RabbitMQExecutor, InitializingBean {
	
	@Resource(name="rabbitMQConnectionPool")
    private RabbitMQConnectionPool connectionPool;

	public void execute() throws Exception {
		
		String[] annoFiled1 = new String[]{"exchange","type","queue","routingKey","autoAck","durable","exclusive","autoDelete","converterClass"};
		Object[] annoValues1 = new Object[]{"exchange01","direct","queue01","routingKey01",false,true,false,false,ConsumerConverter.class};
		AddRuntimeAnnotation.addAnnotationDynamic(RabbitMessageEvent.class.getName(), RabbitMQConfig.class.getName(), "config", annoFiled1, annoValues1);

		/*String[] annoField2 = new String[]{"exchange","type","queue","routingKey","autoAck","durable","exclusive","autoDelete","converterClass","retryInterval","basicQos"};
		Object[] annoValues2 = new Object[]{"exchange01","direct","queue01","routingKey01",false,true,false,false,ConsumerConverter.class,	2000,1};
		RabbitMessageEvent<Message> messageEvent2 = (RabbitMessageEvent<Message>)AddRuntimeAnnotation.addAnnotationDynamic
													(RabbitMessageEvent.class.getName(), RabbitMQConfig.class.getName(), "config", annoField2, annoValues2);*/
		
		List<MessageEvent<Message>> messageEvents = new ArrayList<MessageEvent<Message>>();
//		messageEvents.add(messageEvent1);
//		messageEvents.add(messageEvent2);
		
		RabbitMQConsumerHandler rabbitMQConsumerHandler = new RabbitMQConsumerHandler(connectionPool);
		rabbitMQConsumerHandler.register(messageEvents);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
//		connectionPool.initialize(RabbitMQConfiguration.class);
		
	}
}

