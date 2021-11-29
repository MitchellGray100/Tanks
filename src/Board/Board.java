package Board;

import Graph.Graph;
import Graph.Node;
import piece.Piece;

public interface Board {

	public Graph getBoard();

	public void setBoard(Graph board);

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

	/**
	 * Generates a random board with 2 tanks, a border of bricks, and 15 random
	 * bricks.
	 * 
	 * @return the board as a graph
	 */
	Graph generateBoard();

	/**
	 * Helper Function for generatePowerUp. Checks to see if the tanks can reach
	 * each other
	 * 
	 * @param graph The graph of the board
	 * @param s     The source node
	 * @param d     The destination Node
	 * @return True if there is a path connecting the tanks.
	 */
	boolean tanksConnect(Graph graph, Node s, Node d);

	/**
	 * Helper Function for generateBoard. Generates a power up that is an equal
	 * distance of indexes from both tanks.
	 * 
	 * @param graph The graph of the game
	 * @param board The board of the game
	 * @return Returns false if it cant.
	 */
	boolean generatePowerUp(Graph graph, Piece[][] board);

	/**
	 * Helper Function for generateBoard. Generates a graph of the given 2D Piece
	 * Array
	 * 
	 * @param board The current board to be converted into a graph
	 */
	Graph generateGraph(Piece[][] board);

}
