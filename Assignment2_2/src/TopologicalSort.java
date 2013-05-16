
public class TopologicalSort {
	private boolean[][] marked;
	private Stack<Integer> stack = new Stack<Integer>();
	
	public TopologicalSort(int w, int h) {
		marked = new boolean[h][w];
		for(int i = 0; i<h;i++){
			for(int j =0; j<w;j++){
				if(!marked[i][j]){
					dfs(i,j, h, w);
				}
			}
		}
	}


	private void dfs(int i, int j, int h, int w) {
		marked[i][j] = true;
		if(i == h-1){
			stack.push(w*i+j);
			return;
		}
		for(int adj:Utils.getadj(w, j)){
			if(!marked[i+1][adj]){
				dfs(i+1, adj, h, w);
			}
		}
		stack.push(w*i+j);
	}
	
	public static void main(String[] args) {
		System.out.println(new TopologicalSort(3, 7).stack);
	}


	public Iterable<Integer> order() {
		return stack;
	}
}
