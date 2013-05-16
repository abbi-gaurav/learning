package threading.samples;


public class UnSafeBankAccount {
	private int balance;
	public UnSafeBankAccount(int balance) {
		this.balance = balance;
	}
	public void deposit(int amount) throws InterruptedException {
		balance +=  amount;
		//		System.out.println("deposit Thread["+Thread.currentThread().getId()+" balance " +
		//				"["+balance+"]");
	}
	public void withdraw(int amount) throws InterruptedException {
		deposit(-amount);
//		System.out.println("withdraw Thread["+Thread.currentThread().getId()+" balance " +
//				"["+balance+"]");
	}
	public int getBalance() throws InterruptedException {
		return balance; 
	}
}