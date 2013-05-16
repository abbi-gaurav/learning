package learn.memory.testBench;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapFactory implements ObjectFactory<Map<String, String>>{

	@Override
	public Map<String, String> makeObject() {
		return new ConcurrentHashMap<String, String>();
	}

}
