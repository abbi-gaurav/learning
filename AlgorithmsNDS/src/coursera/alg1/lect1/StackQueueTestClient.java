package coursera.alg1.lect1;
import coursera.alg1.lect2.FixedCapacityArrayStack;
import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.ResizableArrayBasedQueue;
import coursera.alg1.lect2.ResizingArrayBasedStack;
import edu.princeton.cs.introcs.StdIn;

public class StackQueueTestClient {
	public static void main(String... args) {
//		testLinkedStack(args);
//		FtestArrayStack(args);
//		testResizeArrayStack(args);
		
		testResizableQueue(args);
	}

	private static void testResizableQueue(String[] args) {
		testQueue(new ResizableArrayBasedQueue<String>(),args);
	}

	private static void testResizeArrayStack(String[] args) {
		testStack(new ResizingArrayBasedStack(), args);
	}

	private static void testArrayStack(String[] args) {
		testStack(new FixedCapacityArrayStack(10), args);
	}

	private static void testLinkedStack(String...args) {
		testStack(new LinkedStack<String>(), args);
	}

	private static void testStack(coursera.alg1.lect2.Stack<String> stack, String...args) {
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			if(s.equals("-")){
				System.out.println(stack.pop());
			}else{
				stack.push(s);
			}
		}
	}
	
	private static void testQueue(coursera.alg1.lect2.Queue<String> queue, String...args) {
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			if(s.equals("-")){
				System.out.println(queue.dequeue());
			}else{
				queue.enqueue(s);
			}
		}
	}
}
