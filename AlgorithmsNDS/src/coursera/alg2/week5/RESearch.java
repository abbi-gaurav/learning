package coursera.alg2.week5;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;
import coursera.alg2.week1.Bag;
import coursera.alg2.week1.DiGraph;
import coursera.alg2.week1.DirectedDFS;

public class RESearch {
	
	private final  int m;
	private final char[] re;
	private DiGraph epsilonXT;

	public RESearch(String regex) {
		this.m = regex.length();
		this.re = regex.toCharArray();
		
		this.epsilonXT = buildEpsilonTransitions();
	}
	public boolean recognizes(String txt){
		Bag<Integer> possibleStates = new Bag<>();
		DirectedDFS dfs = new DirectedDFS(epsilonXT, 0);
		
		//add possible states at start
		addPossibleStates(possibleStates, dfs);
		
		for(int i=0;i<txt.length();i++){
			Bag<Integer> match = new Bag<>();

			for(int v:possibleStates){
				if(v == m){
					continue;
				}

				if(re[v] == txt.charAt(i)||re[v] == '.'){
					match.add(v+1);
				}
			}

			dfs = new DirectedDFS(epsilonXT, match);
			possibleStates = new Bag<>();
			addPossibleStates(possibleStates, dfs);
		}
		
		for(int v:possibleStates){
			if(v == m){
				return true;
			}
		}
		return false;
	}
	private void addPossibleStates(Bag<Integer> possibleStates, DirectedDFS dfs) {
		for(int v=0;v<epsilonXT.getV();v++){
			if(dfs.hasPathTo(v)){
				possibleStates.add(v);
			}
		}
	}
	
	private DiGraph buildEpsilonTransitions() {
		DiGraph dg = new DiGraph(m+1);
		Stack<Integer> stack = new LinkedStack<>();
		
		for(int i=0;i<m;i++){
			int lp = i;
			int or;
			
			if(re[i] == '(' || re[i] == '|'){
				stack.push(i);
			}else if(re[i] == ')'){
				or = stack.pop();
				if(re[or] == '|'){
					lp = stack.pop();
					dg.addEdge(or, i);
					dg.addEdge(lp, or+1);
				}else{
					lp = or;
				}
			}
			
			if(i < m-1 && re[i+1] == '*'){
				dg.addEdge(lp, i+1);
				dg.addEdge(i+1, lp);
			}
			if(re[i] == ')' || re[i] == '*' || re[i] == '('){
				dg.addEdge(i, i+1);
			}
		}
		return dg;
	}
}
