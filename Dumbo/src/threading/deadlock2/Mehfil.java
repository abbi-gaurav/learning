package threading.deadlock2;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mehfil {
	private final Whiskey[] glasses;
	private final Thinker[] bewade;
	
	public Mehfil(int numOfLog) {
		glasses = new Whiskey[numOfLog];
		bewade = new Thinker[numOfLog];
		
		for(int i =0;i<numOfLog;i++){
			glasses[i] = new Whiskey();
		}
		
		for(int i =0;i<numOfLog;i++){
			Whiskey left = glasses[i];
			Whiskey right = glasses[(i+1)%numOfLog];
			bewade[i] = new Thinker(i, left, right);
		}
	}
	
	public void run() throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<String> results = new ExecutorCompletionService<String>(exec);
		for(Thinker bewada:bewade){
			results.submit(bewada);
		}
		
		System.out.println("waiting for results");
		for(int i =0;i<bewade.length;i++){
			try {
				System.out.println(results.take().get());
			} catch (ExecutionException e) {
				e.getCause().printStackTrace();
			}
		}
		exec.shutdown();
	}
	
	
}
