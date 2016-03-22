public class Dijkstra extends IndexedPriorityQueue {

	Graph graph;
	IndexedPriorityQueue indexedPQ = new IndexedPriorityQueue();

	/**
	 * The method calls the method graphInput. This method initializes the graph
	 * object with the given input.
	 */
	public void readGraph() {
		graph = Graph.graphInput("lp3_l1_in2", true);
	}

	public void shortestPath() {
		/*
		 * Initialize the vertices.
		 */
		 graph.initialize(graph);

		/*
		 * compute distance to a vertex v that is the shortest distance from
		 * source to a vertex v and add to the indexed priority queue. 
		 *
		 * Add source(first) vertex to the queue.Vertex is marked seen and
		 * parent is marked to null.
		 */
		Vertex sourceVertex = graph.verts.get(1);
		sourceVertex.seen = true;
		indexedPQ.insert(sourceVertex);
		System.out.println("The vertexa t zeroth location of the oq is"+indexedPQ.queue.get(0));
		//for every vertex calculate the shortest from the source to the vertex.  
		for (Vertex vertex:graph) {
			//System.out.println(vertex.name+" is the current node.");
			 Vertex current =vertex;
			 for (Edge edge : current.Adj) {
				Vertex v = edge.otherEnd(current);
				if (v.seen == false) {
				//	System.out.println("distance from source and"+" "+v.name+"the other end is");
					v.distanceObj.distance = current.distanceObj.distance
							+ edge.Weight;
					//System.out.println(v.distanceObj.distance);
					v.parent = current;
					v.seen = true;
					indexedPQ.insert(v);
				}
			}
		
		}
		
		
		while(!indexedPQ.queue.isEmpty()){
			System.out.println(indexedPQ.queue.remove(0));
		}
		}
	

	public static void main(String[] args) {
		Dijkstra obj = new Dijkstra();
		obj.readGraph();
		obj.shortestPath();

	}

}
