import java.util.List;
import java.util.Stack;

/**
 * 
 * We initialize distances to all vertices as infinite and distance to source as
 * 0, then we find a topological sorting of the graph. Topological Sorting of a
 * graph represents a linear ordering of the graph (See below, figure (b) is a
 * linear representation of figure (a) ). Once we have topological order (or
 * linear representation), we one by one process all vertices in topological
 * order. For every vertex being processed, we update distances of its adjacent
 * using distance of current vertex.
 * 
 * @author M Krishna Kavya
 * 
 */
public class DAGShortest {
	Graph graph;

	/*
	 * Following is complete algorithm for finding shortest distances. 
	 * 1) Initialize dist[] = {INF, INF, ….} and dist[s] = 0 where s is 
	 * the source vertex. 
	 * 2) Create a toplogical order of all vertices. 
	 * 3) Do following for every vertex u in topological order.
	 * 4) Do following for every adjacent vertex v of u.
	 *    if (distance[v] > distance[u] + weight(u, v))
	 *        distance[v] = distance[u] + weight(u, v)
	 */
	public List<Edge> shortestPath() {
		Stack<Vertex> topOrderedGraphStack = TopologicalOrdering
				.topologicalOrderUsingDFS(graph);
		
		graph.initialize(graph);

		Vertex sourceVertex = graph.getVerts().get(1);
		sourceVertex.distanceObj.setDistance(0);
		sourceVertex.distanceObj.setInfinity(false);
		sourceVertex.setSeen();
		System.out.println(topOrderedGraphStack);

		while (!topOrderedGraphStack.isEmpty()) {
			Vertex u = topOrderedGraphStack.pop();
			int minWeight = Integer.MAX_VALUE;

			for (Edge edge : u.getRevAdj()) {
				Vertex v = edge.otherEnd(u);

				if (v.isSeen()	&& (edge.getWeight() + v.distanceObj.getDistance()) < minWeight) {

					minWeight = edge.getWeight() + v.distanceObj.getDistance();
					u.distanceObj.setDistance(minWeight);
					u.setSeen();
					u.distanceObj.setInfinity(false);
					u.setParent(v);
					u.setParentEdge(edge.reverseEdge(edge));
				}

			}

		}

		return graph.getParentEdgesAndPrintPath("DAG");
	}

	public static void main(String args[]) {

		DAGShortest obj = new DAGShortest();
		obj.graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		obj.shortestPath();
	}

}
