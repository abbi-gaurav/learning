package learn.memory.testBench;

public class BasicObjectFactory implements ObjectFactory<Object>{

	@Override
	public Object makeObject() {
		return new Object();
	}
	

}
