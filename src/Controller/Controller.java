package Controller;

import Graph.Graph;
import piece.Piece;

public interface Controller {
	
	public Graph generateBoard();
	
	public Piece getSquarePiece(int r, int c);
	
	public void setSquarePiece(int r, int c, Piece piece);
	
	/**
	 * Calculates the direction the AI needs to move to get to the player
	 * 
	 * @param r The x location of the AI on the GUI
	 * @param c The y location of the AI on the GUI
	 * @param x The x location of the player on the GUI
	 * @param y The y location of the player on the GUI
	 * @return The direction the AI needs to move.
	 */
	public piece.Piece.Direction getAIMove(double r, double c, double x, double y);
}
