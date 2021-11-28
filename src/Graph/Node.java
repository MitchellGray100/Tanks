package Graph;

import java.util.LinkedList;
import java.util.List;

import piece.Piece;

public class Node {

	private Piece piece;
	private int distance;
	private int r;
	private int c;
	private List<Node> edges = new LinkedList<Node>();

	public Node(Piece pieceType, int r, int c) {
		setPiece(pieceType);
		setDistance(Integer.MAX_VALUE);
		this.setR(r);
		this.setC(c);
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Returns whether the specified edge exists
	 * 
	 * @return whether the specified edge exists
	 */
	public List<Node> getEdges() {
		return edges;
	}

	/**
	 * Adds an edge to the list of edges
	 * 
	 * @param node the edge to add
	 */
	public void addEdge(Node node) {
		edges.add(node);
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

}
