package com.proxy;

import com.myannotations.Service;

public interface IProxy {
	@Service(name = "atest", pattern = "$code/$value")
	public String doSomething(String code,String value);

	@Service(name = "btest", pattern = "$code/$value/$someOtherValue")
	public String doSomethingElse(String code,String value,String otherValue);
}
