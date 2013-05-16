package perfTest;

public class A5 implements Test {
	private final B5 b;
	public A5(B5 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "Poly-Morphic: Four subclasses, via interface";
	}
}

interface B5 {
	public void f();
}

class C5 implements B5 {
	public void f() {
	}
}

class D5 implements B5 {
	@Override
	public void f() {
	}	
}

class E5 implements B5{
	@Override
	public void f() {
		
	}
}

class F5 implements B5{
	@Override
	public void f() {
		
	}
}