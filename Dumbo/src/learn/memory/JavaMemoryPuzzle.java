package learn.memory;

public class JavaMemoryPuzzle {
	private final int dataSize =
		(int) (Runtime.getRuntime().maxMemory() * 0.6);

	public void f() {
		{
			byte[] data = new byte[dataSize];
		}
		int i =0;
		//Runtime.getRuntime().gc();
		byte[] data2 = new byte[dataSize];
	}

	public static void main(String[] args) {
		JavaMemoryPuzzle jmp = new JavaMemoryPuzzle();
		jmp.f();
		System.out.println("No OutOfMemoryError");
	}
}

