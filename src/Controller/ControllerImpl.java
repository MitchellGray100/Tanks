package Controller;

import Board.Board;
import Board.BoardImpl;
import Graph.Graph;
import piece.Piece;
import piece.Piece.Direction;

public class ControllerImpl implements Controller {
	private Board board = new BoardImpl();
	private Graph graph = generateBoard();

	@Override
	public Graph generateBoard() {
		return board.getBoard();
	}

	public Piece getSquarePiece(int r, int c) {
		return graph.getNode(r, c).getPiece();
	}

	public void setSquarePiece(int r, int c, Piece piece) {
		graph.getNode(r, c).setPiece(piece);
	}

	@Override
	public Direction getAIMove(double r, double c, double x, double y) {
		return board.getAIMove(r, c, x, y);
	}

}
