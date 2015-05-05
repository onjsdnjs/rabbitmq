package org.corenel.rabbitmqsupport.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.corenel.rabbitmqsupport.annotation.RabbitMQConfig;
import org.corenel.rabbitmqsupport.consumer.annotations.AfterRegistered;
import org.corenel.rabbitmqsupport.consumer.annotations.BeforeShutdown;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancel;
import org.corenel.rabbitmqsupport.consumer.annotations.OnCancelBySender;
import org.corenel.rabbitmqsupport.consumer.annotations.OnMessage;
import org.corenel.rabbitmqsupport.consumer.annotations.OnRecover;
import org.corenel.rabbitmqsupport.converters.ConsumerConverter;
import org.corenel.rabbitmqsupport.converters.Converter;
import org.corenel.rabbitmqsupport.message.Message;

@SuppressWarnings({"unchecked","rawtypes"})
public class AnnotationIntrospector {
	
	private static Map<Class, ConsumerConverter> classConverterMap;

    public Annotation[] findClassAnnotation(Class<?> clazz) {
        return clazz.getAnnotations();
    }
    
	public static <A extends Annotation> A fetchConfigurationInfo(Class<?> clazz, Class<A> anno) {
        return clazz.getAnnotation(anno);
    }

	public static <T> Converter<T> converterInstance(Class<?> clz) {
        if (classConverterMap == null) {
            classConverterMap = new HashMap<Class, ConsumerConverter>();
        }

        RabbitMQConfig annotation = clz.getAnnotation(RabbitMQConfig.class);
        Class<? extends ConsumerConverter<Message>> converterClass = (Class<? extends ConsumerConverter<Message>>) annotation.converterClass();

        if (classConverterMap.containsKey(converterClass)) {
            return classConverterMap.get(converterClass);
        }

        try {
            classConverterMap.put(converterClass, converterClass.newInstance());
            return classConverterMap.get(converterClass);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }
    
    public static Map<Class<? extends Annotation>, Method> loadMethods(Object messageEvent) {
    	
		Map<Class<? extends Annotation>, Method> annotations = new HashMap<Class<? extends Annotation>, Method>();

        for (Method method : messageEvent.getClass().getDeclaredMethods()) {
        	
            if (hasAnnotation(method, OnMessage.class)) {
                annotations.put(OnMessage.class, method);
                continue;
            }

            if (hasAnnotation(method, AfterRegistered.class)) {
                annotations.put(AfterRegistered.class, method);
                continue;
            }

            if (hasAnnotation(method, BeforeShutdown.class)) {
                annotations.put(BeforeShutdown.class, method);
                continue;
            }

            if (hasAnnotation(method, OnCancel.class)) {
                annotations.put(OnCancel.class, method);
                continue;
            }

            if (hasAnnotation(method, OnCancelBySender.class)) {
                annotations.put(OnCancelBySender.class, method);
                continue;
            }

            if (hasAnnotation(method, OnRecover.class)) {
                annotations.put(OnRecover.class, method);
                continue;
            }
        }

        return annotations;
    }
	
	private static boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) {
        return method.getAnnotation(annotation) != null;
    }
	
    public Annotation[] findMethodAnnotation(Class<?> clazz, String methodName) {

        Annotation[] annotations = null;
        try {
            Class<?>[] params = null;
            Method method = clazz.getDeclaredMethod(methodName, params);
            if (method != null) {
                annotations = method.getAnnotations();
            }
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        }
        return annotations;
    }

    public static Annotation[] findFieldAnnotation(Class<?> clazz, String fieldName) {
        Annotation[] annotations = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                annotations = field.getAnnotations();
            }
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
        }
        return annotations;
    }

    public static String[] setFieldNames(Class<?> clazz) {
    	Annotation[] annotations = null;
    	try {
    		Field[] fields = clazz.getDeclaredFields();
    		List<String> fieldNames = new ArrayList<String>();
    		for (Field field : fields) {
    			if (field != null) {
    				annotations = field.getAnnotations();
    				for (Annotation anno : annotations) {
						if(anno.annotationType() == RabbitMQConfig.class){
							fieldNames.add(field.getName());
						}
					}
    			}
			}
    		return fieldNames.toArray(new String[]{});
    	} catch (SecurityException e) {
    	}
    	return null;
    }

    public static void showAnnotations(Annotation[] ann) {
        if (ann == null)
            return;
        for (Annotation a : ann) {
            System.out.println(a.toString());
        }
    }

}
