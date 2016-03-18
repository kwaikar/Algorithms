import java.util.ArrayDeque;
import java.util.Deque;
/**
 * 
 * @author M Krishna Kavya
 *
 */
public class BreadthFirstSearch {
	public Graph graph;
	public Graph result;
	public void readGraph() {
		graph = Graph.graphInput("lp3_l1_in1", true);
	}

	public void breadthFirstSearch() {
		
		Deque<Vertex> queue = new ArrayDeque<Vertex>();

		/*
		 * Initialization
		 */
		for (Vertex v : graph) {
			v.distanceObj.setDistance(0);
			v.distanceObj.setInfinity(true);
			v.seen = false;
			v.parent = null;
		}
		
		/*
		 * Add source(first) vertex to the queue.Vertex is marked seen and
		 * parent is marked to null.
		 */
		Vertex sourceVertex=graph.verts.get(1);
		queue.add(sourceVertex);
		sourceVertex.seen=true;
		sourceVertex.parent=null;
		sourceVertex.distanceObj.distance=0;
		
		/*
		 * for each vertex in the queue.
		 * parse through all the edges of the current vertex. Mark it as seen. 
		 *  
		 */
		int shortestPath=0;
		
		while(!queue.isEmpty()){
			Vertex current=queue.remove();
			for(Edge edge: current.Adj){
				Vertex v=edge.otherEnd(current);
				
				if(v.seen==false){
					v.distanceObj.distance=current.distanceObj.distance+1;
					shortestPath=shortestPath+v.distanceObj.distance;
					v.parent=current;
					v.seen=true;
					queue.add(v);
					
				}
			}
			
		}
		
		System.out.println(shortestPath);
		for(Vertex v: graph){
			System.out.println(v.name + " "+v.distanceObj.distance+" "+ v.parent);
		}
	}

	public static void main(String[] args) {

		BreadthFirstSearch obj = new BreadthFirstSearch();
		obj.readGraph();
		obj.breadthFirstSearch();
	}
}
