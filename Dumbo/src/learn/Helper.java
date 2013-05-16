package learn;

public class Helper {
	public static void checkNullability(Object object, String message){
		if(object == null){
			throw new RuntimeException(message);
		}
	}
}
