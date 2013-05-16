public class Solver {
	private final State solvableState;

	public Solver(Board board) {
//		solvableState = work(board);
		solvableState = work1Q(board);
	}

	public boolean isSolvable() {
		return solvableState != null;
	}

	public int moves() {
		return solvableState == null ? -1 : solvableState.numOfMoves;
	}

	public Iterable<Board> solution() {
		if (solvableState == null) {
			return null;
		}

		Stack<Board> solvedStack = new Stack<Board>();
		State current = solvableState;
		solvedStack.push(current.board);
		while (current.previousState != null) {
			solvedStack.push(current.previousState.board);
			current = current.previousState;
		}
		return solvedStack;
	}

	private static State work2Q(Board board) {
		return aStarSearch(createQ(board), createQ(board.twin()));
	}

	private static MinPQ<State> createQ(Board board) {
		State state = new State(board, 0, null);
		MinPQ<State> minQ = new MinPQ<State>();
		minQ.insert(state);
		return minQ;
	}
	
	private static State work1Q(Board board) {
		MinPQ<State> minQ = createQ(board);
		minQ.insert(new State(board.twin(), 0, null));
		State successState = aStarSearch(minQ);
		return isCorrectSuccess(successState,board);
	}
	
	private static State isCorrectSuccess(final State successState, Board board) {
		State current = successState;
		
		Board successStartBoard = current.board;
		while(current.previousState != null){
			successStartBoard = current.previousState.board;
			current = current.previousState;
		}
		return successStartBoard.equals(board) ? successState:null;
	}

	private static State aStarSearch(MinPQ<State> minQOrg) {
		while (!minQOrg.isEmpty()) {
			State current = minQOrg.delMin();
			if (current.board.isGoal()) {
				return current;
			}

			insertNeighbors(minQOrg, current);
		}
		return null;
	}
	
	private static State aStarSearch(MinPQ<State> minQOrg, MinPQ<State> twinQ) {
		Stopwatch sw = new Stopwatch();
		while (!minQOrg.isEmpty()) {
			State current = minQOrg.delMin();
			if (current.board.isGoal()) {
				System.out.println("timeElasped::" + sw.elapsedTime());
				return current;
			}

			State currentTwin = twinQ.delMin();
			if (currentTwin.board.isGoal()) {
				return null;
			}

			insertNeighbors(minQOrg, current);
			insertNeighbors(twinQ, currentTwin);
		}
		return null;
	}

	private static void insertNeighbors(MinPQ<State> minQ, State current) {
		for (Board neighbor : current.board.neighbors()) {
			if (current.previousState != null
					&& neighbor.equals(current.previousState.board)) {
				continue;
			}
			minQ.insert(new State(neighbor, current.numOfMoves + 1, current));
		}
	}

	private static final class State implements Comparable<State> {
		private final Board board;
		private final int numOfMoves;
		private final State previousState;

		public State(Board board, int numOfMoves, State previousState) {
			this.board = board;
			this.numOfMoves = numOfMoves;
			this.previousState = previousState;
		}

		@Override
		public int compareTo(State that) {
			int diff = (this.board.manhattan() + this.numOfMoves)
					- (that.board.manhattan() + that.numOfMoves);
			if (diff == 0) {
				return this.board.manhattan() - that.board.manhattan();
			}
			return diff;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			convertToStr(this, sb);
			return sb.toString();
		}

		private static void convertToStr(State state, StringBuilder sb) {
			if (state.previousState != null) {
				convertToStr(state.previousState, sb);
			}
			sb.append(state.board);
		}
	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Stopwatch st = new Stopwatch();
		Solver solver = new Solver(initial);
		System.out.println("timeelasped "+st.elapsedTime());
		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

}
