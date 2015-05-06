package org.corenel.rabbitmqsupport.message.event;

import java.util.Map;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.consumer.annotations.AfterRegistered;
import org.corenel.rabbitmqsupport.consumer.annotations.BeforeShutdown;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancel;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancelBySender;
import org.corenel.rabbitmqsupport.consumer.annotations.OnMessage;
import org.corenel.rabbitmqsupport.consumer.annotations.OnRecover;
import org.corenel.rabbitmqsupport.handler.RabbitMQManager;
import org.corenel.rabbitmqsupport.message.CommandMessage;
import org.corenel.rabbitmqsupport.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RabbitMQConfig
public class RabbitMessageEvent<T> implements MessageEvent<T>{
	
	Logger LOGGER = LoggerFactory.getLogger(RabbitMessageEvent.class);

    @OnMessage
    public void onMessage(T t) {
    	Message message = (CommandMessage)t;
    	LOGGER.info(message.getMessage());
    }

	@AfterRegistered
	public void AfterRegistered(T t) {
		LOGGER.info(t.toString());
	}

	@OnCancel
	public void onCancel(T t) {
		LOGGER.info(t.toString());
	}

	@OnCancelBySender
	public void onCancelBySender(T t) {
		LOGGER.info(t.toString());
	}

	@OnRecover
	public void onRecover(T t) {
		LOGGER.info(t.toString());
	}

	@BeforeShutdown
	public void onBeforeShutdown(T t) {
		LOGGER.info(t.toString());
	}
	
	@Override
	public Map<String, Object> arguments() {
		return null;
	}
}
