package learn.memory.testBench;

import java.util.Map;

public class FullMapObjectFactory implements ObjectFactory<Map<String, String>>{
	private final ObjectFactory<Map<String, String>> factory;
	
	public FullMapObjectFactory(ObjectFactory<Map<String, String>> factory) {
		this.factory = factory;
	}
	@Override
	public Map<String, String> makeObject() {
		return fill(factory.makeObject());
	}
	private Map<String, String> fill(Map<String, String> map) {
		for(int i=-128;i<128;i++){
			map.put(new String(Integer.toString(i)), new String("Value"));
		}
		return map;
	}

}
