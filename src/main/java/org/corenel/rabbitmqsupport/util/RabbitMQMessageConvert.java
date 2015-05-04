package org.corenel.rabbitmqsupport.util;

import org.springframework.util.SerializationUtils;

public class RabbitMQMessageConvert {
	
    public static byte[] serialize(Object obj) {
    	byte[] bytes = SerializationUtils.serialize(obj);
    	return bytes;
    }

    public static Object deserialize(byte[] data) {
    	Object obj = SerializationUtils.deserialize(data);
    	return obj;
    }
    
}
