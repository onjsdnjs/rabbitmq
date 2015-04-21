package org.corenel.rabbitmqsupport.factory;

import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.annotation.Resource;

@Component("rabbitMQConnectionPool")
public class RabbitMQConnectionPool{
	
	@Resource(name="rabbitMQClientFactory")
    private RabbitMQConnectionFactory rabbitMQClientFactory;
	
	private GenericObjectPool<Connection> pool;

    public Connection connection() throws IOException {
    	
        try {
            return pool.borrowObject();
            
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public void returnConnection(Connection connection) {
        pool.returnObject(connection);
    }

	public void initialize(Class<?> clazz){
		
		pool = new GenericObjectPool<Connection>(new ConnectionPooledFactory(rabbitMQClientFactory, clazz));
        pool.setMaxIdle(5);
        pool.setMinIdle(5);
        pool.setMaxTotal(20);
	}
}
