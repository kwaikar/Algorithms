import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class EulerTrail {

	/**
	 * This method accepts a graph, extracts Euler Trail and returns the same.
	 * 
	 * @param g
	 * @return
	 */
	public static List<Vertex> findEulerTour(Graph graph) {
		List<Vertex> eulerTour = new LinkedList<Vertex>();
		Stack<Vertex> tempStack = new Stack<Vertex>();
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}

		int componentId = 0;
		EulerVertex vertex = graph.getVerts().get(1);
		if (!vertex.seen) {
			tempStack.add(vertex);
			componentId++;
			vertex.setSeen(true);
			System.out.println("Moving:" + componentId);
			EulerVertex root = vertex;
			EulerVertex current = root;
			do {
				System.out.println("current:" + current);
				current.setSeen(true);
				current.setComponentId(componentId);
				EulerEdge edge = null;
				for (EulerEdge eulerEdge : current.getAdj()) {
					if (!eulerEdge.isDisabled()) {
						edge = eulerEdge;
						break;
					}
				}
				if (edge != null) {
					System.out.println(edge);
					tempStack.add(edge.getTo());
					edge.setDisabled(true);
					current = edge.otherEnd(current);
				}
			} while (current != root);
			/**
			 * Add Root
			 */
			eulerTour.add(tempStack.pop());
		}

		return eulerTour;
	}

	public static List<EulerVertex> findEulerTourComponents(Graph graph) {

		List<EulerVertex> finalEulerTour = new LinkedList<EulerVertex>();
		for (Vertex vertex : graph) {
			vertex.setSeen(false);
		}

		int componentId = 0;
		for (EulerVertex vertex : graph) {

			if (!vertex.seen) {
				componentId++;
				vertex.setSeen(true);
				System.out.println("Moving:" + componentId);
				EulerVertex root = vertex;
				EulerVertex current = root;
				List<EulerVertex> eulerTour = new LinkedList<EulerVertex>();
				List<EulerVertex> beforeRoot = new LinkedList<EulerVertex>();
				EulerVertex currentRoot = null;
				do {
					System.out.println("current:" + current);
					current.setSeen(true);
					if (currentRoot == null && current.getComponentId() != 0) {
						beforeRoot = new LinkedList<EulerVertex>(eulerTour);
						eulerTour = new LinkedList<>();
						currentRoot = current;
						System.out.println("Found: " + beforeRoot + "  :" + eulerTour);
					} else {
						current.setComponentId(componentId);
					}
					eulerTour.add(current);
					EulerEdge edge = null;
					for (EulerEdge eulerEdge : current.getAdj()) {
						if (!eulerEdge.isDisabled()) {
							edge = eulerEdge;
							break;
						}
					}
					if (edge != null) {
						System.out.println(edge);
						edge.setDisabled(true);
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

	private static List<EulerVertex> mergeCycle(List<EulerVertex> primaryCycle, List<EulerVertex> cycleToBeMerged) {
		if (primaryCycle == null || primaryCycle.size() == 0) {
			primaryCycle = cycleToBeMerged;
		} else {
			System.out.println("Merging " + primaryCycle + " : " + cycleToBeMerged);
			Vertex root = cycleToBeMerged.get(0);
			Integer index = -1;
			for (int i = 0; i < primaryCycle.size(); i++) {
				EulerVertex vertex = primaryCycle.get(i);
				if (vertex == root) {
					index = i;
				}
			}
			primaryCycle.addAll(index, cycleToBeMerged);
			System.out.println(primaryCycle);
		}
		return primaryCycle;
	}

	public static void main(String[] args) {

		EulerTrail trail = new EulerTrail();
		Graph graph = trail.acceptGraphInput(args.length > 0 ? args[0] : null);
		Statistics stats = new Statistics();
		stats.timer();
		EulerTrail.findEulerTourComponents(graph);
		stats.timer("Eulerian Test Function");
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
