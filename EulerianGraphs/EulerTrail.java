import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This Class returns the euler tour from the given graph.
 * 
 * @author Kanchan Waikar Date Created : Feb 16, 2016 - 8:05:19 PM
 *
 */
public class EulerTrail {

	/**
	 * This method returns the euler tour from the graph. Assumption is that
	 * input graph is eulerian.
	 * 
	 * @param graph
	 * @return
	 */
	public static List<EulerEdge> findEulerTourComponents(Graph graph) {

		List<List<EulerEdge>> eulerCycles = new LinkedList<List<EulerEdge>>();
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}

		for (EulerVertex vertex : graph) {
			/**
			 * Loop until there are vertices with enabled edges.
			 */
			if (vertex.hasEnabledEdges()) {
				vertex.setSeen(true);
				/**
				 * Find the cycle that includes current component.
				 */
				EulerVertex root = vertex;
				EulerVertex current = root;
				EulerVertex previousVertex = root;
				List<EulerEdge> eulerTour = new LinkedList<EulerEdge>();
				List<EulerEdge> beforeRoot = new LinkedList<EulerEdge>();
				EulerVertex currentRoot = null;
				/**
				 * Loop until the cycle is found. Abends suggest non-eulerian
				 * graph input.
				 */
				do {
					current.setSeen(true);
					if (currentRoot == null && current.getComponentIds().size() != 0) {
						beforeRoot = new LinkedList<EulerEdge>(eulerTour);
						eulerTour = new LinkedList<>();
						currentRoot = current;
					}
					EulerEdge edge = null;
					previousVertex = current;
					for (EulerEdge eulerEdge : current.getAdj()) {
						if (!eulerEdge.isDisabled()) {
							/**
							 * Find the edge that is not disabled, add the same
							 * to the localTour.
							 */
							edge = eulerEdge;
							edge.setDisabled(true);
							EulerVertex start= current;
							current = edge.otherEnd(current);
							EulerVertex end = current;
							edge.setFrom(start);
							edge.setTo(end);
							eulerTour.add(edge);
							break;
						}
					}
					if (previousVertex == current) {
						System.out.println(" graph is not eulerian : No edge from " + current);
						System.exit(0);
					}

				} while (current != root);
				/**
				 * The block would complete only if the cycle is complete as per
				 * the tempRoot Initially chosen. hence we can assume that there
				 * exists an edge between end of vertex at the final edge of
				 * tour to start of beforeRoot.
				 */
				eulerTour.addAll(beforeRoot);
				eulerCycles.add(eulerTour);
			}
		}
		/**
		 * Merge all individual euler cycles and return the final tour.
		 */
		return mergeCycle(eulerCycles);
	}

	/**
	 * This method verifies the validity of the tour by removing one edge at a
	 * time. Tour's first edge is supposed to start from the starting Point,
	 * hence third parameter is left unused.
	 * 
	 * @param graph
	 * @param tour
	 * @param start
	 * @return
	 */
	public boolean verifyTour(Graph graph, List<EulerEdge> tour, EulerVertex start) {

		/**
		 * Go through each of the tour and and mark edges found in the tour
		 * disabled.
		 */
		for (int i = 0; i < tour.size() - 1; i++) {
			EulerEdge edge = tour.get(i);
			EulerVertex fromVertex = graph.getVerts().get(edge.getFrom().getName());
			for (EulerEdge eulerEdge : fromVertex.getAdj()) {
				if (eulerEdge.getTo().equals(edge.getTo())) {
					eulerEdge.setDisabled(true);
					break;
				}
			}
		}
		/**
		 * Check if there are any edges that are not in the tour and output the
		 * same.
		 */
		for (EulerVertex vertex : graph) {
			for (EulerEdge eulerEdge : vertex.getAdj()) {
				if (!eulerEdge.isDisabled()) {
					System.out.println("edge : " + eulerEdge + " Not covered in the tour");
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * This method merges all cycles and returns the final EulerTour.
	 * 
	 * @param cyclesToBeMerged
	 * @return
	 */
	private static List<EulerEdge> mergeCycle(List<List<EulerEdge>> cyclesToBeMerged) {
		if (cyclesToBeMerged.size() != 0) {
			List<EulerEdge> primaryCycle = cyclesToBeMerged.get(0);

			for (int j = 1; j < cyclesToBeMerged.size(); j++) {
				/**
				 * Invariants:
				 * 
				 * root - root of the current cycle index - index in
				 * the primary cycle where singleCycle needs to be appended
				 * vertex : temporary variable to be used for iteration.
				 * primaryCycle : The primary cycle in which we append rest of
				 * the cycles.
				 */
				List<EulerEdge> singleCycle = cyclesToBeMerged.get(j);
				Vertex root = singleCycle.get(0).getFrom();
				Integer index = -1;
				boolean placeFoundInPrimaryCycle=false;
				for (int i = 0; i < primaryCycle.size(); i++) {
					EulerEdge vertex = primaryCycle.get(i);
					/**
					 * if from equals root, then add the cycle right here, if it equals "To" add cycle there.
					 */
					 
					if (vertex.getFrom() == root || vertex.getTo() == root) {
						if(i==0)
						{
							index= primaryCycle.size()-1;
						}
						else
						{
							index=primaryCycle.get(i-1).getFrom().equals(vertex.getFrom())?i:i+1;
						}
						primaryCycle.addAll(index, singleCycle);
						placeFoundInPrimaryCycle=true;
						break;
					}
				}
				// It was not able to append itself, put it back in the list.
				if(!placeFoundInPrimaryCycle)
				{
				cyclesToBeMerged.add(singleCycle);
				}
			}
			/**
			 * All cycles are merged in primaryCycle.
			 */
			return primaryCycle;
		}
		return new LinkedList<EulerEdge>();
	}

	public static void main(String[] args) {

		EulerTrail trail = new EulerTrail();
		Graph graph = trail.acceptGraphInput(args.length > 0 ? args[0] : null);
		Statistics stats = new Statistics();
		stats.timer();
		List<EulerEdge> tour = EulerTrail.findEulerTourComponents(graph);
		stats.timer("Find Euler Tour Statistics");
		System.out.println("Euler Tour found : " + tour.size() );
		for (EulerEdge eulerEdge : tour) {
			System.out.println(eulerEdge);
		}
		stats.timer();
		System.out.println("Tour Verification result : " + trail.verifyTour(graph, tour, tour.get(0).getFrom()));

		stats.timer("Verify Euler Tour statistics");
	}

	public Graph acceptGraphInput(String inputFilePath) {
		Graph graph = null;
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