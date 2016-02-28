import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a EulerVertex of a graph
 */

public class EulerVertex  extends Vertex{

	private EulerVertex parent; // parent of the vertex
	private int componentId=0;
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


	public static <V extends Vertex> EulerVertex getInstance( int n)
	{
 		return new EulerVertex(n);
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
	 * @param parent the parent to set
	 */
	public void setParent(EulerVertex parent) {
		this.parent = parent;
	}

	/**
	 * @return the adj
	 */
	public List  getAdj() {
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
	public List  getRevAdj() {
		return revAdj;
	}



	/**
	 * @param revAdj the revAdj to set
	 */
	public void setRevAdj(List revAdj) {
		this.revAdj = revAdj;
	}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.name+"["+this.componentId+"]";
}
}
