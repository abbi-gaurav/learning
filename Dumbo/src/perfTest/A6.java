package perfTest;

public class A6 implements Test {
	private final B6 b;
	public A6(B6 b) {
		this.b = b;
	}
	public void run() {
		b.f();
	}
	public String description() {
		return "Poly-Morphic: Four subclasses, via interface";
	}
}

interface B6 {
	public void f();
}

class C6 implements B6 {
	public void f() {
	}
}

class D6 implements B6 {
	@Override
	public void f() {
	}	
}

class E6 implements B6{
	@Override
	public void f() {
		
	}
}

class F6 implements B6{
	@Override
	public void f() {
		
	}
}

class G6 implements B6{
	@Override
	public void f() {
		
	}
}