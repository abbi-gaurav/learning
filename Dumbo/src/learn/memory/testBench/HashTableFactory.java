package learn.memory.testBench;


public class HashTableFactory implements ObjectFactory<java.util.Map<String, String>>{
	@Override
	public java.util.Map<String, String> makeObject() {
		return new java.util.Hashtable<String, String>();
	}

}
