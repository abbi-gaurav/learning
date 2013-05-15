package coursera.alg1.lect1;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class UnionClient {
	public static void main(String[] args)
	{
		int N = StdIn.readInt();
		WtdQuickUnion uf = new WtdQuickUnion(N);
		while (!StdIn.isEmpty())
		{
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.isConnected(p, q))
			{
				uf.union(p, q);
				StdOut.println(p + " " + q);
				StdOut.println(uf);
			}
		}
	}

}
