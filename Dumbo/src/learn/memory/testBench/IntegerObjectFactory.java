package learn.memory.testBench;

public class IntegerObjectFactory implements ObjectFactory<Integer>{

	@Override
	public Integer makeObject() {
		return new Integer(124);
	}

}
