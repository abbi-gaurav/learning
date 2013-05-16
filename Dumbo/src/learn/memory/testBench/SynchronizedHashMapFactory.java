package learn.memory.testBench;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedHashMapFactory implements ObjectFactory<Map<String, String>>{
	@Override
	public Map<String, String> makeObject() {
		return Collections.synchronizedMap(new HashMap<String,String>());
	}

}
