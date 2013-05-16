public class Percolation {
	private WeightedQuickUnionUF delegator;
	private int systemSize;
	private int virtualTop;
	private int virtualBottom;
	private final boolean[] openedSites;
	private final WeightedQuickUnionUF delegatorWithoutBottom;

	public Percolation(int n) {
		this.systemSize = n;
		int gridSize = n * n;
		this.delegator = new WeightedQuickUnionUF(gridSize + 2);
		this.delegatorWithoutBottom = new WeightedQuickUnionUF(gridSize + 1);
		this.virtualTop = gridSize;
		this.virtualBottom = gridSize + 1;
		this.openedSites = new boolean[gridSize];
	}

	public void open(int row, int column) {
		validateIndecies(row, column);
		int gridLocation = xyToP(row, column);
		markOpen(gridLocation);
		int[] adjacents = getAdjacents(row, column, gridLocation);
		connect(gridLocation, adjacents);
	}

	private void validateIndecies(int row, int column) {
		validate(row, "x");
		validate(column, "column");
	}

	private void validate(int coordinate, String coordinateType) {
		if (coordinate <= 0 || coordinate > systemSize) {
			throw new IndexOutOfBoundsException(coordinateType + " value["
					+ coordinate + "] is out of range");
		}
	}

	private void markOpen(int gridLocation) {
		if (!checkIfLocationOpen(gridLocation)) {
			openedSites[gridLocation] = true;
		}
	}

	private boolean checkIfLocationOpen(int gridLocation) {
		return (gridLocation == virtualBottom) || (gridLocation == virtualTop)
				|| openedSites[gridLocation];
	}

	public boolean isOpen(int row, int column) {
		validateIndecies(row, column);
		int gridLocation = xyToP(row, column);
		return checkIfLocationOpen(gridLocation);
	}

	public boolean isFull(int row, int column) {
		validateIndecies(row, column);
		int gridLoc = xyToP(row, column);
		return checkIfLocationOpen(gridLoc)
				&& delegatorWithoutBottom.connected(gridLoc, virtualTop);
	}

	public boolean percolates() {
		return delegator.connected(virtualTop, virtualBottom);
	}

	private void connect(int gridLocation, int[] adjacents) {
		for (int adjacent : adjacents) {
			if (adjacent != -1 && checkIfLocationOpen(adjacent)) {
				delegator.union(gridLocation, adjacent);
				if (adjacent != virtualBottom) {
					delegatorWithoutBottom.union(gridLocation, adjacent);
				}
			}
		}
	}

	private int[] getAdjacents(int row, int column, int gridLocation) {
		int[] adjacents = new int[4];
		addTop(row, column, adjacents);
		addBottom(row, column, adjacents);
		addLeft(gridLocation, adjacents);
		addRight(gridLocation, adjacents);
		return adjacents;
	}

	private void addRight(int gridLocation, int[] adjacents) {
		if (!((gridLocation + 1) % systemSize == 0)) {
			adjacents[3] = gridLocation + 1;
		} else {
			adjacents[3] = -1;
		}
	}

	private void addLeft(int gridLocation, int[] adjacents) {
		if (!(gridLocation % systemSize == 0)) {
			adjacents[2] = gridLocation - 1;
		} else {
			adjacents[2] = -1;
		}
	}

	private void addBottom(int row, int column, int[] adjacents) {
		if (row == systemSize) {
			adjacents[1] = virtualBottom;
		} else {
			adjacents[1] = xyToP(row + 1, column);
		}
	}

	private void addTop(int row, int column, int[] adjacents) {
		if (row == 1) {
			adjacents[0] = virtualTop;
		} else {
			adjacents[0] = xyToP(row - 1, column);
		}
	}

	private int xyToP(int row, int column) {
		return systemSize * (row - 1) + (column - 1);
	}
}