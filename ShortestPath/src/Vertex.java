
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Vertex implements Comparable<Vertex>, Index {
	private int name; // name of the vertex
	private boolean seen; // flag to check if the vertex has already been
							// visited
	private Vertex parent; // parent of the vertex
	private int distance; // distance to the vertex from the source vertex
	private List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList
	private PriorityQueue<Edge> sortedEdges, sortedRevEdges;
	private int count;
	private int componentId;
	private int index;
	private int rank;
	private PseudoVertex pseudoVertex;
	private boolean incomingSeen;
	private boolean outgoingSeen;
	private boolean partOfCycle;
	private boolean enabled = true;
	public Distance distanceObj;
	public Edge parentEdge;
	

	/**
	 * @return the parentEdge
	 */
	public Edge getParentEdge() {
		return parentEdge;
	}

	/**
	 * @param parentEdge
	 *            the parentEdge to set
	 */
	public void setParentEdge(Edge parentEdge) {
		this.parentEdge = parentEdge;
	}

	/*
	 * Distance to the vertex from the source vertex The inner class has value -
	 * This distance value isInfinity- The boolean value represents the value of
	 * distance is infinite or not.
	 * 
	 */
	public class Distance {
		Distance(int distance, boolean isInfinity) {
			this.distance = distance;
			this.isInfinity = isInfinity;
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

		int distance;
		private boolean isInfinity;

	}

	/**
	 * @return the incomingVisit
	 */
	public boolean isIncomingSeen() {
		return incomingSeen;
	}

	/**
	 * @param incomingVisit
	 *            the incomingVisit to set
	 */
	public void setIncomingSeen(boolean incomingVisit) {
		this.incomingSeen = incomingVisit;
	}

	/**
	 * @return the outgoingVisit
	 */
	public boolean isOutgoingSeen() {
		return outgoingSeen;
	}

	/**
	 * @param outgoingVisit
	 *            the outgoingVisit to set
	 */
	public void setOutgoingSeen(boolean outgoingSeen) {
		this.outgoingSeen = outgoingSeen;
	}

	/**
	 * @return the partOfCycle
	 */
	public boolean isPartOfCycle() {
		return partOfCycle;
	}

	/**
	 * @param partOfCycle
	 *            the partOfCycle to set
	 */
	public void setPartOfCycle(boolean partOfCycle) {
		this.partOfCycle = partOfCycle;
	}

	/**
	 * Kanchan : Added for implementing Indexed binary heap.
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void putIndex(int index) {
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Vertex other = (Vertex) obj;
		if (name != other.name)
			return false;
		return true;
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
		sortedEdges = new PriorityQueue<Edge>(new Comparator<Edge>() {
			public int compare(Edge o1, Edge o2) {
				return o1.getWeight() - o2.getWeight();
			}
		});
		distanceObj = new Distance(0, true);

		sortedRevEdges = new PriorityQueue<Edge>(new Comparator<Edge>() {
			public int compare(Edge o1, Edge o2) {
				return o1.getWeight() - o2.getWeight();
			}
		}); /* Only for Directed Edges which need sorting on weight. */
	}

	Vertex(int n, List<Edge> cycle) {
		if (cycle != null && cycle.size() != 0) {
			/**
			 * Custom constructor for Pseudo Vertices.
			 */
			pseudoVertex = new PseudoVertex(cycle);
			name = n;
			seen = false;
			parent = null;
			Adj = new ArrayList<Edge>();
			revAdj = new ArrayList<Edge>(); /* only for directed graphs */
			sortedRevEdges = Graph
					.getNewEdgeSortedPriorityQueue(); /*
														 * Only for Directed Edges
														 * which need sorting on
														 * weight.
														 */
			sortedEdges = Graph
					.getNewEdgeSortedPriorityQueue(); /*
														 * Only for Directed Edges
														 * which need sorting on
														 * weight.
														 */
			distanceObj = new Distance(0, true);

		}
	}

	/**
	 * @return the sortedEdges
	 */
	public PriorityQueue<Edge> getSortedEdges() {
		return sortedEdges;
	}

	/**
	 * @param sortedEdges
	 *            the sortedEdges to set
	 */
	public void setSortedEdges(PriorityQueue<Edge> sortedEdges) {
		this.sortedEdges = sortedEdges;
	}

	/**
	 * @return the pseudoVertex
	 */
	public PseudoVertex getPseudoVertex() {
		return pseudoVertex;
	}

	/**
	 * @param pseudoVertex
	 *            the pseudoVertex to set
	 */
	public void setPseudoVertex(PseudoVertex pseudoVertex) {
		this.pseudoVertex = pseudoVertex;
	}

	/**
	 * This method transposes both, adjecency list as well as reverseadjecency
	 * list
	 */
	public void transposeAdjecencyList() {
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
	 * @return the sortedEdges
	 */
	public Queue<Edge> getSortedRevEdges() {
		return sortedRevEdges;
	}

	/**
	 * @param sortedEdges
	 *            the sortedEdges to set
	 */
	public void setSortedRevEdges(PriorityQueue<Edge> sortedRevEdges) {
		this.sortedRevEdges = sortedRevEdges;
	}

	/**
	 * @return the componentId
	 */
	public int getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId
	 *            the componentId to set
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
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @param seen
	 *            the seen to set
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
	 * @param name
	 *            the name to set
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
	 * @return the seen
	 */
	public boolean fetchAndResetSeen() {
		boolean flag = seen;
		this.seen = false;
		return flag;
	}

	/**
	 * @param seen
	 *            the seen to set
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
	 * @param parent
	 *            the parent to set
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
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @param distance
	 *            the distance to set
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
	 * @param adj
	 *            the adj to set
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
	 * @param revAdj
	 *            the revAdj to set
	 */
	public void setRevAdj(List<Edge> revAdj) {
		this.revAdj = revAdj;
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int compareTo(Vertex o) {

		return this.distanceObj.getDistance() - o.distanceObj.getDistance();
		
		/*if (this.distanceObj != null) {
			if (!this.distanceObj.isInfinity && !o.distanceObj.isInfinity) {
				return this.distanceObj.getDistance() - o.distanceObj.getDistance();
			} else if (o.distanceObj.isInfinity) {
				return -1;
			} else if (this.distanceObj.isInfinity) {
				return 1;
			}
			else
			{
				return 0;
			}
		} else {
			return this.distance - o.distance;
		}

*/	}

	public class PseudoVertex {
		List<Edge> cycle = null;
		Queue<Edge> originalIncoming = Graph.getNewEdgeSortedPriorityQueue();
		List<Edge> originalOutgoing = new LinkedList<Edge>();
		List<Edge> cycleEdges = new ArrayList<Edge>();

		public PseudoVertex(List<Edge> cycle) {
			super();
			this.cycle = cycle;
		}

		/**
		 * @return the cycleEdges
		 */
		public List<Edge> getCycleEdges() {
			return cycleEdges;
		}

		/**
		 * @param cycleEdges
		 *            the cycleEdges to set
		 */
		public void setCycleEdges(List<Edge> cycleEdges) {
			this.cycleEdges = cycleEdges;
		}

		/**
		 * @return the originalIncoming
		 */
		public Queue<Edge> getOriginalIncoming() {
			return originalIncoming;
		}

		/**
		 * @param originalIncoming
		 *            the originalIncoming to set
		 */
		public void addOriginalIncoming(Edge originalIncoming) {
			this.originalIncoming.add(originalIncoming);
		}

		/**
		 * @return the originalOutgoing
		 */
		public List<Edge> getOriginalOutgoing() {
			return originalOutgoing;
		}

		/**
		 * @param originalOutgoing
		 *            the originalOutgoing to set
		 */
		public void addOriginalOutgoing(Edge originalOutgoing) {
			this.originalOutgoing.add(originalOutgoing);
		}

		/**
		 * @return the cycle
		 */
		public List<Edge> getCycle() {
			return cycle;
		}

	}
}
