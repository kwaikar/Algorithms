public class MSTUsingIndexedPriorityQueue {

    public static final int INFINITY = Integer.MAX_VALUE;
	/**
	 * This method Accepts a graph and returns the weight of the Minimum
	 * Spanning tree. This implementation uses priorityQueue in order to select
	 * the edge with minimum Weight
	 * 
	 * The space complexity of this implementation is O(V) instead of O(E)
	 * @param graph
	 * @return
	 */
	public static int PrimMST2(Graph graph) {

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
			vertex.setDistance(INFINITY);
		}

		IndexedHeap<Vertex> queue = new IndexedHeap<Vertex>(graph.getVerts().size());

		/**
		 * Select Any random Vertex, in this case we selected first one, and add
		 * all edges to the priority Queue.
		 */
	
		Vertex src = graph.getVerts().get(1);
		src.setDistance(0);
		for (Vertex vertex: graph.getVerts()) {
			if(vertex!=null)
			queue.add(vertex);
		}
		/**
		 * Set Source as seen.
		 */
		src.setSeen(true);

		/**
		 * While Queue doesn't become empty, start removing vertex that is at least distance from current node.
		 */
		while (!queue.isEmpty()) {
			Vertex u = (Vertex)queue.remove();
			u.setSeen(true);
			wMST+=u.getDistance();
			for (Edge edge : u.getAdj()) {
				Vertex v = edge.otherEnd(u);
				if (!v.isSeen()  && edge.getWeight()<v.getDistance()) {
					v.setDistance(edge.getWeight());
					v.setParent(u);
					queue.decreaseKey(v);
				}
			}
		}
		return wMST;
	}

	public static void main(String[] args) {
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, false);
		Statistics stats = new Statistics();
		stats.timer();
		int output = PrimMST2(graph);
		stats.timer("Minimum Spanning Tree Function");
		System.out.println("Weight of the tree found to be " + output);
	}
}
