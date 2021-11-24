package Board;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import Graph.Graph;
import Graph.Node;
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
		}while(!tanksConnect(graph, graph.getNode(8,1), graph.getNode(1, 8)));
		
		
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
	public boolean tanksConnect(Graph graph, Node s, Node d) {
		LinkedList<Integer>temp;
		 
        // Mark all the vertices as not visited(By default set
        // as false)
        HashSet<Node> visited = new HashSet<Node>();
 
        // Create a queue for BFS
        LinkedList<Node> queue = new LinkedList<Node>();
 
        // Mark the current node as visited and enqueue it
        visited.add(s);
        queue.add(s);
 
        // 'i' will be used to get all adjacent vertices of a vertex
        Iterator<Node> i;
        while (queue.size()!=0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
 
            Node n;
            i = s.getEdges().listIterator();
 
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            while (i.hasNext())
            {
                n = i.next();
 
                // If this adjacent node is the destination node,
                // then return true
                if (n==d)
                    return true;
 
                // Else, continue to do BFS
                if (!visited.contains(n))
                {
                    visited.add(n);
                    queue.add(n);
                }
            }
        }
 
        // If BFS is complete without visited d
        return false;
    }

	@Override
	public boolean generatePowerUp(Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

}
