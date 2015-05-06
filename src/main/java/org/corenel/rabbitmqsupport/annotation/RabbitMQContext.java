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
    
	String urls() default "localhost:5672"; // 클러스터 구성 시 세미콜론(;)을 구분으로 하여 url 을 추가한다 (ex: localhost:5672;localhost:5673)
}
