package org.corenel.rabbitmqsupport.message.event;

import java.util.Map;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.consumer.annotations.AfterRegistered;
import org.corenel.rabbitmqsupport.consumer.annotations.BeforeShutdown;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancel;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancelBySender;
import org.corenel.rabbitmqsupport.consumer.annotations.OnMessage;
import org.corenel.rabbitmqsupport.consumer.annotations.OnRecover;

@RabbitMQConfig
public class RabbitMessageEvent<T> implements MessageEvent<T>{

    @OnMessage
    public void onMessage(T t) {
    	System.out.println(t);
    }

	@AfterRegistered
	public void AfterRegistered(T t) {
		System.out.println(t);
	}

	@OnCancel
	public void onCancel(T t) {
		System.out.println(t);
	}

	@OnCancelBySender
	public void onCancelBySender(T t) {
		System.out.println(t);
	}

	@OnRecover
	public void onRecover(T t) {
		System.out.println(t);
	}

	@BeforeShutdown
	public void onBeforeShutdown(T t) {
		System.out.println(t);
	}
	
	@Override
	public Map<String, Object> arguments() {
		return null;
	}
}
