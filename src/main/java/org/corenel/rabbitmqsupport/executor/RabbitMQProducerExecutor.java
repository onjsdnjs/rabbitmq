package org.corenel.rabbitmqsupport.executor;

import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.annotation.RabbitMQContext;
import org.corenel.rabbitmqsupport.configurations.RabbitMQConfiguration;
import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.corenel.rabbitmqsupport.handler.RabbitMQProducerHandler;
import org.corenel.rabbitmqsupport.message.CommandMessage;
import org.corenel.rabbitmqsupport.message.Message;
import org.corenel.rabbitmqsupport.message.event.MessageEvent;
import org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent;
import org.corenel.rabbitmqsupport.util.AddRuntimeAnnotation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component("rabbitMQProducer")
public class RabbitMQProducerExecutor extends RabbitMQExecutor implements InitializingBean {
	
	@Resource(name="rabbitMQConnectionPool")
    private RabbitMQConnectionPool connectionPool;
	
	public void execute() throws Exception {
		
		String[] annoFiled1 = new String[]{"exchange","type","queue","routingKey"};
		Object[] annoValues1 = new Object[]{"exchange01","direct","queue01","routingKey01"};
		RabbitMessageEvent<Message> messageEvent1 = (RabbitMessageEvent<Message>)AddRuntimeAnnotation.addAnnotationDynamic
													(getAnnoMap(),"org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent", RabbitMQConfig.class.getName(), annoFiled1, annoValues1);
		
		RabbitMQConfig anno1 = fetchConfigurationInfo(RabbitMessageEvent.class, RabbitMQConfig.class);
		
		String[] annoFiled2 = new String[]{"exchange","type","queue","routingKey"};
		Object[] annoValues2 = new Object[]{"exchange02","topic","queue02","routingKey02"};
		RabbitMessageEvent<Message> messageEvent2 = (RabbitMessageEvent<Message>)AddRuntimeAnnotation.addAnnotationDynamic
													(getAnnoMap(), "org.corenel.rabbitmqsupport.message.event.RabbitMessageEvent", RabbitMQConfig.class.getName(), annoFiled2, annoValues2);
		
		RabbitMQConfig anno2 = fetchConfigurationInfo(RabbitMessageEvent.class, RabbitMQConfig.class);

		List<MessageEvent<Message>> messageEvents = new ArrayList<MessageEvent<Message>>();
		messageEvents.add(messageEvent1);
		messageEvents.add(messageEvent2);
		
		Message message = new CommandMessage();
		message.setMessage("test message");
		
		RabbitMQProducerHandler rabbitMQProducerHandler = new RabbitMQProducerHandler(connectionPool);
		rabbitMQProducerHandler.publish(messageEvents, null, message);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		connectionPool.initialize(RabbitMQConfiguration.class);
		
	}
}

