import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to represent a EulerVertex of a graph
 */

public class EulerVertex extends Vertex {

	private EulerVertex parent; // parent of the vertex
	private Set<Integer> componentIds = new HashSet<Integer>();
	protected List<EulerEdge> Adj, revAdj;

	EulerVertex(int n) {
		super(n);
		Adj = new ArrayList<EulerEdge>();
		revAdj = new ArrayList<EulerEdge>();
	}

	/**
	 * @return the parent
	 */
	public Vertex getParent() {
		return parent;
	}

	/**
	 * @return the componentIds
	 */
	public Set<Integer> getComponentIds() {
		return componentIds;
	}

	/**
	 * @param componentIds
	 *            the componentIds to set
	 */
	public void setComponentIds(Set<Integer> componentIds) {
		this.componentIds = componentIds;
	}

	/**
	 * @param componentId
	 *            the componentId to set
	 */
	public void addComponentId(int componentId) {
		this.componentIds.add(componentId);
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(EulerVertex parent) {
		this.parent = parent;
	}

	/**
	 * @return the adj
	 */
	public List<EulerEdge> getAdj() {
		return Adj;
	}

	public boolean hasEnabledEdges() {
		for (EulerEdge eulerEdge : Adj) {
			if (!eulerEdge.isDisabled()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param adj
	 *            the adj to set
	 */
	public void setAdj(List adj) {
		Adj = adj;
	}

	/**
	 * @return the revAdj
	 */
	public List<EulerEdge> getRevAdj() {
		return revAdj;
	}


	/**
	 * @param revAdj
	 *            the revAdj to set
	 */
	public void setRevAdj(List revAdj) {
		this.revAdj = revAdj;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name+"";
	}
}