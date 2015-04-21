package org.corenel.rabbitmqsupport.configurations;

import org.corenel.rabbitmqsupport.annotation.RabbitMQContext;

@RabbitMQContext(urls = "localhost:5672;localhost:5673")
public interface RabbitMQConfiguration {}
