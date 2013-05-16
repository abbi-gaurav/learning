package perfTest;

public class A4 implements Test {
	private final B4 b;
	public A4(B4 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "Poly-Morphic: Three subclasses, via interface";
	}
}

interface B4 {
	public void f();
}

class C4 implements B4 {
	public void f() {
	}
}

class D4 implements B4 {
	@Override
	public void f() {
	}	
}

class E4 implements B4{
	@Override
	public void f() {
		
	}
	
}
