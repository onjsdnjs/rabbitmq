package org.corenel.rabbitmqsupport.util;

import org.springframework.util.SerializationUtils;

public class RabbitMQMessageConvert {
	
    public static byte[] serialize(Object obj) {
    	return SerializationUtils.serialize(obj);
    }

    public static Object deserialize(byte[] data) {
    	return SerializationUtils.deserialize(data);
    }
    
}
