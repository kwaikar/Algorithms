import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EulerTrail {

	/**
	 * This method accepts a graph, extracts Euler Trail and returns the same.
	 * 
	 * @param g
	 * @return
	 */

	public static List<EulerEdge> findEulerTourComponents(Graph<EulerVertex,EulerEdge> graph) {

		List<EulerEdge> finalEulerTour = new LinkedList<EulerEdge>();
		/**
		 * Initialize
		 */
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}

		/**
		 * Invariants: componentId : Used for identifying different cycles that
		 * exist in the graph. vertex : Loop Invariant for looping through all
		 * vertices root : Root of the intermediary cycle.
		 */
		int componentId = 0;
		for (EulerVertex vertex : graph) {

			if (!vertex.seen) {
				componentId++;
				vertex.setSeen(true);
				EulerVertex root = vertex;
				EulerVertex current = root;
				List<EulerEdge> eulerTour = new LinkedList<EulerEdge>();
				List<EulerEdge> beforeRoot = new LinkedList<EulerEdge>();
				EulerVertex currentRoot = null;
				do {
					current.setSeen(true);
					if (currentRoot == null && current.getComponentId() != 0) {
						beforeRoot = new LinkedList<EulerEdge>(eulerTour);
						eulerTour = new LinkedList<>();
						currentRoot = current;
					} else {
						current.setComponentId(componentId);
					}
					EulerEdge edge = null;
					for (Object edge1 : current.getAdj()) {
						EulerEdge eulerEdge=(EulerEdge) edge1;
						if (!eulerEdge.isDisabled()) {
							edge = eulerEdge;
							break;
						}
					}
					if (edge != null) {
					//	System.out.println("adding:" + edge);
						edge.setDisabled(true);

						eulerTour.add(edge.getFrom() == current ? edge : edge.reverse());
						current = edge.otherEnd(current);
					}
				} while (current != root);
				/**
				 * The block would complete only if the cycle is complete as per
				 * the tempRoot Initially chosen. hence we can assume that there
				 * exists an edge between end of vertex at the final edge of
				 * tour to start of beforeRoot.
				 */
				eulerTour.addAll(beforeRoot);
				// eulerTour has 1 cycle;
				/**
				 * Merge cycle just found with the final cycle.
				 */
				finalEulerTour = mergeCycle(finalEulerTour, eulerTour);
				// @TODO : Merge all cycles into final cycle.
			}
		}
		System.out.println(finalEulerTour);
		return finalEulerTour;
	}
	
	private static List<EulerEdge> mergeCycle(List<EulerEdge> primaryCycle, List<EulerEdge> cycleToBeMerged) {
		if (primaryCycle == null || primaryCycle.size() == 0) {
			primaryCycle = cycleToBeMerged;
		} else {
		//	System.out.println("Merging " + primaryCycle + " : " + cycleToBeMerged);
			EulerEdge root = cycleToBeMerged.get(0);
			Integer index = -1;
			for (int i = 0; i < primaryCycle.size(); i++) {
				EulerEdge vertex = primaryCycle.get(i);
				if (root.getFrom() == vertex.getFrom()) {
					index = i;
				} else if (root.getFrom() == vertex.getTo()) {
					index = i + 1;
				}
			}
			primaryCycle.addAll(index, cycleToBeMerged);
		//	System.out.println(primaryCycle);
		}
		return primaryCycle;
	}

	public static void main(String[] args) {

		EulerTrail trail = new EulerTrail();
		Graph<EulerVertex,EulerEdge> graph = trail.acceptGraphInput(args.length > 0 ? args[0] : null);
		Statistics stats = new Statistics();
		stats.timer();
		EulerTrail.findEulerTourComponents(graph);
		stats.timer("Eulerian Test Function");
	}

	public Graph<EulerVertex,EulerEdge> acceptGraphInput(String inputFilePath) {
		Graph<EulerVertex,EulerEdge> graph = null;
		try {
			Scanner sc = null;
			File inputFile = inputFilePath != null ? new File(inputFilePath) : null;
			if (inputFile != null && inputFile.exists()) {
				sc = new Scanner(inputFile);
			} else {
				System.out.println(
						"Please enter dimensions of the graph (#nodes, #edges) followed by edges in format (left,right,weight)");
				sc = new Scanner(System.in);
			}
			graph = Graph.readGraph(sc, false);

		} catch (FileNotFoundException e) {
			System.out.println("Exception occured while Reading input file");
			e.printStackTrace();
		}
		return graph;
	}

}
