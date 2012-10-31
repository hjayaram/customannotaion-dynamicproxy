package com.proxy;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myannotations.Service;

public class ProxyClass implements InvocationHandler {

	private static final VelocityEngine vEngine = (VelocityEngine)
       ((new ClassPathXmlApplicationContext("resources/applicationContext.xml")).
       getBean("velocityEngine"));
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
        try {
        	Service service = method.getAnnotation(Service.class);
        	System.out.println("The service name is " + service.name());
        	System.out.println("The pattern is " + service.pattern());
            //result = method.invoke(args);
        	String retVal = substituteParams(method,args);
        	return "This is the response " + retVal;
        } catch (Exception eBj) {
        	eBj.printStackTrace();
        } finally {
            // Do something after the method is called ...
        }
        return result;
	}

	private String substituteParams(Method method, Object[] args){
		Service service = method.getAnnotation(Service.class);
		String template = service.pattern();
		String[] patternArray = template.split("/");
		HashMap<String, String> context1 = new HashMap<String,String>();
		for (int i = 0; i < patternArray.length; i++) {
			context1.put(stripFirstChar(patternArray[i]), (String)args[i]);
		}
        StringWriter writer = new StringWriter();
        VelocityContext vContext = new VelocityContext(context1);
        try {
        	vEngine.evaluate(vContext, writer, "logTag", template);
		} catch (ParseErrorException e1) {
			e1.printStackTrace();
		} catch (MethodInvocationException e1) {
			e1.printStackTrace();
		} catch (ResourceNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}        
		return writer.toString();
	}
	
	private String stripFirstChar(String value) {
		return value.substring(1);
	}
}
