/**
 * Class that represents an arc in a Graph
 *
 */
public class Edge {
	private Vertex from; // head vertex
	private Vertex to; // tail vertex
	private int weight;// weight of the arc

	/**
	 * Constructor for Edge
	 * 
	 * @param u
	 *            : Vertex - The head of the arc
	 * @param v
	 *            : Vertex - The tail of the arc
	 * @param w
	 *            : int - The weight associated with the arc
	 */
	Edge(Vertex u, Vertex v, int w) {
		from = u;
		to = v;
		weight = w;
	}

	
	
	/**
	 * @return the from
	 */
	public Vertex getFrom() {
		return from;
	}



	/**
	 * @return the to
	 */
	public Vertex getTo() {
		return to;
	}



	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}



	/**
	 * Method to find the other end end of the arc given a vertex reference
	 * 
	 * @param u
	 *            : Vertex
	 * @return
	 */
	public Vertex otherEnd(Vertex u) {
		// if the vertex u is the head of the arc, then return the tail else
		// return the head
		if (from == u) {
			return to;
		} else {
			return from;
		}
	}
	

	/**
	 * Method to represent the edge in the form (x,y) where x is the head of the
	 * arc and y is the tail of the arc
	 */
	public String toString() {
		return "(" + from + "," + to + ")";
	}
}
