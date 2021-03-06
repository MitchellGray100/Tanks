package Graph;

import piece.Brick;
import piece.Piece;
import piece.Piece.Type;
import piece.PowerUp;
import piece.Tank;

public class Graph {
	private Node[][] graph;

	public Graph() {
		graph = new Node[10][10];
	}

	public void generateEdges() {
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				if (getNode(r, c).getPiece() != null) {
					if (getNode(r, c).getPiece().getType() == Type.BRICK) {
//						setNode(r, c, new Node(new Brick(), r, c));
					} else if (getNode(r, c).getPiece().getType() == Type.POWERUP) {

						if (generateGraphHelper(r + 1, c))
							getNode(r, c).addEdge(getNode(r + 1, c));
						if (generateGraphHelper(r, c + 1))
							getNode(r, c).addEdge(getNode(r, c + 1));
						if (generateGraphHelper(r - 1, c))
							getNode(r, c).addEdge(getNode(r - 1, c));
						if (generateGraphHelper(r, c - 1))
							getNode(r, c).addEdge(getNode(r, c - 1));

					} else if (getNode(r, c).getPiece().getType() == Type.TANK) {
						if (generateGraphHelper(r + 1, c))
							getNode(r, c).addEdge(getNode(r + 1, c));
						if (generateGraphHelper(r, c + 1))
							getNode(r, c).addEdge(getNode(r, c + 1));
						if (generateGraphHelper(r - 1, c))
							getNode(r, c).addEdge(getNode(r - 1, c));
						if (generateGraphHelper(r, c - 1))
							getNode(r, c).addEdge(getNode(r, c - 1));
					}

				} else {

					if (generateGraphHelper(r + 1, c))
						getNode(r, c).addEdge(getNode(r + 1, c));
					if (generateGraphHelper(r, c + 1))
						getNode(r, c).addEdge(getNode(r, c + 1));
					if (generateGraphHelper(r - 1, c))
						getNode(r, c).addEdge(getNode(r - 1, c));
					if (generateGraphHelper(r, c - 1))
						getNode(r, c).addEdge(getNode(r, c - 1));
				}
			}
		}
	}

	/**
	 * Takes in a board and turns the board into a graph with edges
	 * 
	 * @param board The board of the game to be turned into a graph
	 */
	public void generateGraph(Piece[][] board) {
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				if (board[r][c] != null) {
					if (board[r][c].getType() == Type.BRICK) {
						setNode(r, c, new Node(new Brick(), r, c));
					} else if (board[r][c].getType() == Type.POWERUP) {
						setNode(r, c, new Node(new PowerUp(((PowerUp) board[r][c]).getPowerUpType()), r, c));

					} else if (board[r][c].getType() == Type.TANK) {
						setNode(r, c, new Node(new Tank(board[r][c].getPlayer()), r, c));
					}

				} else {
					setNode(r, c, new Node(null, r, c));
				}
			}
		}
		generateEdges();

	}

	private boolean generateGraphHelper(int r, int c) {
		if (r < 0 || r > 9 || c < 0 || c > 9) {
			return false;
		}
		if (getNode(r, c).getPiece() == null) {
			return true;
		} else if (getNode(r, c).getPiece().getType() == Piece.Type.POWERUP
				|| getNode(r, c).getPiece().getType() == Piece.Type.TANK) {
			return true;
		}

		return false;
	}

	public Node getNode(int r, int c) {
		return graph[r][c];
	}

	public void setNode(int r, int c, Node node) {
		graph[r][c] = node;
	}

	public void reset() {
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				graph[r][c].setDistance(Integer.MAX_VALUE);
			}
		}
	}
}
