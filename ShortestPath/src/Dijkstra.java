import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Maringanty, Krishna Kavya 
 * Date Created : Mar 26, 2016 - 2:48:15 PM
 */
public class Dijkstra {


 
	/**
	 * The method calls the method graphInput. This method initializes the graph
	 * object with the given input.
	 */

	public static  List<Edge> shortestPath(Graph graph) {
		/*
		 * Initialize the vertices.
		 */
		graph.initialize();

		IndexedHeap<Vertex> queue = new IndexedHeap<Vertex>(graph.getVerts().size());
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
	//	System.out.println("The vertexa t zeroth location of the oq is" + queue.peek());
		// for every vertex calculate the shortest from the source to the
		// vertex.
		for (Vertex vertex : graph) {
		//	System.out.println(vertex.getName() + " is the current node.");
			Vertex current = vertex;
			if (!current.distanceObj.isInfinity()) {
				for (Edge edge : current.getAdj()) {
					Vertex v = edge.otherEnd(current);
					if (v.isSeen() == false) {
						// System.out.println("distance from source and"+"
						// "+v.name+"the other end is");
						v.distanceObj.setInfinity(false);
						/*System.out.println("Setting distance for " + v + " : " + " ----" + current.getName() + "=>"
								+ current.distanceObj.getDistance() + "-" + edge.getWeight());*/
						v.distanceObj.setDistance(current.distanceObj.getDistance() + edge.getWeight());
						System.out.println(v.distanceObj.getDistance());
						v.setParent(current);
						v.setParentEdge(edge);
						v.setSeen(true);
						queue.insert(v);
					}
				}
			}
		}

		System.out.println(queue);
		while (!queue.isEmpty()) {
			Vertex u = (Vertex) queue.remove();
			u.setSeen();
			for (Edge e : u.getAdj()) {
				Vertex v = e.otherEnd(u);
				if (v.distanceObj.getDistance() > u.distanceObj.getDistance() + e.getWeight()) {
					v.distanceObj.setDistance(u.distanceObj.getDistance() + e.getWeight());
					v.setParent(u);
					v.distanceObj.setInfinity(false);
					v.setParentEdge(e);
					queue.decreaseKey(v);
				}
			}
		}
		return graph.getParentEdgesAndPrintPath("dij");
	}

	
	public static void main(String[] args) {
		Dijkstra obj = new Dijkstra();
		obj.shortestPath(Graph.acceptGraphInput(args[0], GraphType.DIRECTED));

	}

}