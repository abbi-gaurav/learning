import junit.framework.Assert;

import org.junit.Test;

public class BoardTest {
	private static final String DEFAULT = "my.txt";
	private static final String DIR = "/home/gaurav_abbi/learning/coursera/data/8puzzle";

	@Test
	public void testHammingManhattan() {
		Board board = getBoard(DEFAULT);
		Assert.assertEquals(5, board.hamming());
		Assert.assertEquals(10, board.manhattan());
	}

	@Test
	public void testTwin() {
		Board board = getBoard(DEFAULT);
		System.out.println(board);
		System.out.println(board.twin());

		Board board2 = getBoard(DEFAULT);
		Assert.assertTrue(board.equals(board2));
	}

	@Test
	public void testNeighbors() {
		System.out.println("newighbor start");
		Board board = getBoard(DEFAULT);
		System.out.println(board);
		System.out.println("test neighbor");

		for (Board b : board.neighbors()) {
			System.out.println(b);
		}

		Board board2 = getBoard(DEFAULT);
		Assert.assertTrue(board.equals(board2));
	}

	public static Board getBoard(String fileName) {
		In in = new In(DIR + "/" + fileName);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blocks[i][j] = in.readInt();
			}
		}
		return new Board(blocks);
	}
}
