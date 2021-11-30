package Board;

import Graph.Graph;
import Graph.Node;
import piece.Brick;
import piece.Piece.Player;
import piece.PowerUp;
import piece.Tank;

public class BoardDebugImpl extends AbstractBoard {
	public BoardDebugImpl() {
		board = new Graph();
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				board.setNode(r, c, new Node(null, r, c));

			}
		}
		board = generateBoard();
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				board.setNode(r, c, new Node(null, r, c));

			}
		}
		board.setNode(8, 1, new Node(new Tank(Player.TWO), 8, 1));
		board.setNode(1, 8, new Node(new Tank(Player.ONE), 1, 8));
		board.setNode(6, 5, new Node(new Brick(), 5, 6));
		board.setNode(0, 0, new Node(new PowerUp(piece.Piece.PowerUpType.FASTBULLET), 0, 0));

	}
}
