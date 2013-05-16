import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordNet {

	private final Map<String, List<Integer>> nounsST;
	private final Digraph diGraph;
	private final SAP sap;
	private final Map<Integer, String> synsetMap;

	public WordNet(String synsets, String hypernyms) {
		In syn = new In(synsets);
		In hns = new In(hypernyms);

		nounsST = new HashMap<String, List<Integer>>();
		this.synsetMap = new HashMap<Integer, String>();

		int max = readSyn(nounsST, syn, synsetMap);

		diGraph = new Digraph(max + 1);
		readHypernms(diGraph, hns);
		
		validate();
		sap = new SAP(diGraph);
	}

	private void validate() {
		if(new DirectedCycle(diGraph).hasCycle()){
			throw new IllegalArgumentException("cyclic");
		}
		Topological topological = new Topological(diGraph);
		if(!topological.hasOrder()){
			throw new IllegalArgumentException();
		}
		
		Iterator<Integer> order = topological.order().iterator();
		int root;
		
		while(true){
			root = order.next();
			if(!order.hasNext()){
				break;
			}else{
				if(!diGraph.adj(root).iterator().hasNext()){
					throw new IllegalArgumentException("not rooted");
				}
			}
		}
		
		if(diGraph.adj(root).iterator().hasNext()){
			throw new IllegalArgumentException();
		}
	}

	private static void readHypernms(Digraph diGraph, In hns) {
		while (!hns.isEmpty()) {
			String[] ints = hns.readLine().split(",");
			int v = Integer.parseInt(ints[0]);

			for (int w = 1; w < ints.length; w++) {
				diGraph.addEdge(v, Integer.parseInt(ints[w]));
			}
		}
	}

	public Iterable<String> nouns() {
		return nounsST.keySet();
	}

	public boolean isNoun(String word) {
		return nounsST.containsKey(word);
	}

	public int distance(String nounA, String nounB) {
		validate(nounA);
		validate(nounB);

		return sap.length(nounsST.get(nounA), nounsST.get(nounB));
	}

	public String sap(String nounA, String nounB) {
		validate(nounA);
		validate(nounB);

		int ancestor = sap.ancestor(nounsST.get(nounA), nounsST.get(nounB));
		return synsetMap.get(ancestor);
	}

	private void validate(String noun) {
		if (!nounsST.containsKey(noun)) {
			throw new IllegalArgumentException(noun + " not in data");
		}
	}

	private static int readSyn(Map<String, List<Integer>> symbolTable, In syn,
			Map<Integer, String> synsetMap) {
		int max = 0;
		while (!syn.isEmpty()) {
			String str[] = syn.readLine().split(",");
			int v = Integer.parseInt(str[0]);

			if (v > max) {
				max = v;
			}

			synsetMap.put(v, str[1]);
			String[] nouns = str[1].split("\\s+");
			for (String noun : nouns) {
				List<Integer> intList = symbolTable.get(noun);

				if (intList == null) {
					intList = new ArrayList<Integer>();
					symbolTable.put(noun, intList);
				}
				intList.add(v);
			}
		}
		return max;
	}

	public static void main(String[] args) {
		WordNet wd = new WordNet(args[0], args[1]);
		assert (wd.nounsST.get("word").size() == 8);
		assert (wd.diGraph.V() == 82192);
		for (int w : wd.diGraph.adj(164)) {
			assert (Arrays.binarySearch(new int[] { 21012, 56099 }, w) != -1);
		}
		int distanceht = wd.distance("horse", "table");
		int hc = wd.distance("horse", "cat");
		assert (distanceht > hc);
	}
}
