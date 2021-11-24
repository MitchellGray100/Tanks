package Board;

import Graph.Graph;
import piece.Piece;

public interface Board {
	
	/**
	 * Creates the board that contains all objects.
	 * @return the board as a graph
	 */
	Graph createBoard();
	
	/**
	 * Helper Function for createBoard.
	 * Generates a random board with 2 tanks, a border of bricks, and 15 random bricks.
	 * @return the board as a graph
	 */
	Graph generateBoard();
	
	/**
	 * Helper Function for generateBoard()
	 * Generates a random 2D array with 2 tanks, a border of bricks, and 15 random bricks.
	 * @return the board as a 2D array of Pieces.
	 */
	Piece[][] generatePieces();
	/**
	 * Helper Function for generatePowerUp.
	 * Checks to see if the tanks can reach each other
	 * @return True if there is a path connecting the tanks.
	 */
	boolean tanksConnect();
	
	/**
	 * Helper Function for createBoard.
	 * Generates a power up that is an equal distance of indexes from both tanks.
	 * @return Returns false if it cant.
	 */
	boolean generatePowerUp();
	
	
}
