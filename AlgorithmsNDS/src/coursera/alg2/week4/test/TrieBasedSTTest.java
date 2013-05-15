package coursera.alg2.week4.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week4.RWayTriesST;
import coursera.alg2.week4.TernarySearchTriesST;
import edu.princeton.cs.introcs.In;

@RunWith(Parameterized.class)
public class TrieBasedSTTest {
	
	private static final String DIR = "/home/gaurav_abbi/learning/coursera/data/algs4-data/";
	private final In in;
	private int count;
	private final RWayTriesST<Integer> rWayST;
	private final TernarySearchTriesST<Integer> ternarySearchST;
	private String verifier;
	
	public TrieBasedSTTest(String fileName, String verifier) {
		this.in = new In(DIR+fileName);
		this.rWayST = new RWayTriesST<>();
		this.ternarySearchST = new TernarySearchTriesST<>(); 
		this.verifier = verifier;
		fill();
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{/*{"mobydick2.txt","interest"},*/{"sheSells.txt","seashells"}});
	}
	
	@Test
	public void test(){
		junit.framework.Assert.assertEquals(rWayST.get(verifier),ternarySearchST.get(verifier));
	}
	
	@Test
	public void testKeys(){
		for(String key:rWayST.keys()){
			System.out.print(key+" ");
		}
	}
	
	@Test
	public void testPrefix(){
		org.junit.Assert.assertEquals(3, TestUtils.count(rWayST.keysWithPrefix("se")));
	}
	
	private void fill() {
		while(!in.isEmpty()){
			String readString = in.readString();
			rWayST.put(readString, ++count);
			try{
				if(readString.equals("ago")){
					System.out.println();
				}
				ternarySearchST.put(readString, count);
			}catch(StringIndexOutOfBoundsException e){
				System.out.println(readString);
				throw e;
			}
		}
	}
	
}
