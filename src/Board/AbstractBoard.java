package Board;

import Graph.Graph;
import piece.Piece;

public abstract class AbstractBoard implements Board{
	@Override
	public Piece[][] generatePieces() {
		// TODO Auto-generated method stub
		return null;
	}

	Graph board = new Graph();
	@Override
	public Graph createBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graph generateBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tanksConnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean generatePowerUp() {
		// TODO Auto-generated method stub
		return false;
	}

}
