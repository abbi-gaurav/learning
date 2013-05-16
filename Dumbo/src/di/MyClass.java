package di;

import java.util.List;

public class MyClass {
	public void doSomething(){
		HeavyClass h = Dependencies.getHeavyClassInstance(); 
			
			//new HeavyClass();
		List<String> result = h.executionTakes15Mins();		
		doAdvancedSortingOn(result);
		//More actions here
	}

	private void doAdvancedSortingOn(List<String> result) {
		/* This is really complex*/
	}
}
