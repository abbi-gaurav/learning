package perfTest;

public class A3 implements Test {
	private final B3 b;
	public A3(B3 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "Bi-Morphic: Two subclasses, via interface";
	}
}

interface B3 {
	public void f();
}

class C3 implements B3 {
	public void f() {
	}
}

class D3 implements B3 {
	@Override
	public void f() {
	}
	
}
