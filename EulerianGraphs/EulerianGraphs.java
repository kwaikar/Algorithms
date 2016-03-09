import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * A graph G is called Eulerian if it is connected and the degree of every
 * vertex is an even number. It is known that such graphs have a cycle (not
 * simple) that goes through every edge of the graph exactly once. A connected
 * graph that has exactly 2 vertices of odd degree has an Eulerian path. Write a
 * function that outputs one of the messages that applies to the given graph.
 * 
 * void testEulerian(Graph g) { ... }
 * 
 * Possible outputs:
 * 
 * Graph is Eulerian. Graph has an Eulerian Path between vertices ?? and ??.
 * Graph is not connected. Graph is not Eulerian. It has ?? vertices of odd
 * degree.
 * 
 * 
 * @author Kanchan Waikar Date Created : 2:09:26 PM
 *
 */
public class EulerianGraphs {

	public static void main(String[] args) {

		EulerianGraphs eulerianGraphs = new EulerianGraphs();
		Graph graph = eulerianGraphs.acceptGraphInput(args.length > 0 ? args[0] : null);
		Statistics stats= new Statistics();
		stats.timer();
		eulerianGraphs.testEulerian(graph);
		stats.timer("Eulerian Test Function");
	}

	/**
	 * This method accepts graph from User.
	 * @param inputFilePath - present if user has provided input data through file instead of command prompt.
	 * @return graph object
	 */ 
	public Graph acceptGraphInput(String inputFilePath) {
		Graph graph = null;
		try {
			Scanner sc = null;
			File inputFile = inputFilePath != null ? new File(inputFilePath) : null;
			if (inputFile != null && inputFile.exists()) {
				sc = new Scanner(inputFile);
			} else {
				System.out.println(
						"Please enter dimensions of the graph (#nodes, #edges) followed by edges in format (left,right,weight)");
				sc = new Scanner(System.in);
			}
			graph = Graph.readGraph(sc, false);

		} catch (FileNotFoundException e) {
			System.out.println("Exception occured while Reading input file");
			e.printStackTrace();
		}
		return graph;
	}

	/**
	 * This method accepts a graph and verifies where graph is eulerian or not.
	 * @param graph - Graph to be tested for presence of Eulerian Path
	 * @return boolean Flag - Whether Graph has Eulerian Path or not.
	 */
	public  boolean testEulerian(Graph  graph) {
		/**
		 * Invariants: connected : flag for checking whether graph is
		 * connected or not. Default value is set as true.
		 * oddDegreeVerticesList : contains list of vertices that have odd degree queue
		 * hasEulerianPath : Whether graph has eulerian path.
		 */
		List<Vertex> oddDegreeVerticesList = new ArrayList<Vertex>();
		boolean connected = true;
		boolean hasEulerianPath=false;

		/**
		 * Select one random node from the graph, visit entire graph through
		 * that node.
		 */
		Queue<Vertex> queue = new ArrayDeque<>();
		queue.add(graph.getVerts().get(1));
		while (!queue.isEmpty()) {
			Vertex currentVertex = queue.remove();
			if(!currentVertex.isSeen())
			{
				currentVertex.setSeen(true);
				for (Edge edge :  currentVertex.getAdj()) {
					if (!edge.getTo().isSeen()) {
						queue.add(edge.getTo());
					}
				}	
			}
		}
		queue = null;
		/**
		 * once all nodes from zero'th node are marked, if there are no
		 * "unseen" nodes left, it means that the graph is connected. If
		 * there is even a single node that was not reached through the
		 * iteration, it means graph is not connected
		 */
		for (Vertex vertex : graph) {
			if (!vertex.isSeen()) {
				connected = false;
				break;
			}
			if (vertex.getAdj().size() % 2 != 0) {
				oddDegreeVerticesList.add(vertex);
			}
		}

		System.out.println("-----------------------------------------------------------------------------");
		if (connected) {
			if (oddDegreeVerticesList.size() == 0) {
				/**
				 * Connected graph with no odd degree vertices is eulerian
				 * graph
				 */
				hasEulerianPath=true;
				System.out.println("Graph is Eulerian");
			} else if (oddDegreeVerticesList.size() == 2) {

				/**
				 * Semi-Eulerian graph: If there are exactly two vertices of
				 * odd degree, all Eulerian trails start at one of them and
				 * end at the other.
				 */
				System.out.println("Eulerian Path found between " + oddDegreeVerticesList.get(0) + " and "
						+ oddDegreeVerticesList.get(1));
				hasEulerianPath=true;
			} else if (oddDegreeVerticesList.size() == 1 || oddDegreeVerticesList.size() > 2) {
				System.out.println("Graph is not Eulerian.  It has " + oddDegreeVerticesList.size()
						+ " vertices of odd degree.");
			}
		} else {
			System.out.println("Graph is not connected");
		}

		System.out.println("-----------------------------------------------------------------------------");
		return hasEulerianPath;
	}
}
