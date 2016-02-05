import java.util.Stack;

/**
 * Strongly connected components of a directed graph. Implement the algorithm
 * for finding strongly connected components of a directed graph (see page 617
 * of Cormen et al, Introduction to algorithms, 3rd ed.). Run DFS on G and push
 * the nodes into a stack as they complete DFSVisit(). Find G^T, the graph
 * obtained by reversing all edges of G. Run DFS on G^T, but using the stack
 * order in DFS outer loop. Each DSF tree in the second DFS is a strongly
 * connected component.
 * 
 * int stronglyConnectedComponents(Graph g) { ... } Each node is marked with a
 * component number, and the function returns the number of strongly connected
 * components of G.
 * 
 * @author Kanchan Waikar Time Created : 11:18:03 PM
 *
 */
public class SCCDetectionUsingKosarajuAlgorithm {

	public static void main(String[] args) {
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, true);
		Statistics stats = new Statistics();
		stats.timer();
		SCCDetectionUsingKosarajuAlgorithm.printStronglyConnectedComponents(graph);
		System.out.println();
		stats.timer("Strongly Connected Components Detection Test Function");
	}

	/**
	 * This method prints Strongly connected components of the graph along with
	 * their component Root ID
	 * 
	 * @param graph
	 */
	public static void printStronglyConnectedComponents(Graph graph) {
		Stack<Vertex> vertices = new Stack<>();
		for (Vertex u : graph) {
			if (!u.isSeen()) {
				visit(u, vertices);
			}
		}
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}
		graph = graph.transpose(true);
		while (!vertices.isEmpty()) {
			System.out.println();
			Vertex entry = vertices.pop();
			if (!graph.getVerts().get(entry.getName()).isSeen()) {
				System.out.println();
				visitAndAssign(graph.getVerts().get(entry.getName()), entry.getName());
			}
		}
	}

	/**
	 * DFSVisit method for Component ID assignment
	 * @param u - vertex to be visited 
	 * @param ComponentId - Component Id to be assignment
	 */
	private static void visitAndAssign(Vertex u, Integer ComponentId) {
		if (!u.isSeen()) {
			u.setSeen();
			for (Edge edge : u.getAdj()) {
				if (u.getComponentId() == 0) {
					visitAndAssign(edge.getTo(), ComponentId);
				}
			}
			u.setComponentId(ComponentId);
			System.out.print(u + "[" + ComponentId + "]  ");

		}
	}

	/**
	 * This function recursively visits entire tree using DFS approach
	 * 
	 * @param u
	 * @param vertices
	 */
	private static void visit(Vertex u, Stack<Vertex> vertices) {
		if (!u.isSeen()) {
			u.setSeen(true);
			for (Edge edge : u.getAdj()) {
				visit(edge.getTo(), vertices);
			}
			vertices.push(u);
		}
	}

}
