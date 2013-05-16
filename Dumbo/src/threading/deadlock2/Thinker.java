package threading.deadlock2;

import java.util.concurrent.Callable;

public class Thinker implements Callable<String>{
	private final int id;
	private final Whiskey leftGlass;
	private final Whiskey rightGlass;
	
	public Thinker(int id, Whiskey lefGlass, Whiskey right) {
		this.id = id;
		this.leftGlass = lefGlass;
		this.rightGlass = right;
	}
	
	public String call(){
		for(int i = 0;i<1000;i++){
			try {
				noDeadlockDrink();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			think();
		}
		return "Sari darooo pee layi";
	}
	private void think() {
		System.out.println(id+" is thinking");
	}

	private void drink(){
		synchronized (leftGlass) {
			synchronized (rightGlass) {
				System.out.printf("%d is drinking%n",id);
			}
		}
	}
	
	private void noDeadlockDrink() throws InterruptedException{
		while(true){
			if(Thread.interrupted()){
				throw new InterruptedException();
			}
			if(leftGlass.tryLock()){
				try{
					if(rightGlass.tryLock()){
						try{
							System.out.printf("%d is drinking%n",id);
							return;
						}finally{
							rightGlass.unlock();
						}
					}
				}finally{
					leftGlass.unlock();
				}
			}
			sleepRandomTime();
		}
	}

	private void sleepRandomTime() throws InterruptedException {
		Thread.sleep(700);
	}
	
}
