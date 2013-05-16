package training;

import java.util.Set;
import java.util.TreeSet;

public class SetExample {
	public static void main(String[] args) {
		Set<String> stringSet = new TreeSet<String>();
		System.out.println(stringSet.add("This"));
		System.out.println(stringSet.add("This"));
		System.out.println(stringSet.add("is"));
		System.out.println(stringSet.add("a"));
		System.out.println(stringSet.add("Set example"));
		
		System.out.println(stringSet);
	}
	
}
