package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.DiGraph;
import coursera.alg2.week1.StronglyConnectedDiGraph;

@RunWith(Parameterized.class)
public class StronglyConnectedDiGraphTest {
	private DiGraph diGraph;
	private final int expectedStcc;

	public StronglyConnectedDiGraphTest(String fileName, int stCC) {
		this.diGraph = (DiGraph) GraphTestUtils.createGraph(fileName, true);
		expectedStcc = stCC;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"tinyDG.txt",5}});
	}
	
	@Test
	public void test(){
		StronglyConnectedDiGraph st = new StronglyConnectedDiGraph(diGraph);
		Assert.assertEquals(expectedStcc, st.count());
	}
}
