package learn.dynamicProxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyHandler implements InvocationHandler{
	private Object target;
	public MyHandler() {}
	public MyHandler(Object target){
		this.target = target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return target!= null?method.invoke(target, args):null;
	}
	
	public static void main(String[] args) {
		System.out.println(((IA)Proxy.newProxyInstance(MyHandler.class.getClassLoader(), 
				new Class[]{IA.class}, 
				new MyHandler())).m());
		System.out.println(((IA)Proxy.newProxyInstance(MyHandler.class.getClassLoader(), 
				new Class[]{IA.class}, 
				new MyHandler(new IAImpl()))).m());
	}
}

interface IA{
	String m();
}

class IAImpl implements IA{

	@Override
	public String m() {
		return "this impl";
	}
}
