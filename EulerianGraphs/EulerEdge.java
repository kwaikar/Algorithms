/**
 * Class that represents an arc in a Graph
 *
 */
public class EulerEdge extends  Edge{
	private EulerVertex from; // head vertex
	private EulerVertex to; // tail vertex
	private boolean disabled;
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
	EulerEdge(EulerVertex u, EulerVertex v, int w) {
		super(u,v,w);
		from = u;
		to = v;
	}

	
	
	/**
	 * @return the from
	 */
	public EulerVertex getFrom() {
		return from;
	}



	/**
	 * @return the to
	 */
	public EulerVertex getTo() {
		return to;
	}

 



	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}



	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}



	/**
	 * Method to find the other end end of the arc given a vertex reference
	 * 
	 * @param u
	 *            : Vertex
	 * @return
	 */
	public EulerVertex otherEnd(EulerVertex u) {
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
		return "(" + from + "->" + to + ")";
	}
}
