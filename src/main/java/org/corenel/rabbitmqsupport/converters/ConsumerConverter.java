package org.corenel.rabbitmqsupport.converters;

public interface ConsumerConverter<T> {
	
    T convert(byte[] body);
    
}
