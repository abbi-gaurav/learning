package learn.dynamicClassLoading;

public interface Helpable<T> {
	T test() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
}
