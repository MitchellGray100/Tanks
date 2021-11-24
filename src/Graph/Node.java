package Graph;

import piece.Piece;

public class Node {
	
	private Piece piece;
	
	//edges documentation:
	//	  	    [0] UP
	//			[1] RIGHT
	//			[2] DOWN
	//			[3] LEFT
	private boolean[] edges = new boolean[4];
	
	public Node(Piece pieceType)
	{
		setPiece(pieceType);
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Returns whether the specified edge exists
	 * @return whether the specified edge exists
	 */
	public boolean[] getEdges() {
		return edges;
	}

	/**
	 * Sets the specified edge to be bool
	 * @param edgeNum Indication of which edge to change
	 * @param bool Indication of whether the edge exists.
	 */
	public void setEdge(int edgeNum, boolean bool) {
		edges[edgeNum] = bool;
	}
}
