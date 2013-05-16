package perfTest;

public class A2 implements Test {
	private final B2 b;
	public A2(B2 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "Mono-Morphic: Single subclasses, via interface";
	}
}

interface B2 {
	public void f();
}

class C2 implements B2 {
	public void f() {
	}
}
