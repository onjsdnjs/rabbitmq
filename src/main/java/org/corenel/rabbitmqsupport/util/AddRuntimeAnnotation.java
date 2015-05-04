package org.corenel.rabbitmqsupport.util;


import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
//import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;


public class AddRuntimeAnnotation {
	
	public static Object addAnnotationDynamic(Map<String, Object> annoMap, String className, String annoName, String[] annoFields, Object[] annoValues) throws Exception {

		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(className);
//		CtMethod methodDescriptor = cc.getDeclaredMethod(methodName);
		ClassFile ccFile = null;
		if(!annoMap.containsKey(annoName)){
			ccFile = cc.getClassFile();
		}else{
			ccFile = (ClassFile) annoMap.get(annoName);
		}
		ConstPool constpool = ccFile.getConstPool();
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		Annotation annotation = new Annotation(annoName, constpool);
		
		for (int i=0; i<annoFields.length; i++) {
			
			if(annoValues[i] instanceof String){
				annotation.addMemberValue(annoFields[i], new StringMemberValue((String)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Integer){
				annotation.addMemberValue(annoFields[i], new IntegerMemberValue((Integer)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Double){
				annotation.addMemberValue(annoFields[i], new DoubleMemberValue((Double)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Float){
				annotation.addMemberValue(annoFields[i], new FloatMemberValue((Float)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Boolean){
				annotation.addMemberValue(annoFields[i], new BooleanMemberValue((Boolean)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Byte){
				annotation.addMemberValue(annoFields[i], new ByteMemberValue((Byte)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Character){
				annotation.addMemberValue(annoFields[i], new CharMemberValue((Character)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Short){
				annotation.addMemberValue(annoFields[i], new ShortMemberValue((Short)annoValues[i], ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Class){
				annotation.addMemberValue(annoFields[i], new ClassMemberValue(annoValues[i].getClass().getSimpleName(), ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Enum){
				annotation.addMemberValue(annoFields[i], new EnumMemberValue(ccFile.getConstPool()));
				
			}else if(annoValues[i] instanceof Annotation){
				annotation.addMemberValue(annoFields[i], new AnnotationMemberValue((Annotation)annoValues[i],ccFile.getConstPool()));
			}
		}
		
		attr.addAnnotation(annotation);
//		methodDescriptor.getMethodInfo().addAttribute(attr);
		ccFile.addAttribute(attr);
		annoMap.put(annoName, ccFile);
		
		if(!annoMap.containsKey(className)){
			Class<?> dynamiqueBeanClass = cc.toClass();
			annoMap.put(className, dynamiqueBeanClass);
			return dynamiqueBeanClass.newInstance();
		}else{
			Class<?> dynamiqueBeanClass = ((Class<?>)annoMap.get(className));
			return dynamiqueBeanClass.newInstance();
		}
	}
}
