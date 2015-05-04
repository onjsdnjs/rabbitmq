package org.corenel.rabbitmqsupport.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.corenel.rabbitmqsupport.converters.ConsumerConverter;
import org.corenel.rabbitmqsupport.converters.DefaultConverter;
import org.corenel.rabbitmqsupport.message.Message;

@SuppressWarnings("rawtypes")
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitMQConfig {

	String exchange() default "";

	String type() default "";

	String queue() default "queue";

    String routingKey() default "";

    boolean autoAck() default false;

    boolean durable() default true;
    
    boolean exclusive() default false;
    
    boolean autoDelete() default false;

	Class<? extends ConsumerConverter> converterClass() default DefaultConverter.class;

    int retryInterval() default 20000;

    int basicQos() default 1;
}
