import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MSTUsingPriorityQueue {

	/**
	 * This method Accepts a graph and returns the weight of the Minimum
	 * Spanning tree. This implementation uses priorityQueue in order to select
	 * the edge with minimum Weight
	 * 
	 * Space Complexity of this algorithm is O(E) where E is the number of edges in the graph. 
	 * @param graph
	 * @return
	 */
	public static int PrimMST(Graph graph) {

		/**
		 * Invariants:
		 * src : Randomly chosen starting point
		 * u - Source vertex of the edge
		 * v - Destination vertex of the edge
		 * wMST - total weight of the MST
		 * edge - current edge
		 * queue - priority Queue that returns the 
		 */
		
		int wMST = 0;
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
			vertex.setParent(null);
		}

		Queue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.getWeight() - o2.getWeight();
			}
		});

		/**
		 * Select Any random Vertex, in this case we selected first one, and add
		 * all edges to the priority Queue.
		 */
	
		Vertex src = graph.getVerts().get(1);
		for (Edge edge : src.getAdj()) {
			queue.add(edge);
		}
		/**
		 * Set Source as seen.
		 */
		src.setSeen(true);

		/**
		 * While Queue doesn't become empty, start removing edges that have the
		 * least weight. If there is an edge thats got both visited vertices,
		 * move to the next one. Since this is a connected graph, all vertices
		 * will be visited no matter which vertex we start from
		 */
		Vertex u =null;
		Vertex v=null;
		while (!queue.isEmpty()) {
			Edge e = queue.remove();
			if (!(e.getTo().isSeen() && e.getFrom().isSeen())) {

				/**
				 * Queue will never have edges that have never been visited.
				 */
				if(e.getFrom().isSeen())
					{
						u=e.getFrom();
						v=e.getTo() ;
					}
				else
				{
					v=e.getFrom();
					u=e.getTo() ;
				}
				/**
				 * Add the destination to the Tree, increase weight of the tree
				 * by weight of the edge.
				 */
				v.setSeen(true);
				v.setParent(u);
				wMST += e.getWeight();
				/**
				 * Add all edges of the destination vertex to the queue.
				 */
				for (Edge edge : v.getAdj()) {
					if (!edge.getTo().isSeen() || !edge.getFrom().isSeen()) {
						queue.add(edge);
					}
				}
			}
		}
		return wMST;
	}

	public static void main(String[] args) {
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, GraphType.UNDIRECTED);
		Statistics stats = new Statistics();
		stats.timer();
		int output = PrimMST(graph);
		stats.timer("Minimum Spanning Tree Function");
		System.out.println("Weight of the tree found to be " + output);
	}
}
