import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BellmanFord {

	public List<Edge> getShortestPath(Graph graph) {
		graph.initialize(graph);

		Queue<Vertex> queue = new PriorityQueue<Vertex>(graph.getVerts().size());
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
		queue.add(sourceVertex);

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			u.setSeen();
			if (u.getCount() >= graph.getVerts().size()) {
				System.out.println("Graph has negative cycle");
				break;
			}
			for (Edge edge : u.getAdj()) {
				Vertex v = edge.otherEnd(u);
				if (v.distanceObj.isInfinity() || (v.distanceObj.getDistance() > (u.distanceObj.getDistance() + edge.getWeight()))) {
					v.distanceObj.setDistance(u.distanceObj.getDistance() + edge.getWeight());
					v.distanceObj.setInfinity(false);
					v.setParent(u);
					v.setParentEdge(edge);
					if(!v.isSeen())
					{
						queue.add(v);
						v.setSeen();
					}
					u.setCount(u.getCount()+1);
				}
			}

		}
		

		return graph.getParentEdgesAndPrintPath("B-F");
	}
	
	
	public static void main(String[] args) {
		BellmanFord obj = new BellmanFord();
	List<Edge> edges = obj.getShortestPath(Graph.acceptGraphInput(args[0], GraphType.DIRECTED));

	}

}
