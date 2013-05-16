package training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListExample {
	public static void main(String[] args) {
		List<Integer> integerList = new ArrayList<Integer>();
		integerList.add(1);
		integerList.add(1);
		integerList.add(3);
		integerList.add(2);
		integerList.add(8);
		integerList.add(5);
		
		Collections.sort(integerList);
		System.out.println(integerList);
		
		Collections.sort(integerList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		System.out.println(integerList);
	}
}
