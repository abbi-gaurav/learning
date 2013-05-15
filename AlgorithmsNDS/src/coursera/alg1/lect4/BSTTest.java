package coursera.alg1.lect4;


public class BSTTest extends SymbolTableTest<BST<String,Integer>>{

	public BSTTest(String fileName, int count, String word,int minLen) {
		super(fileName, new BST<String, Integer>(), count, word, minLen);
	}
}
