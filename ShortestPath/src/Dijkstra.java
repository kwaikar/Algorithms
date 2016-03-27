import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Dijkstra's algorithm is an algorithm for finding the shortest paths between
 * nodes in a graph. Dijkstra's algorithm will assign some initial distance
 * values for node to source and will try to improve them step by step.
 * 
 * 
 * @author Maringanty, Krishna Kavya Date Created : Mar 26, 2016 - 2:48:15 PM
 */
public class Dijkstra {

	Graph graph;

	/**
	 * The method calls the method graphInput. This method initializes the graph
	 * object with the given input.
	 */

	public List<Edge> shortestPath() {
		/*
		 * Initialize the vertices.
		 */
		graph.initialize(graph);

		IndexedHeap<Vertex> queue = new IndexedHeap<Vertex>(graph.getVerts()
				.size());
		/*
		 * compute distance to a vertex v that is the shortest distance from
		 * source to a vertex v and add to the indexed priority queue.
		 * 
		 * Add source(first) vertex to the queue.Vertex is marked seen and
		 * parent is marked to null.
		 */
		Vertex sourceVertex = graph.getVerts().get(1);
		sourceVertex.setSeen();
		sourceVertex.distanceObj.setInfinity(false);
		queue.insert(sourceVertex);
		// for every vertex calculate the shortest from the source to the vertex.
		for (Vertex vertex : graph) {
			Vertex current = vertex;
			if (!current.distanceObj.isInfinity()) {
				for (Edge edge : current.getAdj()) {
					Vertex v = edge.otherEnd(current);
					if (v.isSeen() == false) {
						v.distanceObj.setInfinity(false);
						v.distanceObj.setDistance(current.distanceObj
								.getDistance() + edge.getWeight());
						System.out.println(v.distanceObj.getDistance());
						v.setParent(current);
						v.setParentEdge(edge);
						v.setSeen(true);
						queue.insert(v);
					}
				}
			}
		}
		
		
		/**
		 * Calculation of Shortest Path.
		 * 
		 * For every Vertex removed from the indexed Priority queue, consider
		 * all of its unvisited neighbors. Compare the newly calculated
		 * tentative distance to the current assigned value and assign the
		 * smaller one. For example, if the current node A is marked with a
		 * distance of 6, and the edge connecting it with a neighbor B has
		 * length 2, then the distance to B (through A) will be 6 + 2 = 8. If B
		 * was previously marked with a distance greater than 8 then change it
		 * to 8. Otherwise, keep the current value.
		 * 
		 * This is performed for every vertex removed from the priority Queue and
		 * decrease key operation is performed.
		 */
		while (!queue.isEmpty()) {
			Vertex u = (Vertex) queue.remove();
			u.setSeen();
			for (Edge e : u.getAdj()) {
				Vertex v = e.otherEnd(u);
				if (v.distanceObj.getDistance() > u.distanceObj.getDistance()
						+ e.getWeight()) {
					v.distanceObj.setDistance(u.distanceObj.getDistance()
							+ e.getWeight());
					v.setParent(u);
					v.distanceObj.setInfinity(false);
					v.setParentEdge(e);
					queue.decreaseKey(v);
				}
			}
		}
		return graph.getParentEdgesAndPrintPath("DIJ");
	}

	public static void main(String[] args) {
		Dijkstra obj = new Dijkstra();
		obj.graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		obj.shortestPath();

	}

}