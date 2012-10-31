package com.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ServiceFactoryBean<T> extends AbstractFactoryBean 
								   implements BeanNameAware {
	String beanName;
    Class<T> interfaceClass;
    InvocationHandler invocationHandler;
	
	public ServiceFactoryBean(Class<T> interfaceClass, 
							  InvocationHandler invocationHandler){
        this.interfaceClass = interfaceClass;
        this.invocationHandler = invocationHandler;		
	}
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}
    protected Object createInstance() throws Exception {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
        		                      new Class[]{interfaceClass},
                                      this.invocationHandler);
		/*IProxy iProxy = (IProxy)Proxy.newProxyInstance(
		IProxy.class.getClassLoader(), 
		new Class[]{IProxy.class}, 
		new ProxyClass()); 
		iProxy.doSomething();*/        
    }
	@Override
	public Class<T> getObjectType() {
		return interfaceClass;	
	}
}
