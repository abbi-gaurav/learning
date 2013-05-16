package test;

public class D {
	static void m(){
		System.out.println("d.m");
	}
}

class E extends D{
	static void m(){
		System.out.println("E.m");
	}
	public static void main(String[] args) {
		m();
		E.m();
	}
}
