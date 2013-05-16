package perfTest;

public class A1 implements Test{
	private final C1 b;
	public A1(C1 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "No-morphic: Single subclass, pointed to directly";
	}
}

class C1 {
	public void f() {
	}
}
