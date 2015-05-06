package org.corenel.rabbitmqsupport.factory;

import static org.corenel.rabbitmqsupport.util.AnnotationIntrospector.fetchConfigurationInfo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.corenel.rabbitmqsupport.annotation.RabbitMQContext;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component("rabbitMQClientFactory")
public class RabbitMQConnectionFactory {
	
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	
    public Connection connection(Class<?> clazz) throws IOException {
    	
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(fetchConfigurationInfo(clazz, RabbitMQContext.class).username());
        factory.setPassword(fetchConfigurationInfo(clazz, RabbitMQContext.class).password());
        factory.setVirtualHost(fetchConfigurationInfo(clazz, RabbitMQContext.class).virtualhost());
        String[] urls = fetchConfigurationInfo(clazz, RabbitMQContext.class).urls().split(";");

        List<Address> addresses = new LinkedList<Address>();

        for (String url : urls) {
        	
            String[] urlInf = url.split(":");
            String hostname = urlInf[0];
            int port = Integer.parseInt(urlInf[1]);
            addresses.add(new Address(hostname, port));
            
        }
        return factory.newConnection(executorService, addresses.toArray(new Address[addresses.size()]));
    }

}
