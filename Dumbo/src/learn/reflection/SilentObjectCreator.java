package learn.reflection;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import sun.reflect.ReflectionFactory;

public class SilentObjectCreator {
	public static<T> T create(Class<T> klazz){
		return create(klazz,Object.class);
	}

	@SuppressWarnings("rawtypes")
	public static<T> T create(Class<T> klazz,Class<? super T> parent){

		ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
		Constructor objDef;
		try {
			objDef = parent.getDeclaredConstructor();
			Constructor instConstructor = factory.newConstructorForSerialization(klazz,objDef);
			return klazz.cast(instConstructor.newInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	public static void main(String[] args) {
		MySerializable ms = SilentObjectCreator.create(MySerializable.class, NotSerializable.class);
		System.out.println("------------------");
		MySerializable ms2 = SilentObjectCreator.create(MySerializable.class);
	}

}
class NotSerializable{
	public NotSerializable() {
		System.out.println("NotSerializable Constructor");
	}
}

class MySerializable extends NotSerializable implements Serializable{

	private static final long serialVersionUID = -6864948297838777730L;

	public MySerializable() {
		System.out.println("MySerializable Constructor");
	}
}
