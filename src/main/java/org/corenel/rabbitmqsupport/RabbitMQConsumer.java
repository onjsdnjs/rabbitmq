package org.corenel.rabbitmqsupport;

import org.corenel.rabbitmqsupport.executor.RabbitMQConsumerExecutor;
import org.corenel.rabbitmqsupport.executor.RabbitMQExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("resource")
public class RabbitMQConsumer {
	
	public static void main(String[] args) throws Exception{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/common.xml");
		RabbitMQExecutor executor = (RabbitMQConsumerExecutor)context.getBean("rabbitMQConsumer");
		
		executor.execute();
	}
	
}
