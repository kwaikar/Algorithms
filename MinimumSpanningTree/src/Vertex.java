
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vertex implements Comparable<Vertex>, Index{
	private int name; // name of the vertex
	private boolean seen; // flag to check if the vertex has already been
							// visited
	private Vertex parent; // parent of the vertex
	private int distance; // distance to the vertex from the source vertex
	private List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList
	private int count;
	private int componentId;
	private int index;
	private int rank;
	
	

	/**
	 * Kanchan : Added for implementing Indexed binary heap.
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void putIndex(int index) {
		this.index = index;
	}

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
	 * This method transposes both, adjecency list as well as reverseadjecency list
	 */
	public void transposeAdjecencyList()
	{
		for (Edge edge : this.getAdj()) {
			Vertex temp = edge.getTo();
			edge.setTo(edge.getFrom());
			edge.setFrom(temp);
		}
		for (Edge edge : this.getRevAdj()) {
			Vertex temp = edge.getTo();
			edge.setTo(edge.getFrom());
			edge.setFrom(temp);
		}
	}
	
	/**
	 * @return the componentId
	 */
	public int getComponentId() {
		return componentId;
	}



	/**
	 * @param componentId the componentId to set
	 */
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}



	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}



	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}



	/**
	 * @param seen the seen to set
	 */
	public void setSeen(boolean seen) {
		this.seen = seen;
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
	public void setSeen() {
		this.seen = true;
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
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
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
	public List<Edge> getAdj() {
		return Adj;
	}



	/**
	 * @param adj the adj to set
	 */
	public void setAdj(List<Edge> adj) {
		Adj = adj;
	}



	/**
	 * @return the revAdj
	 */
	public List<Edge> getRevAdj() {
		return revAdj;
	}



	/**
	 * @param revAdj the revAdj to set
	 */
	public void setRevAdj(List<Edge> revAdj) {
		this.revAdj = revAdj;
	}



	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name)+":"+ distance ;
	}


	@Override
	public int compareTo(Vertex o) {
		return this.getDistance()-o.getDistance();
	}
	
	
	
}
