package threading.forkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaximumFinder extends RecursiveTask<Integer>{

	private static final long serialVersionUID = 1345185735094192111L;
	private static final int THRESHOLD = 7;
	private final int end;
	private final int start;
	private final  int[] data;
	
	private MaximumFinder(int[] data, int start, int end){
		this.data = data;
		this.start = start;
		this.end = end;
	}
	
	public MaximumFinder(int[] data){
		this(data,0,data.length);
	}
	
	@Override
	protected Integer compute() {
		final int length = end-start;
		if(length <= THRESHOLD){
			return computeDirectly();
		}
		
		final int split = start+length/2;
		
		final MaximumFinder left = fork(start, split);
		final MaximumFinder right = fork( split, end);
		
		return Math.max(right.join(), left.join());
	}

	private MaximumFinder fork(int low, final int high) {
		final MaximumFinder left = new MaximumFinder(data, low, high);
		left.fork();
		return left;
	}
	
	private int computeDirectly(){
		int max = data[start];
		for(int i=start+1; i<end;i++){
			if(data[i] > max){
				max = data[i];
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		int[] data = prepareData(args);
		
		usingFJP(data);
		
		direct(data);
	}

	private static void direct(int[] data) {
		long start = System.nanoTime();
		MaximumFinder task = new MaximumFinder(data);
		int result = task.computeDirectly();
		System.out.println("Time Took using direct:::"+(System.nanoTime()-start));
		System.out.println(result);
	}

	private static void usingFJP(int[] data) {
		long start = System.nanoTime();
		ForkJoinPool pool = new ForkJoinPool(4);
		MaximumFinder task = new MaximumFinder(data);
		Integer result = pool.invoke(task);
		System.out.println("Time Took using FJP:::"+(System.nanoTime()-start));
		System.out.println(result);
	}

	private static int[] prepareData(String[] args) {
		int length = 1000;
		if(args.length > 0){
			length = Integer.parseInt(args[0]);
		}
		Random random = new Random();
		int[] data = new int[length];
		
		for(int i=0;i<length;i++){
			data[i] = random.nextInt(length);
		}
		
		return data;
	}
	
}
