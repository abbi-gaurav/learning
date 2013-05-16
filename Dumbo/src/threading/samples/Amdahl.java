package threading.samples;

public class Amdahl {
	public static void main(String[] args) {
		for(int i=1; i < 100000; i*= 3){
			System.out.println("speed(0.9,"+i+")--"+speed(0.1, i));
		}
	}
	
	private static double speed(double seq, int numOfProcessors){
		return 1.0 /(seq + (1-seq)/numOfProcessors);
	}
}
