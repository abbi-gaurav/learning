import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({DequeueTest.class, RQTestAB.class, RQTestLL.class})
public class TestSuite {
	public static Collection<Object[]> getCount() {
		Object[][] data = new Object[][] { { 32 }, { 64 }, { 128 }, { 256 },{ 1024 } 
				,{ 65536 }, { 131072 } };
		return Arrays.asList(data);
	}
}
