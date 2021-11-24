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
	 * Helper Function for generatePowerUp.
	 * Checks to see if the tanks can reach each other
	 * @param The graph of the board
	 * @return True if there is a path connecting the tanks.
	 */
	boolean tanksConnect(Graph graph);
	
	/**
	 * Helper Function for generateBoard.
	 * Generates a power up that is an equal distance of indexes from both tanks.
	 * @return Returns false if it cant.
	 */
	boolean generatePowerUp(Piece[][] board);
	
	/**
	 * Helper Function for generateBoard.
	 * Generates a graph of the given 2D Piece Array
	 * @param board The current board to be converted into a graph
	 */
	Graph generateGraph(Piece[][] board);
	
	
}
