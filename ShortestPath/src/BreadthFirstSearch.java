import java.util.ArrayDeque;
import java.util.Deque;

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
	public Graph result;

	/**
	 * The method calls the method graphInput. This method initialises the graph
	 * object with the given input.
	 */
	public void readGraph() {
		graph = Graph.graphInput("lp3_l1_in1", true);
	}

	/**
	 * The method calculates the shorted path for a given graph with BFS
	 * algorithm.
	 */
	public void shortestPath() {
		Timer.timer();
		Deque<Vertex> queue = new ArrayDeque<Vertex>();

		/*
		 * Initialization. Every vertex in the graph is assigned distance infinity from source, 
		 * marked not seen and has no parent
		 */
		for (Vertex v : graph) {
			v.distanceObj.setDistance(0);
			v.distanceObj.setInfinity(true);
			v.seen = false;
			v.parent = null;
		}

		/*
		 * Add source(first) vertex to the queue.Vertex is marked seen and
		 * parent is marked to null.
		 */
		Vertex sourceVertex = graph.verts.get(1);
		queue.add(sourceVertex);
		sourceVertex.seen = true;

		/*
		 * for each vertex in the queue. parse through all the edges of the
		 * current vertex. Mark it as seen.Add the other end of the edge to the
		 * queue. we repeat the process of parsing through the edges of the
		 * vertices that are not visited before. The current vertex becomes the
		 * parent of the other end of the edge. The distance to the vertex v
		 * from the source is incremented by adding the value of the distance
		 * of current vertex and the new vertex
		 */

		while (!queue.isEmpty()) {
			Vertex current = queue.remove();
			for (Edge edge : current.Adj) {
				Vertex v = edge.otherEnd(current);
				if (v.seen == false) {
					v.distanceObj.distance = current.distanceObj.distance
							+ 1;
					v.parent = current;
					v.seen = true;
					queue.add(v);

				}
			}

		}
		Timer.timer();
		/*
		 * Output of the graph with shortest path. 
		 */
		for (Vertex v : graph) {
			System.out.println(v.name + " " + v.distanceObj.distance + " "
					+ v.parent);
		}
	}

	public static void main(String[] args) {

		BreadthFirstSearch obj = new BreadthFirstSearch();
		obj.readGraph();
		obj.shortestPath();
	}
}
