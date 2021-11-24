package Board;

import java.util.Random;

import Graph.Graph;
import piece.Brick;
import piece.Piece;
import piece.Piece.Player;
import piece.Tank;

public abstract class AbstractBoard implements Board{

	Graph board = new Graph();
	
	@Override
	public Graph generateBoard() {
		Graph graph = new Graph();
		Piece[][] board = new Piece[10][10];
		
		//Creates The Vertical Borders
		for(int r = 0; r < 10; r++)
		{
			board[r][0] = new Brick();
			board[r][9] = new Brick();
		}
		//Creates the Horizontal Borders
		for(int c = 0; c < 10; c++)
		{
			board[0][c] = new Brick();
			board[9][c] = new Brick();
		}
		
		//Creates the Tanks
		board[8][1] = new Tank(Player.ONE);
		board[1][8] = new Tank(Player.TWO);
		
		do {
			//Creates the Random Bricks
			Random random = new Random();
			int r;
			int c;
			int bricks = 15;
		
			while(bricks > 0)
			{
				r = random.nextInt(7);
				c = random.nextInt(7);
			
				if(board[r][c] == null)
				{
					board[r][c] = new Brick();
					bricks--;
				}
			}
			//generates the graph for tanksConnect
			graph = generateGraph(board);
		}while(!tanksConnect(graph));
		
		
		//creates the powerUp
		generatePowerUp(board);
		
		//Regenerates the graph with the new powerup in it
		graph = generateGraph(board);
		
		return graph;
	}
	@Override
	public Graph generateGraph(Piece[][] board) {
		Graph graph = new Graph();
		graph.generateGraph(board);
		
		return graph;
		
	}
	@Override
	public Graph createBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tanksConnect(Graph graph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean generatePowerUp(Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

}
