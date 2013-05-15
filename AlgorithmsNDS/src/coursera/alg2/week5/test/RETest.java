package coursera.alg2.week5.test;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week5.RESearch;

@RunWith(Parameterized.class)
public class RETest {

	private final String str;
	private final RESearch regex;
	private final boolean match;

	public RETest(String str, String regex, boolean match) {
		this.str = str;
		this.regex = new RESearch(regex);
		this.match = match;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"AABACACD","((A*B|AC)*D)",true},
				{"AABD","((A*B|AC)DI)",false}});
	}
	
	@Test
	public void test(){
		Assert.assertEquals(match, regex.recognizes(str));
	}
	
}
