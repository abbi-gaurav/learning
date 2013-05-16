package work;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJavaLogger {
	private static final Logger LOGGER = Logger.getLogger(TestJavaLogger.class.getCanonicalName());
	public static void main(String[] args) {
		new TestJavaLogger().m();
	}
	private void m() {
		LOGGER.log(Level.SEVERE, "Error during call {0}", new Object[]{"at0", new Exception("dddd")});
	}
}
