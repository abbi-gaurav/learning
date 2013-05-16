package test.threadLocal;

public class LearnThreadLocal {
	private static final ThreadLocal<Integer> threadlocal = new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return 0;
		};
	};
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Worker(i).start();
		}
	}
	
	static class Worker extends Thread{
		private Integer number;
		public Worker(Integer number) {
			this.number = number;
		}

		@Override
		public void run() {
			System.out.println("Setting Value for thread["+Thread.currentThread()+"] " +
																	"is["+number+"]");
			threadlocal.set(number);
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				System.out.println("Getting Value for thread["+Thread.currentThread()+"]" +
																"is["+threadlocal.get()+"]");
			}
			
		}
	}
}
