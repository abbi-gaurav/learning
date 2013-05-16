package threading;

public class LazySingleton {
	public static LazySingleton getInstance(){
		return SingletonHolder.getInstance();
	}
	
	private LazySingleton(){}
	
	private static class SingletonHolder{
		private static final LazySingleton singleton = new LazySingleton();
		
		private static LazySingleton getInstance(){
			return singleton;
		}
	}
}
