package org.corenel.rabbitmqsupport.consumer;

import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;

import java.io.IOException;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

public class ConsumerRegister {
	
	public <T> void register(Consumer consumer, Channel channel, T event) throws IOException {
		
        String queueName = fetchConfigurationInfo(event.getClass(), RabbitMQConfig.class).queue();
        boolean autoAck = fetchConfigurationInfo(event.getClass(), RabbitMQConfig.class).autoAck();
        int basicQos = fetchConfigurationInfo(event.getClass(), RabbitMQConfig.class).basicQos();
        
        channel.basicQos(basicQos);
        channel.basicConsume(queueName, autoAck, consumer);
    }
}
