import java.util.HashSet;
import java.util.List;

/**
 * This program executes appropriate shortest path algorithm based on the graph
 * condition.
 * 
 * @author Kanchan Waikar Date Created : Mar 27, 2016 - 1:16:13 PM
 *
 */
public class ShortestPathsDriver {

	public static void main(String[] args) {
		/**
		 * Accept graph input from user.
		 */
		Graph graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		int weight = graph.getVerts().get(1).getAdj().get(0).getWeight();
		boolean hasUniformEdges = true;
		boolean negativeWeights = false;
		boolean isCyclic = false;
		/**
		 * Loop through vertices in order to check whether 1. Graph has uniform
		 * edges 2. Graph has negative weights 3. Graph is acyclic
		 */
		for (Vertex vertex : graph) {

			for (Edge edge : vertex.getAdj()) {

				if (hasUniformEdges && edge.getWeight() != weight) {
					hasUniformEdges = false;
				}
				if (!negativeWeights && edge.getWeight() < 0) {
					negativeWeights = true;
				}
			}
		}
		
		isCyclic = TopologicalOrdering.validate(graph);
		
		Statistics stats = new Statistics();
		stats.timer();
		List<Edge> edges = null;
		/**
		 * Based on above flags, call appropriate algorithm.
		 */
		if (hasUniformEdges && !negativeWeights) {
			edges = BreadthFirstSearch.shortestPath(graph);
		} else if (!isCyclic) {
			edges = DAGShortest.shortestPath(graph);
		} else if (!negativeWeights ) {
			edges = Dijkstra.shortestPath(graph);
		} else {
			/**
			 * umbrella implementation
			 */
			edges = BellmanFord.getShortestPath(graph);
		}
		/**
		 * Log time taken.
		 */
		stats.timer("Shortest Path Identification");
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}
	}
}