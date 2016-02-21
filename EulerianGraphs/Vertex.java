
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	protected int name; // name of the vertex
	protected boolean seen; // flag to check if the vertex has already been
							// visited
	protected Vertex parent; // parent of the vertex
	protected int distance; // distance to the vertex from the source vertex
	private List  Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
	}

	
	
	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(int name) {
		this.name = name;
	}



	/**
	 * @return the seen
	 */
	public boolean isSeen() {
		return seen;
	}



	/**
	 * @param seen the seen to set
	 */
	public void setSeen(boolean seen) {
		this.seen = seen;
	}



	/**
	 * @return the parent
	 */
	public Vertex getParent() {
		return parent;
	}



	/**
	 * @param parent the parent to set
	 */
	public void setParent(Vertex parent) {
		this.parent = parent;
	}



	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}



	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}



	/**
	 * @return the adj
	 */
	public List<?extends Edge>  getAdj() {
		return Adj;
	}



	/**
	 * @param adj the adj to set
	 */
	public void setAdj(List  adj) {
		Adj = adj;
	}



	/**
	 * @return the revAdj
	 */
	public List<?extends Edge>  getRevAdj() {
		return revAdj;
	}



	/**
	 * @param revAdj the revAdj to set
	 */
	public void setRevAdj(List  revAdj) {
		this.revAdj = revAdj;
	}



	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}
	
	
	
}
