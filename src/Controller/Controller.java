package Controller;

import Graph.Graph;
import piece.Piece;

public interface Controller {
	
	public Graph generateBoard();
	
	public Piece getSquarePiece(int r, int c);
	
	public void setSquarePiece(int r, int c, Piece piece);
	
	
}
