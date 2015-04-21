package org.corenel.rabbitmqsupport.factory;

import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ConnectionPooledFactory implements PooledObjectFactory<Connection> {
	
    private RabbitMQConnectionFactory factory;
	private Class<?> clazz;

    public ConnectionPooledFactory(RabbitMQConnectionFactory rabbitMQClientFactory, Class<?> clazz) {
        this.factory = rabbitMQClientFactory;
		this.clazz = clazz;
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        return new DefaultPooledObject<Connection>(factory.connection(clazz));
    }

    @Override
    public void destroyObject(PooledObject<Connection> connectionPooledObject) throws Exception {
        connectionPooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Connection> connectionPooledObject) {
        return connectionPooledObject.getObject().isOpen();
    }

    @Override
    public void activateObject(PooledObject<Connection> connectionPooledObject) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<Connection> connectionPooledObject) throws Exception {
    }

}
