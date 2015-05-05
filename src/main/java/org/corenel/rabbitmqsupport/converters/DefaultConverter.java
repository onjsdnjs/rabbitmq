package org.corenel.rabbitmqsupport.converters;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@SuppressWarnings("unchecked")
@Component
public class DefaultConverter<T> implements ConsumerConverter<T>, ProducerConverter<T> {
	
	@Override
    public T convert(byte[] body) {
        return (T) SerializationUtils.deserialize(body);
    }

    @Override
    public byte[] convert(T body) {
        return SerializationUtils.serialize(body);
    }
}
