package Graph;

public class Graph {
	private Node[][] graph;
	
	public Graph()
	{
		graph = new Node[10][10];
	}
	
	
	public Node getNode(int r, int c) {
		return graph[r][c];
	}
	public void setNode(int r, int c, Node node)
	{
		graph[r][c] = node;
	}
}
