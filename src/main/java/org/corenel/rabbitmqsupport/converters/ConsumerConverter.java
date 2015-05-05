package org.corenel.rabbitmqsupport.converters;

public interface ConsumerConverter<T> extends Converter<T>{
	
    T convert(byte[] body);
    
}
