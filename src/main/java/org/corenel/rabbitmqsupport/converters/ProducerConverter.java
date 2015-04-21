package org.corenel.rabbitmqsupport.converters;

public interface ProducerConverter<T> {
    byte [] convert(T body);
}
