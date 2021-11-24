package Graph;

import piece.Brick;
import piece.Piece;
import piece.Piece.Type;
import piece.PowerUp;
import piece.Tank;

public class Graph {
	private Node[][] graph;
	
	public Graph()
	{
		graph = new Node[10][10];
	}
	
	/**
	 * Takes in a board and turns the board into a graph with edges
	 * @param board The board of the game to be turned into a graph
	 */
	public void generateGraph(Piece[][] board)
	{
		for(int r = 0; r < 9; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				if(board[r][c] != null)
				{
					if(board[r][c].getType() == Type.BRICK)
					{
						setNode(r,c,new Node(new Brick()));
					}
					else if(board[r][c].getType() == Type.POWERUP)
					{
						setNode(r,c,new Node(new PowerUp(((PowerUp)board[r][c]).getPowerUpType())));
						
						if(generateGraphHelper(r+1,c,board))
							getNode(r,c).addEdge(getNode(r+1,c));
						if(generateGraphHelper(r,c+1,board))
							getNode(r,c).addEdge(getNode(r,c+1));
						if(generateGraphHelper(r-1,c,board))
							getNode(r,c).addEdge(getNode(r-1,c));
						if(generateGraphHelper(r,c-1,board))
							getNode(r,c).addEdge(getNode(r,c-1));
						
					}
					else if(board[r][c].getType() == Type.TANK)
					{
						setNode(r,c,new Node(new Tank(board[r][c].getPlayer())));
						
						if(generateGraphHelper(r+1,c,board))
							getNode(r,c).addEdge(getNode(r+1,c));
						if(generateGraphHelper(r,c+1,board))
							getNode(r,c).addEdge(getNode(r,c+1));
						if(generateGraphHelper(r-1,c,board))
							getNode(r,c).addEdge(getNode(r-1,c));
						if(generateGraphHelper(r,c-1,board))
							getNode(r,c).addEdge(getNode(r,c-1));
					}
					
						
					
				}
				else
				{
					setNode(r,c,new Node(null));
					
					if(generateGraphHelper(r+1,c,board))
						getNode(r,c).addEdge(getNode(r+1,c));
					if(generateGraphHelper(r,c+1,board))
						getNode(r,c).addEdge(getNode(r,c+1));
					if(generateGraphHelper(r-1,c,board))
						getNode(r,c).addEdge(getNode(r-1,c));
					if(generateGraphHelper(r,c-1,board))
						getNode(r,c).addEdge(getNode(r,c-1));
				}
			}
		}
	}
	
	private boolean generateGraphHelper(int r, int c, Piece[][] board) {
		if(r < 0 || r > 9 || c < 0 || c > 9)
		{
			return false;
		}
		if(board[r][c] == null)
		{
			return true;
		}
		return false;
	}

	public Node getNode(int r, int c) {
		return graph[r][c];
	}
	public void setNode(int r, int c, Node node)
	{
		graph[r][c] = node;
	}
}
