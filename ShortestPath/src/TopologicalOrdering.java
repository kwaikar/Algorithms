import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * The Class in am implementation of Validation of Directed Acyclic Graph and
 * finding topological ordering.
 * 
 * @author M Krishna Kavya
 * 
 */

public class TopologicalOrdering {

	/**
	 * Algorithm 1. Remove vertices with no incoming edges, one at a time, along
	 * with their incident edges, and add them to a list.
	 * 
	 * @param graphobject
	 *            - A Directed Graph.
	 * @return - List of vertices in topological order.
	 */

	public static List<Vertex> toplogicalOrder1(Graph graphobject) {
		/**
		 * validate method- to check if the graph is acyclic.if the graph
		 * contains a cycle It will return Null.
		 */
		if (!validate(graphobject)) {
			System.out.println("The Graph is cyclic");
			return null;
		} else {

			/*
			 * 
			 * Queue has the nodes with indegree 0 are added to the queue. we
			 * compute the size of the vertices and initialize all the nodes
			 * with in degree 0.
			 */

			Queue<Integer> queue = new LinkedList<Integer>();
			List<Vertex> result = new ArrayList<Vertex>();

			/*
			 * fetch the number of vertices and initialise an indegree array for
			 * all vertices.
			 */

			int number_of_vertices = graphobject.getNumNodes();
			int indegree[] = new int[graphobject.getVerts().size()];
			for (int i = 1; i <= number_of_vertices; i++) {
				indegree[i] = 0;
			}

			/*
			 * We compute indegree for each node. The number of head ends
			 * adjacent to a vertex is called the indegree of the vertex.
			 */
			List<Vertex> vertex = graphobject.getVerts();

			for (int i = 1; i <= number_of_vertices; i++) {
				Vertex v = vertex.get(i);
				List<Edge> edges = v.getAdj();
				for (Edge e : edges) {
					Vertex otherEndVertex = e.otherEnd(v);
					indegree[otherEndVertex.getName()] = indegree[otherEndVertex.getName()] + 1;
				}
			}

			/*
			 * The queue has the nodes with indegree 0 to sort the elements in
			 * topological order, we start from the node with indegree 0. we
			 * parse to all the kinds of the node. we decrement the indegree by
			 * one and if the indegree of any child node is 0 we add it to the
			 * queue. According to the algorithm we start at the node with 0
			 * indegree and parse to its child nodes and remove the parent node.
			 * Here we decrease the indegree instead of removing the node. we
			 * add the elements removes to the queue one by one which gives us
			 * the topological sort.
			 */
			for (int i = 1; i <= number_of_vertices; i++) {
				if (indegree[i] == 0) {
					queue.add(i);
				}
			}

			while (!queue.isEmpty()) {
				int currentnode = queue.remove();
				Vertex v = vertex.get(currentnode);
				result.add(v);
				List<Edge> edge = v.getAdj();
				for (Edge e : edge) {
					Vertex otherEndVertex = e.otherEnd(v);
					indegree[otherEndVertex.getName()]--;

					if (indegree[otherEndVertex.getName()] == 0) {
						queue.add(otherEndVertex.getName());
					}
				}

			}
			return result;
		}
	}

	/**
	 * 
	 * Algorithm 2- In this method we implement the Depth first search algorithm
	 * to get the topological order of a graph. By default all the nodes in the
	 * graph are not seen( visited). we follow the following steps: 1. start
	 * from any node. In this program we have all vertices in a list and start
	 * parsing from the first vertex . 2. Once we visit a vertex me set the
	 * value of seen to be true. 3. we parse till the node with no child nodes.
	 * 4. condition:A node is considered to be done processing when all the
	 * child nodes are visited along with the node itself. 5. once the condition
	 * is met we push the element into stack. 6. once the node is done
	 * processing we check with parent node. Once we are done till parsing to
	 * the top of the path. we start at a new vertex. All the nodes which are
	 * not visited are processed. 7. The stack has the elements in topological
	 * order.
	 * 
	 */
	public static Stack<Vertex> topologicalOrderUsingDFS(Graph g) {
		if (!validate(g)) {
			System.out.println("The Graph is not Acyclic");
			return null;
		} else {
			List<Vertex> vertices = g.getVerts();
			Stack<Vertex> stack = new Stack<Vertex>();
			Set<Vertex> visited = new HashSet<Vertex>();
			for (int i = 1; i < vertices.size(); i++) {
				Vertex currentVertex = vertices.get(i);
				if (visited.contains(currentVertex)) {
					continue;
				}
				topologicalSort(currentVertex, stack, visited);
			}
			return stack;
		}
	}

