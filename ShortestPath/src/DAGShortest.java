import java.util.List;
import java.util.Stack;

public class DAGShortest {
	Graph graph;

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

				if (v.isSeen()
						&& (edge.getWeight() + v.distanceObj.getDistance()) < minWeight) {
					
					
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
