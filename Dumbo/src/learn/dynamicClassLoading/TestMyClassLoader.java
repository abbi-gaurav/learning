package learn.dynamicClassLoading;

public class TestMyClassLoader {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//new MyTestHelper("/home/gauravabbi/learning/dcl/dynamic.jar").test();
		MySchemaResolver mySchemaResolver = new MySchemaResolver("/home/gauravabbi/learning/dcl/schema.jar");
		mySchemaResolver.test();
		mySchemaResolver.test();
	}

}
