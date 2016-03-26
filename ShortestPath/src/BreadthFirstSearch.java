import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * 
 * FrameWork: Directed Graph with source node that is a vertex in the graph.
 * Edges connect the vertices and have weight with the value of real numbers.
 * 
 * Goal: Find the shortest path that connects all the vertices.
 * 
 * The Breadth First Search algorithm is used to find the shorted path in a
 * graph in two cases. 1. The graph does not have weights for the edges. 2. All
 * the edges in the graph have equal weights.
 * 
 * 
 * Breadth First Search:
 * 
 * Breadth-first search (BFS) is an algorithm for traversing or searching tree
 * or graph data structures. It starts at the source root (or some arbitrary
 * node of a graph, sometimes referred to as a 'search key'[1]) and explores the
 * neighbor nodes first, before moving to the next level neighbors
 * 
 * @author M Krishna Kavya
 * 
 */
public class BreadthFirstSearch {
	public Graph graph;

	/**
	 * The method calculates the shorted path for a given graph with BFS
	 * algorithm.
	 */
	public List<Edge> shortestPath() {
		Timer.timer();
		Deque<Vertex> queue = new ArrayDeque<Vertex>();

		/*
		 * Initialization. Every vertex in the graph is assigned distance infinity from source, 
		 * marked not seen and has no parent
		 */
		graph.initialize(graph);
		/*
		 * Add source(first) vertex to the queue.Vertex is marked seen and
		 * parent is marked to null.
		 */
		Vertex sourceVertex = graph.getVerts().get(1);
		queue.add(sourceVertex);
		sourceVertex.setSeen();
		sourceVertex.distanceObj.setInfinity(false);

		/*
		 * for each vertex in the queue. parse through all the edges of the
		 * current vertex. Mark it as seen.Add the other end of the edge to the
		 * queue. we repeat the process of parsing through the edges of the
		 * vertices that are not visited before. The current vertex becomes the
		 * parent of the other end of the edge. The distance to the vertex v
		 * from the source is incremented by adding the value of the distance
		 * of current vertex and the new vertex
		 */
		int wmst=0;
		while (!queue.isEmpty()) {
			Vertex current = queue.remove();
			for (Edge edge : current.getAdj()) {
			
				Vertex v = edge.otherEnd(current);
				if (!v.isSeen()) {
					v.distanceObj.setDistance( current.distanceObj.getDistance()+ edge.getWeight());
					v.distanceObj.setInfinity(false);
					wmst=wmst+v.distanceObj.getDistance();
					v.setParent( current);
					v.setParentEdge(edge);
					v.setSeen();
					queue.add(v);
				}
			}
		}
		Timer.timer();
		/*
		 * Output of the graph with shortest path. 
		 */
		System.out.println(wmst);
		for (Vertex v : graph) {
			System.out.println(v.getName() + " " + v.distanceObj.getDistance() + " "
					+ v.getParent());
		}
		return graph.getParentEdgesAndPrintPath("BFS");
	}

	public static void main(String[] args) {

		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		bfs.shortestPath();
	}
}
