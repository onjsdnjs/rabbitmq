package org.corenel.rabbitmqsupport.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitMQContext {
    
	String username() default "onjsdnjs";

	String password() default "sust1616";
    
	String virtualhost() default "/";
    
	String urls() default "localhost:5672"; //if you have a cluster, you should separate each url by ";"
}
