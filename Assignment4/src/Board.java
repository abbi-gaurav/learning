import java.util.Arrays;

public class Board {
	private int[][] blocks;

	public Board(int[][] input) {
		this.blocks = copy(input);
	}

	public int dimension() {
		return blocks.length;
	}

	public int hamming() {
		return hamming(blocks);
	}

	public int manhattan() {
		return manhattan(blocks);
	}

	public boolean isGoal() {
		return isGoalReached(blocks);
	}

	public Board twin() {
		return twin(copy());
	}

	@Override
	public String toString() {
		int n = blocks.length;
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object that) {
		if (!(that instanceof Board)) {
			return false;
		}
		return Arrays.deepEquals(this.blocks, ((Board) that).blocks);
	}

	private static Board twin(int[][] arrayOrg) {
		int[][] array = copy(arrayOrg);

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (j + 1 < array.length
						&& (array[i][j] != 0 && array[i][j + 1] != 0)) {
					swap(array, i, j, i, j + 1);
					return new Board(array);
				}
			}
		}
		return null;
	}

	private static void swap(int[][] array, int i, int j, int i2, int j2) {
		int tmp = array[i][j];
		array[i][j] = array[i2][j2];
		array[i2][j2] = tmp;
	}

	private static int manhattan(int[][] array) {
		int manhattan = 0;
		int n = array.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (array[i][j] == 0) {
					continue;
				}
				int ix = i - ((array[i][j] - 1) / n);
				int jx = j - ((array[i][j] - 1) % n);
				manhattan += (Math.abs(ix) + Math.abs(jx));
			}
		}
		return manhattan;
	}

	public Iterable<Board> neighbors() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if (blocks[i][j] == 0) {
					return createStack(i, j);
				}
			}
		}

		return null;
	}

	private Stack<Board> createStack(int i, int j) {
		Stack<Board> stack = new Stack<Board>();
		addDown(stack, i, j);
		addLeft(stack, i, j);
		addUp(stack, i, j);
		addRight(stack, i, j);
		return stack;
	}

	private static int hamming(int[][] array) {
		int count = 0;
		int n = array.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (array[i][j] != 0 && array[i][j] != (i * n) + j + 1) {
					count++;
				}
			}
		}
		return count;
	}

	private static boolean isGoalReached(int[][] array) {
		return manhattan(array) == 0;
	}

	private void addUp(Stack<Board> stack, int i, int j) {
		if (i != 0) {
			int[][] array = copy();
			swap(array, i, j, i - 1, j);
			stack.push(new Board(array));
		}
	}

	private int[][] copy() {
		return copy(blocks);
	}

	private static int[][] copy(int[][] org) {
		int[][] copy = new int[org.length][org.length];
		for (int i = 0; i < org.length; i++) {
			copy[i] = Arrays.copyOf(org[i], org[i].length);
		}
		return copy;
	}

	private void addDown(Stack<Board> stack, int i, int j) {
		if (i < blocks.length - 1) {
			int[][] array = copy();
			swap(array, i, j, i + 1, j);
			stack.push(new Board(array));
		}
	}

	private void addLeft(Stack<Board> stack, int i, int j) {
		if (j != 0) {
			int[][] array = copy();
			swap(array, i, j, i, j - 1);
			stack.push(new Board(array));
		}
	}

	private void addRight(Stack<Board> stack, int i, int j) {
		if (j < blocks.length - 1) {
			int[][] array = copy();
			swap(array, i, j, i, j + 1);
			stack.push(new Board(array));
		}
	}
}
