package learn.memory.testBench;

public class LongObjectFactory implements ObjectFactory<Long>{

	@Override
	public Long makeObject() {
		return new Long(250l);
	}

}
