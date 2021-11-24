package Controller;

import Board.Board;
import Board.BoardImpl;
import Graph.Graph;
import piece.Piece;

public class ControllerImpl implements Controller {
	private Board board = new BoardImpl();
	private Graph graph = generateBoard();
	
	@Override
	public Graph generateBoard() {
		return board.generateBoard();
	}
	
	public Piece getSquarePiece(int r, int c)
	{
		return graph.getNode(r,c).getPiece();
	}
	
	public void setSquarePiece(int r, int c, Piece piece)
	{
		graph.getNode(r, c).setPiece(piece);
	}

}
