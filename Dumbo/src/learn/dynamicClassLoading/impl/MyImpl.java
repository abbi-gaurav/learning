package learn.dynamicClassLoading.impl;

import learn.dynamicClassLoading.interfaces.MyInterface;

public class MyImpl implements MyInterface{
	@Override
	public void doSomething() {
		System.out.println("Not Dynamically doing something");
	}
}
