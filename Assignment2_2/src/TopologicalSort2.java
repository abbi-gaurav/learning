
public class TopologicalSort2 {
	private boolean[] marked;
	private Stack<Integer> stack = new Stack<Integer>();
	
	public TopologicalSort2(int w, int h) {
		marked = new boolean[h*w+2];
		for(int i = 0; i<marked.length;i++){
				if(!marked[i]){
					dfs(i, h, w);
			}
		}
	}


	private void dfs(int i, int h, int w) {
		marked[i] = true;
		for(int adj:Utils.getadj2(w,h, i)){
			if(!marked[adj]){
				dfs(adj, h, w);
			}
		}
		stack.push(i);
	}
	
	public static void main(String[] args) {
		System.out.println(new TopologicalSort2(3, 7).stack);
	}


	public Iterable<Integer> order() {
		return stack;
	}
}
