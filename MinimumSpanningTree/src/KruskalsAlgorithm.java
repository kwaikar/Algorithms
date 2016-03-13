import java.io.FileNotFoundException;
import java.util.Queue;
/**
 * This algorithm Finds MST using Kruskals Algorithm for finding Minimum spanning tree.
 * @author Kavya M.
 *
 */
public class KruskalsAlgorithm {

	/**
	 * Creates new set that represents itself and initializes the rank for the same to zero.
	 * @param vertex
	 */
	private static void makeSet(Vertex vertex) {
		vertex.setParent(vertex);
		vertex.setRank(0);
	}
	/**
	 * This method returns the parent of the input vertex.
	 * This function loops until it finds its parent; stops when it realizes it is its own parent. 
	 * All vertices from same set will have same parent.
	 * 
	 * Recursion was avoided here for performance benefit.  
	 * 
	 * @param vertex - Parent vertex of input.
	 * @return
	 */
	private static Vertex find(Vertex vertex) 
	{
		/**
		 * Invariant : 
		 * temp  : for looping through parents. Ultimately, it would point to parent of the original vertex. 
		 */
		Vertex temp=vertex;
		while (temp != vertex.getParent()) {
			temp = vertex.getParent();
		}
		return temp;
	}
	/**
	 * This function unites two vertices that are connected by an edge. Unification happens only when both do not already belong to the same set.
	 * @param x
	 * @param y
	 */
	private static void union(Vertex x, Vertex y) {
		
		if (x.getRank() > y.getRank()) {
			/**
			 * X is already a parent of someone, assign it as parent of 
			 */
			y.setParent(x);
		} else if (y.getRank() > x.getRank()) {
			/**
			 * Y is already parent higher in hierarchy. Make it a parent of X 
			 */
			x.setParent(y);
		} else {
			/**
			 * Both are at equal level. Promote any of the two as parent.
			 */
			x.setParent(y);
			y.setRank(y.getRank() + 1);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Statistics stats = new Statistics();
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, GraphType.UNDIRECTED_EDGE_SORTED);
		stats.timer("Read input graph");
		stats.timer();
		int wmst = minimumSpanningTree(graph);
		stats.timer("Minimum Spanning Tree retrieval Statistics");
		System.out.println("Total Weight of the MST was found to be : "+wmst);
	}

	/**
	 * This method finds the minimum spanning tree by removing the lowest weight edge and merging it with the set. 
	 * @param undirectedEdgeSortedGraph - Input graph that has undirected edges sorted by weight.
	 * @return
	 */
	public static int minimumSpanningTree(Graph undirectedEdgeSortedGraph) {
		
		/**
		 * Initialize rank for each vertex and set parent for each vertex as itself.
		 */
		/**
		 * Invariants:
		 * wmst - Weight of the MST to be returned.
		 * ru - Representative of U
		 * rv - Representative of V
		 * edge - Lowest weight edge returned.
		 */
		int wmst = 0;
		for (Vertex vertex : undirectedEdgeSortedGraph) {
			makeSet(vertex);
		}
		Queue<Edge> edge = undirectedEdgeSortedGraph.getEdgeQueueSortedAscByWeight();
		while (!edge.isEmpty()) {
			Edge e = edge.remove();
			/**
			 * Find parents of both the ends of the input edge. If they both have different parents, it means that both sets can be combined into one.
			 */
			Vertex ru = find(e.getFrom());
			Vertex rv = find(e.getTo());
			if (!ru.equals(rv)) {
				wmst = wmst + e.getWeight();
				union(ru, rv);
			}
		}
		return wmst;
	}
}
