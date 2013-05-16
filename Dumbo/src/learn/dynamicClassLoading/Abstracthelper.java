package learn.dynamicClassLoading;

public class Abstracthelper {

	protected final MyClassLoader classLoader;
	protected final ClassLoader origLoader;
	
	public Abstracthelper(String jarPaths) {
		origLoader = Thread.currentThread().getContextClassLoader();
		classLoader = new MyClassLoader(jarPaths,origLoader);
	}

	protected void unsetCL() {
		Thread.currentThread().setContextClassLoader(origLoader);
	}

	protected void setCL() {
		Thread.currentThread().setContextClassLoader(classLoader);
	}
}
