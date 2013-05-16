package learn.innerClass;

public class ThisEscapeTest {
	public static void main(String[] args) {
		EventSource source = new EventSource();
		source.start();
		while(true){
			new ThisEscape(source);
		}
	}
}
