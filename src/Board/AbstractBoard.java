package Board;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Graph.Graph;
import Graph.Node;
import piece.Piece;
import piece.Piece.Player;
import piece.PieceFactory;

public abstract class AbstractBoard implements Board{
	PieceFactory factory = new PieceFactory();
	Graph board = new Graph();
	
	@Override
	public Graph generateBoard() {
		Graph graph = new Graph();
		Piece[][] board = new Piece[10][10];
		
		//Creates The Vertical Borders
		for(int r = 0; r < 10; r++)
		{
			board[r][0] = factory.getBrick();
			board[r][9] = factory.getBrick();
		}
		//Creates the Horizontal Borders
		for(int c = 0; c < 10; c++)
		{
			board[0][c] = factory.getBrick();
			board[9][c] = factory.getBrick();
		}
		
		//Creates the Tanks
		board[8][1] = factory.getTank(Player.ONE);
		board[1][8] = factory.getTank(Player.TWO);
		
		Random random = new Random();
		
		boolean powerSpawned = false;
		do {
			do {
				for(int r = 1; r < 9; r++)
				{
					for(int c = 1; c <9; c++)
					{
						if(board[r][c] != null && board[r][c].getType() != Piece.Type.TANK)
						{
							board[r][c] = null;
						}
					}
				}
				//Creates the Random Bricks
				int r;
				int c;
				int bricks = 15 + random.nextInt(6);
				
				while(bricks > 0)
				{
					r = random.nextInt(10);
					c = random.nextInt(10);
			
					if(board[r][c] == null)
					{
						board[r][c] = factory.getBrick();
						bricks--;
					}
				}
				//generates the graph for tanksConnect
				graph = generateGraph(board);
			}while(!tanksConnect(graph, graph.getNode(8,1), graph.getNode(1, 8)));
		
		
			//creates the powerUp
			powerSpawned = generatePowerUp(graph, board);
		
			//Regenerates the graph with the new powerup in it
			graph = generateGraph(board);
		}
		while(!powerSpawned);
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
	public boolean generatePowerUp(Graph graph, Piece[][] board) {
		//Getting distances from player one tank
		Queue<Node> frontier = new LinkedList<Node>();
		frontier.add(graph.getNode(8, 1));
		graph.getNode(8, 1).setDistance(0);
		int[][] distances = new int[10][10];
		
		while (!frontier.isEmpty()) {
		    Node g = frontier.poll();
		    for (Node v: g.getEdges()) {
		        if (v.getDistance() == Integer.MAX_VALUE) {
		            frontier.add(v);
		            v.setDistance(g.getDistance() + 1);
		        }
		    }
		}
		
		for(int r  = 0; r <10; r++)
		{
			for(int c = 0;c < 10; c++)
			{
				distances[r][c] = graph.getNode(r,c).getDistance();
			}
		}
		graph.reset();
		
		
		//Getting distances from player two tank
		Queue<Node> frontier2 = new LinkedList<Node>();
		int[][] distances2 = new int[10][10];
		frontier2.add(graph.getNode(1, 8));
		graph.getNode(1, 8).setDistance(0);
		
		
		while (!frontier2.isEmpty()) {
		    Node g = frontier2.poll();
		    for (Node v: g.getEdges()) {
		        if (v.getDistance() == Integer.MAX_VALUE) {
		            frontier2.add(v);
		            v.setDistance(g.getDistance() +1);
		        }
		    }
		}
		for(int r  = 0; r <10; r++)
		{
			for(int c = 0;c < 10; c++)
			{
				distances2[r][c] = graph.getNode(r,c).getDistance();
			}
		}
		
		
		//Finding the spot
		boolean done = false;
		Random random = new Random();
		int rand = random.nextInt(4);
		Piece.PowerUpType powerUp;
		switch(rand)
		{
			case 0: 
				powerUp = Piece.PowerUpType.FASTSHOOT;
				break;
			case 1: 
				powerUp = Piece.PowerUpType.FASTSPEED;
				break;
			case 2: 
				powerUp = Piece.PowerUpType.FASTSHOOT;
				break;
			default: 
				powerUp = Piece.PowerUpType.SHIELD;
				break;
		}
		
		for(int r = 0; r < 10; r++)
		{
			for(int c = 0; c < 10; c++)
			{
				if(distances[r][c] == distances2[r][c] && board[r][c] == null) 
				{
					done = true;
					graph.setNode(r, c, new Node(factory.getPowerUp(powerUp)));
					board[r][c] = factory.getPowerUp(powerUp);
				}
				if(done)
				{
					return true;
				}
			}
		}
		return false;
	}

}
