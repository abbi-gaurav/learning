package test.threads;

public class TestLock {
	public static class SharedObj{
		private int count = 10;
		//private final Boolean lock = Boolean.FALSE;
		private Boolean lock = Boolean.FALSE;
		public int getCount() {
			synchronized (lock) {
				return count;
			}
		}

		public void setCount(int count) {
			synchronized (lock) {
				this.count = count;
				lock = Boolean.TRUE;
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static class Testable extends Thread{
		private SharedObj obj;
		private boolean isSet;
		public Testable(SharedObj obj,boolean isSet) {
			this.obj = obj;
			this.isSet= isSet;
		}
		@Override
		public void run() {
			if(isSet){
				int count = 7;
				System.out.println("Setting count ["+count+"] for thread["+this.getName()+"]");
				obj.setCount(count);
			}else{
				System.out.println("Count i got is["+obj.getCount()+"] for thread["+getName()+"]");
			}
		}
	}
	
	public static void main(String[] args) {
		SharedObj obj = new SharedObj();
		new Testable(obj , true).start();
		new Testable(obj , false).start();
	}
}
