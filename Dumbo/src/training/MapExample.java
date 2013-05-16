package training;

import java.util.HashMap;
import java.util.Map;

public class MapExample {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("One", 1);
		map.put("Two", 2);
		map.put("Three", 3);
		
		System.out.println(map);
		
		map.put("Three", 4);
		
		System.out.println(map);
	}
}
