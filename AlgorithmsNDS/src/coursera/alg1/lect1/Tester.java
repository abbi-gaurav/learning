package coursera.alg1.lect1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class Tester<T extends Unionable> {

	private final Class<T> klazz;

	public Tester(Class<T> klazz) {
		this.klazz = klazz;
	}

	public void test() throws IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		int size = returnInts(reader.readLine())[0];
		Unionable worker = klazz.getConstructor(int.class).newInstance(size);
		System.out.println(worker.toString());
		String read = null;
		while ((read = reader.readLine()) != null && read.trim().length() > 0) {
			int[] pNq = returnInts(read);
			int p = pNq[0];
			int q = pNq[1];

			if (!worker.isConnected(p, q)) {
				worker.union(p, q);
				System.out.println(worker.toString());
			}
		}
	}

	public static void main(String[] args) throws IOException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		new Tester<QuickFind>(QuickFind.class).test();
		// new Tester<QuickUnion>(QuickUnion.class).test();
		// new Tester<WtdQuickUnion>(WtdQuickUnion.class).test();
	}

	public static int[] returnInts(String str) {
		String[] ints = str.split(" ");
		int[] intArray = new int[ints.length];
		for (int i = 0; i < ints.length; i++) {
			intArray[i] = Integer.parseInt(ints[i]);
		}
		return intArray;
	}
}
