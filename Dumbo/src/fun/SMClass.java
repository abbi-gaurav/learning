package fun;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SMClass {
	@Test
	public void testIsFoo() {
		assertTrue(SMClass.isFoo("foo"));
		assertFalse(SMClass.isFoo("bar"));
	}

	public static boolean isFoo(String s) {
		return s == "foo";
	}
}
