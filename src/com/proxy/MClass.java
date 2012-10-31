package com.proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class MClass {

	public static void main(String[] args) {
		ApplicationContext context = 
    		new ClassPathXmlApplicationContext
    		("resources/applicationContext.xml");

		IProxy iProxy = (IProxy)context.getBean("client");
		String x= iProxy.doSomething("myCode","myValue");
		System.out.println("The value of x is \n" + x);
		
		x= iProxy.doSomethingElse("myCode1","myValue0","myValue1");
		System.out.println("The value of x is \n" + x);
	}
}
