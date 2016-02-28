
/**
 * Class to represent a graph
 * 
 *
 */

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Graph<V extends Vertex, E extends Edge> implements Iterable<V> {
	private List<V> verts; // array of vertices
	private int numNodes; // number of verices in the graph

	/**
	 * @return the verts
	 */
	public List<V> getVerts() {
		return verts;
	}

	/**
	 * @param verts
	 *            the verts to set
	 */
	public void setVerts(List<V> verts) {
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
		{
		        V v =(V)V.getInstance(i);
					verts.add(i,v );	
		}
			 
			
	}

	/**
	 * Method to add an Edge to the graph
	 * 
	 * @param a
	 *            : int - one end of Edge
	 * @param b
	 *            : int - other end of Edge
	 * @param weight
	 *            : int - the weight of the Edge
	 */
	void addEdge(int a, int b, int weight) {
		V u = verts.get(a);
		V v = verts.get(b);

        E e = (E) E.getInstance(u,v,weight);
		//E e =(E) new Edge(u,v,weight);
		e.setFrom(u);
		e.setTo(v);
		e.setWeight(weight);
		u.getAdj().add(e);
		v.getAdj().add(e);
	}

	/**
	 * Method to add an arc (directed Edge) to the graph
	 * 
	 * @param a
	 *            : int - the head of the arc
	 * @param b
	 *            : int - the tail of the arc
	 * @param weight
	 *            : int - the weight of the arc
	 */
	void addDirectedEdge(int a, int b, int weight) {
		V head = verts.get(a);
		V tail = verts.get(b);

	//	E e =(E) new Edge(head,tail,weight);

        E e =(E)Edge.getInstance(head,tail,weight);
		e.setFrom(head);
		e.setTo(tail);
		e.setWeight(weight);
		head.getAdj().add(e);
		tail.getRevAdj().add(e);
	}

	/**
	 * Method to create an instance of VertexIterator
	 */
	public Iterator<V> iterator() {
		return new VertexIterator();
	}

	/**
	 * A Custom Iterator Class for iterating through the vertices in a graph
	 * 
	 *
	 * @param <Vertex>
	 */
	private class VertexIterator implements Iterator<V> {
		private Iterator<V> it;

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
		 * Method to check if there is any Vertex left in the iteration
		 * Overrides the default hasNext() method of Iterator Class
		 */
		public boolean hasNext() {
			return it.hasNext();
		}

		/**
		 * Method to return the next Vertex object in the iteration Overrides
		 * the default next() method of Iterator Class
		 */
		public V next() {
			return it.next();
		}

		/**
		 * Throws an error if a Vertex is attempted to be removed
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = Graph.readGraph(sc, false);
		System.out.println("Read graph:" + g.verts);
	}

	public static <V extends Vertex,E extends Edge> Graph<V, E> readGraph(Scanner in, boolean directed) {
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph

		// create a graph instance
		Graph<V,E> g = new Graph<V,E>(n);
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
