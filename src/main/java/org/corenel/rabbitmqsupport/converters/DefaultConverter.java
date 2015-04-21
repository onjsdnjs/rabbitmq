package org.corenel.rabbitmqsupport.converters;

import static org.corenel.rabbitmqsupport.util.RabbitMQMessageConvert.deserialize;
import static org.corenel.rabbitmqsupport.util.RabbitMQMessageConvert.serialize;

@SuppressWarnings("unchecked")
public class DefaultConverter<T> implements ConsumerConverter<T>, ProducerConverter<T> {
	
	@Override
    public T convert(byte[] body) {
        return (T) deserialize(body);
    }

    @Override
    public byte[] convert(T body) {
        return serialize(body);
    }
}
