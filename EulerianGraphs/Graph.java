/**
 * Class to represent a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Graph implements Iterable<EulerVertex> {
    private List<EulerVertex> verts; // array of vertices
    private int numNodes; // number of verices in the graph

    /**
	 * @return the verts
	 */
	public List<EulerVertex> getVerts() {
		return verts;
	}

	/**
	 * @param verts the verts to set
	 */
	public void setVerts(List<EulerVertex> verts) {
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
	// create an array of EulerVertex objects
	for (int i = 1; i <= size; i++)
	    verts.add(i, new EulerVertex(i));
    }

    /**
     * Method to add an EulerEdge to the graph
     * 
     * @param a
     *            : int - one end of EulerEdge
     * @param b
     *            : int - other end of EulerEdge
     * @param weight
     *            : int - the weight of the EulerEdge
     */
    void addEdge(int a, int b, int weight) {
	EulerVertex u = verts.get(a);
	EulerVertex v = verts.get(b);
	EulerEdge e = new EulerEdge(u, v, weight);
	u.getAdj().add(e);
	v.getAdj().add(e);
    }

    /**
     * Method to add an arc (directed EulerEdge) to the graph
     * 
     * @param a
     *            : int - the head of the arc
     * @param b
     *            : int - the tail of the arc
     * @param weight
     *            : int - the weight of the arc
     */
    void addDirectedEdge(int a, int b, int weight) {
	EulerVertex head = verts.get(a);
	EulerVertex tail = verts.get(b);
	EulerEdge e = new EulerEdge(head, tail, weight);
	head.getAdj().add(e);
	tail.getRevAdj().add(e);
    }


    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<EulerVertex> iterator() {
	return new VertexIterator();
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     * 
     *
     * @param <EulerVertex>
     */
    private class VertexIterator implements Iterator<EulerVertex> {
	private Iterator<EulerVertex> it;
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
	    it.next();  // Index 0 is not used.  Skip it.
	}

	/**
	 * Method to check if there is any EulerVertex left in the iteration
	 * Overrides the default hasNext() method of Iterator Class
	 */
	public boolean hasNext() {
	    return it.hasNext();
	}

	/**
	 * Method to return the next EulerVertex object in the iteration
	 * Overrides the default next() method of Iterator Class
	 */
	public EulerVertex next() {
	    return it.next();
	}

	/**
	 * Throws an error if a EulerVertex is attempted to be removed
	 */
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    public static Graph readGraph(Scanner in, boolean directed) {
	// read the graph related parameters
	int n = in.nextInt(); // number of vertices in the graph
	int m = in.nextInt(); // number of edges in the graph

	// create a graph instance
	Graph g = new Graph(n);
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    if(directed) {
		g.addDirectedEdge(u, v, w);
	    } else {
		g.addEdge(u, v, w);
	    }
	}
	in.close();
	return g;
    }
}
