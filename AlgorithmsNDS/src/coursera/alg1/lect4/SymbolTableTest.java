package coursera.alg1.lect4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdRandom;

@RunWith(Parameterized.class)
public abstract class SymbolTableTest<X extends SymbolTable<String, Integer>> {
	private static final String DIR="/home/gaurav_abbi/learning/coursera/data/algs4-data";
	private final String fileName;
	private final X st;
	private final int expectedCount;
	private final String word;
	private final int minLen;
	private static final int TEST_SIZE = 1024*1024;
	
	public SymbolTableTest(String fileName, X st, int expected, String word, int minLen) {
		this.fileName = fileName;
		this.st = st;
		this.expectedCount = expected;
		this.word = word;
		this.minLen = minLen;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		Object[][] data = new Object[][] { { "tinyTale.txt", 10 ,"it",1},
				/*{ "tale.txt", 122 ,"business",8},
				{ "leipzig1M.txt", 24763 ,"government",10}*/};
		return Arrays.asList(data);
	}
	
	@Test
	public void testFrequency() {
		readFrmFile();
		findMaxFreq();
	}
	
	@Test
	public void testOrder(){
		BST<Integer, Integer> bst = creatBSTFromArray(1024);
		int counter = 0;
		for (Integer i : bst) {
			Assert.assertEquals(counter++, i.intValue());
		}
	}

	
	@Test
	public void testMax(){
		int length = 10;
		BST<Integer, Integer> bst = creatBSTFromArray(length);
		for(int i = length-1;i>=0;i-- ){
			Assert.assertEquals(i, bst.max().intValue());
			Assert.assertEquals(i+1, bst.size());
			bst.deleteMax();
		}
		
	}
	
	@Test
	public void testMin(){
		int length = 16;
		BST<Integer, Integer> bst = creatBSTFromArray(length);
		for(int i=0;i<length;i++){
			Assert.assertEquals(i, bst.min().intValue());
			Assert.assertEquals(length-i, bst.size());
			bst.deleteMin();
		}
	}
	
	@Test
	public void testFloor() {
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		Assert.assertEquals(TEST_SIZE-1, bst.floor(TEST_SIZE).intValue());
		for (int i = 0; i < 7;) {
			int random = StdRandom.uniform(1,TEST_SIZE);
			if (bst.get(random) != null) {
				bst.delete(random);
				Assert.assertEquals(random - 1, bst.floor(random).intValue());
				bst.put(random, random);
				i++;
			}
		}
	}
	
	@Test
	public void testCeiling(){
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		Assert.assertEquals(TEST_SIZE-1, bst.ceiling(TEST_SIZE-1).intValue());
		for (int i = 0; i < 7;) {
			int random = StdRandom.uniform(1,TEST_SIZE);
			if (bst.get(random) != null) {
				bst.delete(random);
				Assert.assertEquals(random + 1, bst.ceiling(random).intValue());
				bst.put(random, random);
				i++;
			}
		}
	}
	
	@Test
	public void testRank() {
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		Assert.assertEquals(TEST_SIZE - 1, bst.rank(TEST_SIZE - 1));
		
		for(int i=0; i<128;i++){
			int random = StdRandom.uniform(TEST_SIZE);
			Assert.assertEquals(random, bst.rank(random));
		}
	}
	
	@Test
	public void testRankBasedSelection(){
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		Assert.assertEquals(TEST_SIZE-1, bst.rankBasedSelection(TEST_SIZE-1).intValue());
		
		for(int i=0; i<128;i++){
			int random = StdRandom.uniform(1, TEST_SIZE);
			Assert.assertEquals(random, bst.rankBasedSelection(random).intValue());
		}
	}
	
	@Test
	public void testRangeCountHighExists(){
		Range<Integer> range = creatBSTFromArray(TEST_SIZE);
		Integer high = StdRandom.uniform(0, TEST_SIZE);
		Integer lo = StdRandom.uniform(0, high);
		Assert.assertEquals(high-lo+1 , range.rangeCount(lo, high));
		
	}
	
	@Test
	public void testRangeCountHighDoesNotExist(){
		BST<Integer, Integer> range = creatBSTFromArray(TEST_SIZE);
		Integer high = StdRandom.uniform(0, TEST_SIZE);
		Integer lo = StdRandom.uniform(0, high);
		range.delete(high);
		Assert.assertEquals(high-lo,range.rangeCount(lo, high));
		
	}
	@Test
	public void testRangeSearch(){
		Integer high = StdRandom.uniform(0,TEST_SIZE);
		Integer low = StdRandom.uniform(0, high); 
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		
		assertRange(high, low, bst);
	}
	
	@Test
	public void testRangeSearchWithHighDeleted(){
		Integer high = StdRandom.uniform(0,TEST_SIZE);
		Integer low = StdRandom.uniform(0, high);
		BST<Integer, Integer> bst = creatBSTFromArray(TEST_SIZE);
		bst.delete(high);
		
		assertRange(high, low, bst);
	}
	
	private void assertRange(Integer high, Integer low,
			BST<Integer, Integer> bst) {
		Iterable<Integer> range = bst.range(low, high);
		assertIterator(range, low, high);
	}
	private void assertIterator(Iterable<Integer> range, Integer low, Integer high) {
		Iterator<Integer> iterator = range.iterator();
		Assert.assertTrue(iterator.hasNext());
		while(iterator.hasNext()){
			int key = iterator.next();
			Assert.assertTrue("Range violation("+low+","+high+"] for value["+key+"]", low < key && high >= key);
		}
	}

	private BST<Integer, Integer> creatBSTFromArray(int length) {
		BST<Integer, Integer> bst = new BST<>();
		int[] array = TestHelper.getShuffledArray(length);
		for(int i=0;i<array.length;i++){
			Assert.assertEquals(i, bst.size());
			bst.put(array[i], i);
		}
		return bst;
	}
	
	private void readFrmFile() {
		In in = new In(DIR+"/"+fileName);
		while (!in.isEmpty()) {
			String s = in.readString();
			if(s.length() < minLen){
				continue;
			}
			Integer integer = st.get(s);
			if (integer == null) {
				st.put(s, 1);
			}else{
				st.put(s, integer+1);
			}
		}
	}
	
	private void findMaxFreq(){
		String max = "";
		st.put(max, 0);
		
		for (String key : st) {
			if(st.get(key) > st.get(max)){
				max = key;
			}
		}
		int actualCount = st.get(max).intValue();
		System.out.println("Max Count is: "+actualCount +" for string "+max);
		Assert.assertEquals(word, max);
		Assert.assertEquals(expectedCount, actualCount);
	}
	
	
}
