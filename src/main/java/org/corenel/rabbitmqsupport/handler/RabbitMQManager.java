package org.corenel.rabbitmqsupport.handler;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.corenel.rabbitmqsupport.factory.RabbitMQConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

public abstract class RabbitMQManager implements ShutdownListener {
	
	Logger LOGGER = LoggerFactory.getLogger(RabbitMQManager.class);
	
	private volatile Connection connection;
	private ScheduledExecutorService executor;
	private RabbitMQConnectionPool connectionPool;
	
	public RabbitMQManager(RabbitMQConnectionPool pool) {
		connectionPool = pool;
	}

	protected void establish() {
		try
        {
			connection = connectionPool.connection();
            connection.addShutdownListener(this);
            executor = Executors.newSingleThreadScheduledExecutor();
            LOGGER.info("Connected to " + connection.getAddress() + ":" + connection.getPort());
        }
        catch (final Exception e)
        {
            LOGGER.debug("Failed to connect to " + connection.getAddress() + ":" + connection.getPort(), e);
            asyncWaitAndReconnect();
        }
		
	}

	public RabbitMQConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
    public void shutdownCompleted(final ShutdownSignalException cause)
    {
        if (!cause.isInitiatedByApplication())
        {
            LOGGER.debug("Lost connection to " + connection.getAddress() + ":" + connection.getPort(), cause);

            connection = null;
            asyncWaitAndReconnect();
        }
    }
    
	
	private void asyncWaitAndReconnect()
    {
        executor.schedule(new Runnable()
        {
            @Override
            public void run()
            {
				establish();
            }
        }, 15, TimeUnit.SECONDS);
    }
	
	public void connect() throws IOException {
		establish();
	}
	
	public void stop()
    {
        executor.shutdownNow();

        if (connection == null)
        {
            return;
        }

        connectionPool.returnConnection(connection);
    }

    public Channel createChannel()
    {
        try
        {
            return connection == null ? null : connection.createChannel();
        }
        catch (final Exception e)
        {
            LOGGER.debug("Failed to create channel", e);
            return null;
        }
    }

    public void closeChannel(final Channel channel)
    {
        if ((channel == null) || (!channel.isOpen()))
        {
            return;
        }

        try
        {
            channel.close();
        }
        catch (final Exception e)
        {
            LOGGER.debug("Failed to close channel: " + channel, e);
        }
    }
	
}
