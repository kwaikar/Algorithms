/**
 * Class that represents an arc in a Graph
 *
 */
public class Edge {
	private Vertex from; // head vertex
	private Vertex to; // tail vertex
	private int weight;// weight of the arc
	private int originalWeight;// weight of the arc
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
		originalWeight=weight;
	}


	public static Edge reverseEdge(Edge edge)
	{
		Edge revEdge = new Edge(edge.getTo(),edge.getFrom(),edge.weight);
		revEdge.originalWeight=edge.originalWeight;
		return revEdge;
	}
	/**
	 * @return the from
	 */
	public Vertex getFrom() {
		return from;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
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
	 * This method reduces weight of the edge by the quantity specified.
	 * @param reductionQuantity
	 */
	public void reduceWeight(int reductionQuantity) {
		this.weight = this.weight - reductionQuantity;
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
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Vertex from) {
		this.from = from;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Vertex to) {
		this.to = to;
	}

	/**
	 * Method to represent the edge in the form (x,y) where x is the head of the
	 * arc and y is the tail of the arc
	 */
	public String toString() {
		return "(" + from + "," + to + ")";
	}
}
