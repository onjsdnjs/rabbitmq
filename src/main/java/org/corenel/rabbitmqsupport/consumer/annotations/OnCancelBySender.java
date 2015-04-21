package org.corenel.rabbitmqsupport.consumer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnCancelBySender {
    boolean retryOnException() default true;
    int maxRetries() default 50;
    int retryInterval() default 100000;}
