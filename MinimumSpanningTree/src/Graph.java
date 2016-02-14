
/**
 * Class to represent a graph
 * 
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Graph implements Iterable<Vertex> {
	private List<Vertex> verts; // array of vertices
	private int numNodes; // number of verices in the graph

	/**
	 * @return the verts
	 */
	public List<Vertex> getVerts() {
		return verts;
	}

	/**
	 * @param verts
	 *            the verts to set
	 */
	public void setVerts(List<Vertex> verts) {
		this.verts = verts;
	}

	/**
	 * Constructor for Graph
	 * 
	 * @param size
	 *            : int - number of vertices
	 */
	Graph(int size) {
		numNodes = size;
		verts = new ArrayList<>(size + 1);
		verts.add(0, null);
		// create an array of Vertex objects
		for (int i = 1; i <= size; i++)
			verts.add(i, new Vertex(i));
	}

	/**
	 * Method to add an edge to the graph
	 * 
	 * @param a
	 *            : int - one end of edge
	 * @param b
	 *            : int - other end of edge
	 * @param weight
	 *            : int - the weight of the edge
	 */
	void addEdge(int a, int b, int weight) {
		Vertex u = verts.get(a);
		Vertex v = verts.get(b);
		Edge e = new Edge(u, v, weight);
		u.getAdj().add(e);
		v.getAdj().add(e);
	}

	/**
	 * Method to add an arc (directed edge) to the graph
	 * 
	 * @param a
	 *            : int - the head of the arc
	 * @param b
	 *            : int - the tail of the arc
	 * @param weight
	 *            : int - the weight of the arc
	 */
	void addDirectedEdge(int a, int b, int weight) {
		Vertex head = verts.get(a);
		Vertex tail = verts.get(b);
		Edge e = new Edge(head, tail, weight);
		head.getAdj().add(e);
		tail.getRevAdj().add(e);
	}

	public Graph transpose(boolean directed) {
		Graph transpose = new Graph(this.verts.size());
		for (Vertex vertex : this) {
			for (Edge edge : vertex.getAdj()) {
				if (directed) {
					transpose.addDirectedEdge(edge.getTo().getName(), edge.getFrom().getName(), edge.getWeight());
				} else {
					transpose.addEdge(edge.getTo().getName(), edge.getFrom().getName(), edge.getWeight());
				}
			}

		}
		return transpose;
	}

	/**
	 * Method to create an instance of VertexIterator
	 */
	public Iterator<Vertex> iterator() {
		return new VertexIterator();
	}

	/**
	 * A Custom Iterator Class for iterating through the vertices in a graph
	 * 
	 *
	 * @param <Vertex>
	 */
	private class VertexIterator implements Iterator<Vertex> {
		private Iterator<Vertex> it;

		/**
		 * Constructor for VertexIterator
		 * 
		 * @param v
		 *            : Array of vertices
		 * @param n
		 *            : int - Size of the graph
		 */
		private VertexIterator() {
			it = verts.iterator();
			it.next(); // Index 0 is not used. Skip it.
		}

		/**
		 * Method to check if there is any vertex left in the iteration
		 * Overrides the default hasNext() method of Iterator Class
		 */
		public boolean hasNext() {
			return it.hasNext();
		}

		/**
		 * Method to return the next Vertex object in the iteration Overrides
		 * the default next() method of Iterator Class
		 */
		public Vertex next() {
			return it.next();
		}

		/**
		 * Throws an error if a vertex is attempted to be removed
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * This method accepts graph from User.
	 * 
	 * @param inputFilePath
	 *            - present if user has provided input data through file instead
	 *            of command prompt.
	 * @return graph object
	 */
	public static Graph acceptGraphInput(String inputFilePath, boolean isDirected) {
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
			graph = Graph.readGraph(sc, isDirected);

		} catch (FileNotFoundException e) {
			System.out.println("Exception occured while Reading input file");
			e.printStackTrace();
		}
		return graph;
	}

	/**
	 * This method reads graph input from scanner and returns the
	 * 
	 * @param in
	 * @param directed
	 * @return
	 */
	private static Graph readGraph(Scanner in, boolean directed) {
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph

		// create a graph instance
		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			if (directed) {
				g.addDirectedEdge(u, v, w);
			} else {
				g.addEdge(u, v, w);
			}
		}
		in.close();
		return g;
	}
}
