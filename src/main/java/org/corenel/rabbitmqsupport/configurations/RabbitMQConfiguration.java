package org.corenel.rabbitmqsupport.configurations;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.annotation.RabbitMQContext;
import org.corenel.rabbitmqsupport.converters.DefaultConverter;

@RabbitMQContext(urls = "1.226.84.208:5672")
@RabbitMQConfig(converterClass = DefaultConverter.class)
public interface RabbitMQConfiguration {}
