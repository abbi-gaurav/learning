package learn.memory.testBench;

import java.util.HashMap;
import java.util.Map;

public class HashmapFactory implements ObjectFactory<Map<String, String>>{
	@Override
	public Map<String, String> makeObject() {
		return new HashMap<String, String>();
	}

}
