package learn.dynamicClassLoading;

import learn.dynamicClassLoading.interfaces.MyInterface;

public class MyTestHelper extends Abstracthelper implements Helpable<MyInterface>{
	public MyTestHelper(String jarPaths) {
		super(jarPaths);
	}

	public MyInterface test() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		MyInterface worker;
		setCL();
		worker = getImpl();
		worker.doSomething();
		unsetCL();
		return worker;
	}

	private MyInterface getImpl() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		return (MyInterface) contextClassLoader
				.loadClass("learn.dynamicClassLoading.impl.MyImpl").newInstance();
	}
}
