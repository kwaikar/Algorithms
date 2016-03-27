
public class ShortestPathsDriver {

	public static void main(String[] args) {

		Graph graph = Graph.acceptGraphInput(args[0], GraphType.DIRECTED);
		Vertex root = graph.getVerts().get(0);
		int weight=graph.getVerts().get(0).getAdj().get(0).getWeight();
		boolean hasUniformEdges =false;
		for (Vertex vertex : graph) {
			
			for (Edge edge: vertex.getAdj()) {
				 
				
			}
		}
		
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}
	}
}
