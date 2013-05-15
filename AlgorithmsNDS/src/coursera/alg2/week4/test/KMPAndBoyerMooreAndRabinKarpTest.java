package coursera.alg2.week4.test;

import java.util.Arrays;
import java.util.Collection;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week4.BoyerMoore;
import coursera.alg2.week4.KMP;
import coursera.alg2.week4.RabinKarp;

@RunWith(Parameterized.class)
public class KMPAndBoyerMooreAndRabinKarpTest {
	private final String str;
	private final KMP kmp;
	private final boolean match;
	private BoyerMoore bm;
	private RabinKarp rk;

	public KMPAndBoyerMooreAndRabinKarpTest(String pat, String str, boolean isPresent){
		this.kmp = new KMP(pat);
		this.bm = new BoyerMoore(pat);
		this.rk = new RabinKarp(pat);
		this.str = str;
		this.match = isPresent;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"this","stooothisxcbbx",true}});
	}
	
	@Test
	public void test(){
		int searchKmp = kmp.search(str);
		int searchbm = bm.search(str);
		int searchrk = rk.search(str);
		
		Assert.assertEquals(searchKmp, searchbm);
		Assert.assertEquals(searchbm, searchrk);
		Assert.assertEquals(match, searchKmp != -1);
	}
}
