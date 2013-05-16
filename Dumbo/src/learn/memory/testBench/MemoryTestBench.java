package learn.memory.testBench;

public class MemoryTestBench {
	public <T>void  showMemoryUsage(ObjectFactory<T> factory){
		long mem = calculateMemoryUsage(factory);
		System.out.println(factory.getClass().getSimpleName()+" produced " +
				factory.makeObject().getClass().getSimpleName()+" which took "+
				mem+" bytes");
	}

	@SuppressWarnings("unused")
	private<T> long calculateMemoryUsage(ObjectFactory<T> factory) {
		T object = factory.makeObject();
		long memory = usedMemory();
		object = null;
		lotsOfGC();
		
		memory = usedMemory();
		object = factory.makeObject();
		lotsOfGC();
		return usedMemory()-memory;
	}

	private void lotsOfGC() {
		for(int i = 0; i<25;i++){
			System.gc();
			try {
				Thread.sleep(97);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private long usedMemory() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	public static void main(String[] args) {
		MemoryTestBench memoryTestBench = new MemoryTestBench();
		
		memoryTestBench.showMemoryUsage(new BasicObjectFactory());
		memoryTestBench.showMemoryUsage(new IntegerObjectFactory());
		memoryTestBench.showMemoryUsage(new LongObjectFactory());
		
		memoryTestBench.showMemoryUsage(new HashmapFactory());
		memoryTestBench.showMemoryUsage(new SynchronizedHashMapFactory());
		memoryTestBench.showMemoryUsage(new HashTableFactory());
		memoryTestBench.showMemoryUsage(new ConcurrentHashMapFactory());
		
		System.out.println("Full HashMap");
		memoryTestBench.showMemoryUsage(new FullMapObjectFactory(new HashmapFactory()));
		System.out.println("Full SynchronizedhashMap");
		memoryTestBench.showMemoryUsage(new FullMapObjectFactory(new SynchronizedHashMapFactory()));
		System.out.println("Full Hashtable");
		memoryTestBench.showMemoryUsage(new FullMapObjectFactory(new HashTableFactory()));
		System.out.println("Full ConcurrentHashMap");
		memoryTestBench.showMemoryUsage(new FullMapObjectFactory(new ConcurrentHashMapFactory()));
	}
}