	/**
	 * The topological order implements the Depth first search and adds the
	 * elements to the stack.
	 * 
	 * @param currentVertex
	 * @param stack
	 * @param visited
	 */
	private static void topologicalSort(Vertex currentVertex,
			Stack<Vertex> stack, Set<Vertex> visited) {
		visited.add(currentVertex);
		currentVertex.setSeen(true);
		List<Edge> edgeList = currentVertex.getAdj();

		for (Edge edge : edgeList) {
			Vertex otherEndVertex = edge.otherEnd(currentVertex);
			if (visited.contains(otherEndVertex)) {
				continue;
			}
			topologicalSort(otherEndVertex, stack, visited);
		}
		stack.add(currentVertex);
	}

	/**
	 * 
	 * The validate method is used to check the graph is acyclic. we implement
	 * the validation by checking with DFS. A cycle can be tracked in a DFS is
	 * found when we revisit a visited node. we check if a path has a
	 * cycle.while it has a cycle he return false.
	 * 
	 * @param graph
	 *            - input graph.
	 * @return - boolean value true when there is a cycle.
	 */
	public static boolean validate(Graph graph) {
		Set<Vertex> path = new HashSet<Vertex>();
		List<Vertex> vertices = graph.getVerts();
		for (int i = 1; i < vertices.size(); i++) {
			if (isCyclic(vertices.get(i), path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the there is a cycle in the graph
	 * 
	 * @param vertex
	 *            - vertex
	 * @param path
	 *            -The path has all the vertices that are already visited.
	 * @return- true when we visit already visited node. Else false.
	 */
	public static boolean isCyclic(Vertex vertex, Set<Vertex> path) {

		if (path.contains(vertex)) {
			return true;
		} else {
			path.add(vertex);
			vertex.setSeen(true);
			List<Edge> edgeList = vertex.getAdj();
			for (Edge edge : edgeList) {
				if (isCyclic(edge.otherEnd(vertex), path)) {
					return true;
				} else {
					path.remove(vertex);
				}
			}

		}
		return false;
	}

	public static void main(String[] inputFilePath) throws FileNotFoundException {

		
		// passing graph as an input.
		Scanner sc = null;
		File inputFile = inputFilePath.length>0?new File(inputFilePath[0]): null;
		if (inputFile != null && inputFile.exists()) {
			sc = new Scanner(inputFile);
		} else {
			System.out.println(
					"Please enter dimensions of the graph (#nodes, #edges) followed by edges in format (left,right,weight)");
			sc = new Scanner(System.in);
		}
		
		
		
		
		/**
		 * Creating a graph object. in -taking input from file( vertices and
		 * nodes) true- The graph is a directed one.
		 */
		Timer.timer();
		Graph graphobject = Graph.readGraph(sc, GraphType.DIRECTED);

		List<Vertex> topological_order = toplogicalOrder1(graphobject);
		
		System.out.println("The topological order after the implementation of algorithm1:");
		System.out.println();

		for (Vertex vertex : topological_order) {
			System.out.print(" " + vertex.getName());
		}
		System.out.println();
		System.out.println();
		System.out.println(" The Timer details");
		
		Timer.timer();
		Timer.timer();
		Stack<Vertex> topological_order2 = topologicalOrderUsingDFS(graphobject);
		System.out.println();
		
		
		System.out.println("The topological order after the implementation of algorithm2:");
		
		while (!((topological_order2).isEmpty())) {
			System.out.print(" " + topological_order2.pop());
		}
		System.out.println();
		System.out.println();
		System.out.println(" The Timer details");
		Timer.timer();

	}

}
