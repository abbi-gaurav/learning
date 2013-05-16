package test;

public class JavaOperator {
	 /*public static final int DEFAULT=0;
	    public static final int REQUEST=1;
	    public static final int FORWARD=2;
	    public static final int INCLUDE=4;
	    public static final int ERROR=8;
	    public static final int ALL=15;*/
	public static void main(String[] args) {
		System.out.println(2&15);
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
