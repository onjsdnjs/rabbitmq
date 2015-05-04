package org.corenel.rabbitmqsupport;


import org.corenel.rabbitmqsupport.executor.RabbitMQExecutor;
import org.corenel.rabbitmqsupport.executor.RabbitMQProducerExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("resource")
public class RabbitMQProducer {
	
	public static void main(String[] args) throws Exception{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/common.xml");
		RabbitMQExecutor executor = (RabbitMQProducerExecutor)context.getBean("rabbitMQProducer");
		executor.execute();
	}
}
