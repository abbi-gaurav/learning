package security;

import java.lang.reflect.Field;

public class ReflectionAccess {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> klazz = Class.forName("security.Sample");
		
		for (Field field : klazz.getDeclaredFields()){
			System.out.println(field.getName());
		}
		
		
	}
}
