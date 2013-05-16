
public class RQTestAB extends RQTestLL{

	public RQTestAB(int count) {
		super(count);
	}
	
	@Override
	RandomizedQueue<Integer> getRQ() {
		return new RandomizedQueueArrayBase<>();
	}
}
