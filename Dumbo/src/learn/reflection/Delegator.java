package learn.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Delegator {
	private final Object source;
	private final Object delegatee;
	private final Class<?> superClass;

	public Delegator(Object source,Object delegatee, Class<?> superClass) {
		this.source = source;
		this.delegatee = delegatee;
		this.superClass = superClass;
	}

	public Delegator(Object source,String delegateeClassName, Class<?> superClass) {
		this.source = source;
		this.superClass = superClass;
		try {
			Class<?> implClass = Class.forName(delegateeClassName);
			Constructor<?> delegateeConstructor = implClass.getDeclaredConstructor();
			delegateeConstructor.setAccessible(true);
			this.delegatee = delegateeConstructor.newInstance();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new DelegationException("delegatee object nahin banda",e);
		}
	}

	public final <T> T invoke(Object...args){
		try{
			String methodName = extractMethodName();
			Method method = findMethod(methodName,args);
			@SuppressWarnings("unchecked")
			T t = (T)invoke0(method,args);
			return t;
		}catch (NoSuchMethodException e) {
			throw new DelegationException(e);
		}
	}

	private Object invoke0(Method method, Object[] args) {
		try {
			writeFields(superClass,source,delegatee);
			method.setAccessible(true);
			Object result = method.invoke(delegatee, args);
			writeFields(superClass, delegatee, source);
			return result;
		}catch (RuntimeException e) {
			throw e;
		}catch (InvocationTargetException e) {
			throw new DelegationException(e.getCause());
		} catch (Exception e) {
			throw new DelegationException(e);
		}
	}

	private void writeFields(Class<?> clazz, Object from,Object to) 
	throws Exception{
		for(Field field:clazz.getDeclaredFields()){
			field.setAccessible(true);
			field.set(to, field.get(from));
		}
	}

	private Method findMethod(String methodName, Object[] args) throws NoSuchMethodException {
		if(args.length == 0){
			return superClass.getDeclaredMethod(methodName);
		}
		Method match = null;
		next:
			for(Method method:superClass.getDeclaredMethods()){
				if(methodName.equals(method.getName())){
					Class<?> parameterClasses[] = method.getParameterTypes();
					if(parameterClasses.length == args.length){
						for (int i = 0; i < parameterClasses.length; i++) {
							Class<?> argType = convertPrimitiveClass(parameterClasses[i]);
							if(!argType.isInstance(parameterClasses[i])) continue next;
						}
						if(match == null){
							match = method;
						}else{
							throw new DelegationException("Duplicate matches["+methodName+"]");
						}
					}
				}
			}
		if(match != null){
			return match;
		}else{
			throw new DelegationException("No method found for["+methodName+"]");
		}
	}

	private Class<?> convertPrimitiveClass(Class<?> argType) {
		if(argType.isPrimitive()){
			if(argType == int.class) return Integer.class;

			if(argType == long.class) return Long.class;

			if(argType == float.class) return Float.class;

			if(argType == double.class) return Double.class;

			if(argType == boolean.class) return Boolean.class;

			if(argType == char.class) return Character.class;

			if(argType == short.class) return Short.class;

			if(argType == byte.class) return Byte.class;
		}
		return argType;
	}

	private String extractMethodName() {
		Throwable thr = new Throwable();
		String methodName = thr.getStackTrace()[2].getMethodName();
		return methodName;
	}

	public DelegateMethodfinder delegateTo(String methodName,Class<?>... parameters){
		return new DelegateMethodfinder(methodName,parameters);
	}

	public class DelegateMethodfinder{
		private final Method method;

		public DelegateMethodfinder(String methodName, Class<?>[] parameters) {
			try{
				method = superClass.getDeclaredMethod(methodName, parameters);
			}catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new DelegationException(e);
			}
		}
		
		public <T> T invoke(Object... parameterValues){
			@SuppressWarnings("unchecked")
			T t = (T) Delegator.this.invoke0(method, parameterValues);
			return t;
		}
	}
}
