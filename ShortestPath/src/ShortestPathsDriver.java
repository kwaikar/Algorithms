import java.util.HashSet;
import java.util.List;

public class ShortestPathsDriver {

	public static void main(String[] args) {

		Graph graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		int weight = graph.getVerts().get(0).getAdj().get(0).getWeight();
		boolean hasUniformEdges = true;
		boolean negativeWeights = false;
		boolean isCyclic = false;
		for (Vertex vertex : graph) {

			for (Edge edge : vertex.getAdj()) {

				if (hasUniformEdges && edge.getWeight() != weight) {
					hasUniformEdges = false;
				}
				if (!negativeWeights && edge.getWeight() < 0) {
					negativeWeights = true;
				}
			}
			if (TopologicalOrdering.isCyclic(vertex, new HashSet<Vertex>())) {
				isCyclic = true;
			}
		}
		List<Edge> edges=null;
		if (hasUniformEdges && !negativeWeights) {
			BreadthFirstSearch bfs = new BreadthFirstSearch();
		 edges = bfs.shortestPath();
		}
		else if(!negativeWeights && !isCyclic)
		{
			//call DAG
		}
		else if (!negativeWeights && isCyclic)
		{
			Dijkstra dijkstra = new Dijkstra();
			edges=dijkstra.shortestPath();
		}
		else
		{
			// BellManFord
		}

		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}
	}
}
