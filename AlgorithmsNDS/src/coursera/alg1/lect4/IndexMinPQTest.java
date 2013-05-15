package coursera.alg1.lect4;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Test;


public class IndexMinPQTest {
	public static final Map<String, Integer> map = new TreeMap<>();
	static{
		map.put("E",0);
		map.put("D",2);
		map.put("C",3);
		map.put("Z",4);
		map.put("A",5);
	}
	@Test
	public void test(){
		IndexMinPQ<String> indexMinPQ = new IndexMinPQ<String>(7);
		for(Entry<String, Integer> entry:map.entrySet()){
			indexMinPQ.insert(entry.getValue(), entry.getKey());
		}
		
		Assert.assertEquals(map.get("A").intValue(), indexMinPQ.delMin());
	}
}
