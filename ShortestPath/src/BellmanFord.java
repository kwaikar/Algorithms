import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Approach 3 implementation of Bellman ford algorithm
 * 
 * @author Kanchan Waikar Date Created : Mar 27, 2016 - 1:18:07 PM
 *
 */
public class BellmanFord {

 
	public  static  List<Edge> getShortestPath(Graph graph) {
		/**
		 * Create a Queue for holding vertices to be processed
		 */
		Queue<Vertex> queue = new PriorityQueue<Vertex>(graph.getVerts().size());
		/**
		 * Initialize
		 */
		graph.initialize();
		Vertex sourceVertex = graph.getVerts().get(1);
		sourceVertex.setSeen();
		sourceVertex.distanceObj.setInfinity(false);
		queue.add(sourceVertex);
		/**
		 * Start processing Queue until it becomes empty and shortest path has
		 * been found.
		 */
		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			u.setSeen();
			if (u.getCount() >= graph.getVerts().size()) {
				/**
				 * vertex has been visited more times than the |V| this means
				 * the cycle does exist! Break and exit out of the loop
				 */
				System.out.println("Graph has negative cycle");
				break;
			}
			for (Edge edge : u.getAdj()) {
				Vertex v = edge.otherEnd(u);
				/**
				 * Revisit parent of v, set it as u if total distance is more
				 * optimal than existing one.
				 */
				if (v.distanceObj.isInfinity()
						|| (v.distanceObj.getDistance() > (u.distanceObj.getDistance() + edge.getWeight()))) {
					v.distanceObj.setDistance(u.distanceObj.getDistance() + edge.getWeight());
					v.distanceObj.setInfinity(false);
					v.setParent(u);
					v.setParentEdge(edge);
					/**
					 * Schedule v for processing if its not processed already.
					 */
					if (!v.isSeen()) {
						queue.add(v);
						v.setSeen();
					}
					u.setCount(u.getCount() + 1);
				}
			}
		}
		/**
		 * Print shortest paths.
		 */
		return graph.getParentEdgesAndPrintPath("B-F");
	}

	public static void main(String[] args) {
		BellmanFord obj = new BellmanFord();
		List<Edge> edges = obj.getShortestPath(Graph.acceptGraphInput(args[0], GraphType.DIRECTED));
		System.out.println("Shortest path found.");
	}

}
