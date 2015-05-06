package org.corenel.rabbitmqsupport.consumer;


import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.converterInstance;
import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;
import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.loadMethods;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.consumer.annotations.AfterRegistered;
import org.corenel.rabbitmqsupport.consumer.annotations.BeforeShutdown;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancel;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancelBySender;
import org.corenel.rabbitmqsupport.consumer.annotations.OnMessage;
import org.corenel.rabbitmqsupport.consumer.annotations.OnRecover;
import org.corenel.rabbitmqsupport.converters.ConsumerConverter;
import org.corenel.rabbitmqsupport.message.CommandMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class DefaultConsumer<T> implements Consumer {
	
	Logger LOGGER = LoggerFactory.getLogger(DefaultConsumer.class);
	
    private ConsumerRegister consumerRegister;
    private Map<Class<? extends Annotation>, Method> annotations;
    private T messageEvent;
	private Channel channel;

    public DefaultConsumer(Channel ch, ConsumerRegister register, T event) throws IOException {
    
		channel = ch;
		messageEvent = event;
		consumerRegister = register;
    	consumerRegister.register(this, channel, messageEvent);
    }

	@Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
    	
        Method method = annotations.get(OnMessage.class);
        
        if (method != null) {
            try
            {
            	ConsumerConverter<Object> convert = (ConsumerConverter<Object>) converterInstance(messageEvent.getClass());
            	CommandMessage message = (CommandMessage)convert.convert(body);
                method.invoke(messageEvent, message);
                replyTo(envelope, properties);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
            catch (final Exception e)
            {
                LOGGER.info("Failed to push over websocket message ID: " + properties.getMessageId(), e);
                try
                {
                    final boolean requeue = true;
                    channel.basicReject(envelope.getDeliveryTag(), requeue);
                }
                catch (final Exception e2)
                {
                    LOGGER.info("Failed to reject and requeue message ID: " + properties.getMessageId(), e);
                }
            }finally{
//            	channel.close();
            }
        }
    }

	private void replyTo(Envelope envelope, AMQP.BasicProperties properties) throws IOException, UnsupportedEncodingException {

		String routingKey = envelope.getRoutingKey();
		/*properties.getReplyTo();
		String contentType = properties.getContentType();
		long deliveryTag = envelope.getDeliveryTag();*/
		channel.basicPublish("", routingKey, new BasicProperties.Builder().correlationId(properties.getCorrelationId()).build(), 
				"send response from Consumer".getBytes("UTF-8"));
	}

    @Override
    public void handleConsumeOk(String consumerTag) {
    	annotations = loadMethods(messageEvent);
        StringBuilder builder = new StringBuilder();

        builder.append("Class ")
               .append(messageEvent.getClass().getSimpleName())
               .append(" listening to ")
               .append(fetchConfigurationInfo(messageEvent.getClass(), RabbitMQConfig.class).queue())
               .append(" queue");

        if(annotations != null){
	        Method method = annotations.get(AfterRegistered.class);
	
	        if (method != null) {
	            try {
	                method.invoke(messageEvent, consumerTag);
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        }
        }

        LOGGER.info(builder.toString());
    }

    @Override
    public void handleCancelOk(String consumerTag) {
    	LOGGER.info("handleCancelOk");

        Method method = annotations.get(OnCancelBySender.class);

        if (method != null) {
            try {
                method.invoke(messageEvent, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
    	LOGGER.info("handleCancel");

        Method method = annotations.get(OnCancel.class);

        if (method != null) {
            try {
                method.invoke(messageEvent, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        try {
            consumerRegister.register(this, channel, messageEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Method method = annotations.get(BeforeShutdown.class);

        if (method != null) {
            try {
                method.invoke(messageEvent, consumerTag, sig);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
    	LOGGER.info("handleRecoverOk");

        Method method = annotations.get(OnRecover.class);

        if (method != null) {
            try {
                method.invoke(messageEvent, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object messageEvent() {
        return messageEvent;
    }
}
