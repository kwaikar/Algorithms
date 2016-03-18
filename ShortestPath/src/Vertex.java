/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.*;

public class Vertex {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public Distance distanceObj;
    
    /* 
     * Distance to the vertex from the source vertex
     * The inner class has 
     * value - This distance value
     * isInfinity- The boolean value represents the value of distance is infinite or not. 
     * 
     */
    public class Distance{
    	Distance(int distance, boolean isInfinity){
    		this.distance=distance;
    		this.isInfinity=isInfinity;
    	}
    	
		public int getDistance() {
			return distance;
		}
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public boolean isInfinity() {
			return isInfinity;
		}
		public void setInfinity(boolean isInfinity) {
			this.isInfinity = isInfinity;
		}

		public int distance;
    	public boolean isInfinity;
		
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
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
	distanceObj=new Distance(0,true);
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
}