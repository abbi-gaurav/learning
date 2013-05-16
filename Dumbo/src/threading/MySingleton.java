package threading;

public class MySingleton {
	private static MySingleton singleton;
	//TODO: break it
	private MySingleton() {}
	
	public static MySingleton getSingleton(){
		if(singleton == null){
			synchronized(MySingleton.class){
				if(singleton == null){
					singleton = new MySingleton();
				}
			}
		}
		return singleton;
	}
	
	public static void main(String[] args) {
		for(int i=0; i < 2;i++){
			new Thread(){
				public void run() {
					MySingleton.getSingleton();
				};
			}.start();
		}
	}
}
