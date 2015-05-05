package org.corenel.rabbitmqsupport.converters;


public interface ProducerConverter<T> extends Converter<T>{
    byte [] convert(T body);
}
