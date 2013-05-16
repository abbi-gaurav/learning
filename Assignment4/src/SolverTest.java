import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SolverTest {
	private final String fileName;

	public SolverTest(String fileName) {
		this.fileName = fileName;
	}

	@Parameters
	public static Collection<Object[]> params() {
		Object[][] params = { { "puzzle04.txt" } };
		return Arrays.asList(params);
	}

	@Test
	public void testSolve() {
		new Solver(BoardTest.getBoard(fileName));
	}
}
