import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * This class Implements Edmonds algorithm for finding MST in a directed Graph.
 * @author Kanchan Waikar 
 * Date Created : Mar 12, 2016 - 8:49:52 PM
 */
public class EdmondsAlgorithm {

	/**
	 * Driver Program for Edmunds Algorithm.
	 * 
	 * @param args - Arguments
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Statistics stats = new Statistics();
		stats.timer();
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, GraphType.DIRECTED_EDGE_SORTED);
		stats.timer("=> input graph read statistics");
		stats.timer();
		Pair<List<Edge>, Long> spanningTreeDetails = minimumSpanningTree(graph, null);
		stats.timer("=> Edmonds : Minimum Spanning Tree retrieval");
		System.out.println("Total Weight of the MST was found to be : " + spanningTreeDetails.getSecond());

		/**
		 * Sort Edges by "To" and print the same, if number of edges are less
		 * than or equal to 50
		 */
		if (spanningTreeDetails.getFirst().size() <= 50) {
			List<Edge> finalEdges = spanningTreeDetails.getFirst();
			Collections.sort(finalEdges, new Comparator<Edge>() {
				@Override
				public int compare(Edge o1, Edge o2) {
					return o1.getTo().getName() - (o2.getTo().getName());
				}
			});
			/**
			 * Print Edges in sorted order.
			 */
			for (Edge edge : finalEdges) {
				System.out.println(edge);
			}
		}
	}

	/**
	 * This method returns minimum spanning tree found using Kruskals Algorithm
	 * on the given directed graph. Please note that this graph maintains a
	 * reverseAdjescencyList using a priorityQueue data structure
	 * 
	 * It is given that vertex 1 is the root vertex.
	 * 
	 * @param directedEdgeSortedGraph
	 *            - Incoming Graph on which Kruskals Algorithm needs to be run
	 *            for finding MST.
	 * @return
	 */
	public static Pair<List<Edge>, Long> minimumSpanningTree(Graph directedGraph,
			Pair<List<Edge>, Long> previousOutput) {
		List<Edge> edges = new ArrayList<Edge>();
		Long wMST = 0L;
		if (previousOutput != null) {
			edges = previousOutput.getFirst();
			wMST = wMST + previousOutput.getSecond();
		}
		int intialGraphSize = directedGraph.getVerts().size();
		/**
		 * Step 1 - Transformation of weights such that all except root have a
		 * zero weight incoming edge.
		 */
		if (directedGraph.getVerts().size() > 2) {
			for (int i = 2; i < (intialGraphSize); i++) {
				Vertex currentVertex = directedGraph.getVerts().get(i);
				if (currentVertex.isEnabled()) {
					Edge minEdge = currentVertex.getSortedRevEdges().peek();
					int deltaU = minEdge.getWeight();
					if (deltaU > 0) {
						for (Edge edge : currentVertex.getSortedRevEdges()) {
							edge.reduceWeight(deltaU);
						}
						wMST += deltaU;
						if (minEdge.getTo().getPseudoVertex() == null) {
							/**
							 * PseudoVertex incoming edge is already populated
							 * in the edges list.
							 */
							edges.add(minEdge);
						}
					}
				}
			}
		} else {
			System.out.println(new Pair<List<Edge>, Long>(new ArrayList<Edge>(), 0L));
		}

		/**
		 * Step 2 - Start visiting all nodes from Root and find the ones that
		 * cannot be visited.
		 */
		Vertex root = directedGraph.getVerts().get(1);
		traverse0WeightGraph(root);
		/**
		 * Now all the Vertices have "seen" flag set. Loop through the same to
		 * find out which Vertices were not visited through zero length cycle.
		 * Gives Preference to Last unreachable node - no specific intention
		 * behind it except to reduce LOC.
		 */
		Vertex unreachableNodeZ = null;
		for (Vertex vertex : directedGraph) {
			if (vertex.isEnabled()) {
				if (!vertex.fetchAndResetSeen()) {
					unreachableNodeZ = vertex;
				}
			}
		}
		if (unreachableNodeZ == null) {
			/**
			 * Graph found is an MST. Expand Edges, return path - wMST does not
			 * change since expanded edges are indeed zero length cycles.
			 */
			List<Edge> allEdges = getMSTOfZeroTree(directedGraph, edges);
			return new Pair<List<Edge>, Long>(allEdges, wMST);
		} else {
			/**
			 * Walk backward from unReachableNodeZ and try to find the cycle.
			 */
			Pair<Vertex, Stack<Edge>> output = walkBackwardsFromZAndReturnRepeatingVertexAlongWithPath(
					unreachableNodeZ);
			Vertex repeatingVertex = output.getFirst();
			Stack<Edge> backwardsWalkStack = output.getSecond();
			/**
			 * u now points to the x Vertex that got repeated on the walk! We
			 * need to now back
			 */
			List<Edge> cycle = new LinkedList<>();
			Vertex previousTo = repeatingVertex;
			Edge revEdge = backwardsWalkStack.pop();
			do {
				while (revEdge.getFrom() != previousTo) {
					revEdge = backwardsWalkStack.pop();
				}
				/**
				 * Since stack contains reverse Edges, we need to re-reverse the
				 * edge to get correct one.
				 */
				cycle.add(Edge.reverseEdge(revEdge));
				revEdge.getFrom().setSeen(false);
				revEdge.getFrom().setPartOfCycle(true);
				previousTo = revEdge.getTo();

			} while (!previousTo.equals(repeatingVertex));

			/**
			 * Now that the cycle has been found, we shrink the cycle into a
			 * single node.
			 */
			Vertex shrunkenVertex = new Vertex((intialGraphSize), cycle);
			directedGraph.getVerts().add((intialGraphSize), shrunkenVertex);

			for (Edge cycleEntry : cycle) {
				Vertex vertex = cycleEntry.getFrom();
				vertex.setEnabled(false);

				/**
				 * Find out all optimal outgoing edges from the cycle.
				 */

				if (vertex.getSortedEdges() != null && vertex.getSortedEdges().size() != 0) {

					for (Edge edge : vertex.getSortedEdges()) {
						Vertex outsideVertex = edge.getTo();

						if (!outsideVertex.isOutgoingSeen() && !outsideVertex.isPartOfCycle()
								&& outsideVertex.getSortedRevEdges().size() != 0) {
							/**
							 * "To" Vertex is not seen yet and is also not part
							 * of the cycle. Find the minimum incoming for the
							 * same from the cycle, and assign create a new
							 * edge.
							 */
							/**
							 * Mark it seen
							 */
							outsideVertex.setOutgoingSeen(true);
							Collection<Edge> queueOfEdgeRemoved = new ArrayList<>();
							Edge minimumEdgeFromCycle = null;
							/**
							 * Loop through reverse adjacency Priority Queue of
							 * the Vertex, until you find the node that is part
							 * of the cycle. This edge points to the
							 * minimum-incoming edge from the cycle - Use this
							 * for creating the outgoing edge from the cycle..
							 */
							Edge finalMinEdge = null;
							while (!outsideVertex.getSortedRevEdges().isEmpty()) {
								minimumEdgeFromCycle = outsideVertex.getSortedRevEdges().remove();
								if (minimumEdgeFromCycle.getFrom().isPartOfCycle()) {
									if (finalMinEdge == null) {
										finalMinEdge = minimumEdgeFromCycle;
									}
								} else {
									queueOfEdgeRemoved.add(minimumEdgeFromCycle);
								}
							}
							/**
							 * Reinstate the queue.
							 */
							outsideVertex.getSortedRevEdges().addAll(queueOfEdgeRemoved);
							/**
							 * Add the minimum edge to the adjacency list.
							 */
							if (finalMinEdge != null) {
								Edge minOutgoingEdgeFromCycleToOutsideVertex = new Edge(shrunkenVertex,
										finalMinEdge.getTo(), finalMinEdge.getWeight());
								shrunkenVertex.getSortedEdges().add(minOutgoingEdgeFromCycleToOutsideVertex);
								// System.out.println("Outgoing edge created : "
								// + minOutgoingEdgeFromCycleToOutsideVertex);
								finalMinEdge.getTo().getSortedRevEdges().add(minOutgoingEdgeFromCycleToOutsideVertex);
								// System.out.println("Original=>" +
								// finalMinEdge);
								shrunkenVertex.getPseudoVertex().addOriginalOutgoing(finalMinEdge);
							}
						}
					}
					for (Edge edge : vertex.getSortedEdges()) {
						edge.getTo().setOutgoingSeen(false);
					}
				}
			}

			/**
			 * Find out and create optimal incoming edges to the cycle
			 */

			List<Vertex> outsideVertexes = new ArrayList<>();
			for (Edge cycleEntry : cycle) {
				Vertex vertex = cycleEntry.getFrom();

				if (vertex.getSortedRevEdges() != null && vertex.getSortedRevEdges().size() != 0) {
					for (Edge edge : vertex.getSortedRevEdges()) {
						Vertex outsideVertex = edge.getFrom();

						if (!outsideVertex.isIncomingSeen() && !outsideVertex.isPartOfCycle()
								&& outsideVertex.getSortedEdges().size() != 0) {
							/**
							 * "From" Vertex is not seen yet and is also not
							 * part of the cycle. Find the minimum outgoing edge
							 * into the cycle.
							 */
							/**
							 * Mark it seen
							 */
							outsideVertex.setIncomingSeen(true);
							outsideVertexes.add(outsideVertex);
						}
					}
					/**
					 * Prep for next cycle calculation
					 */
					for (Edge edge : vertex.getSortedRevEdges()) {
						edge.getFrom().setIncomingSeen(false);

					}
				}
			}
			for (Vertex outsideVertex : outsideVertexes) {
				Collection<Edge> queueOfEdgeRemoved = new ArrayList<>();
				Edge minimumEdgeToCycle = null;
				/**
				 * Loop through adjacency Priority Queue of the Vertex, until
				 * you find the node that is part of the cycle. This edge points
				 * to the minimum-incoming edge to the cycle - Use this for
				 * creating the incoming edge to the cycle..
				 */
				Edge finalMinEdge = null;

				int weight = Integer.MAX_VALUE;
				while (!outsideVertex.getSortedEdges().isEmpty()) {
					minimumEdgeToCycle = outsideVertex.getSortedEdges().poll();
					if (minimumEdgeToCycle.getTo().isPartOfCycle()) {
						if (weight > minimumEdgeToCycle.getWeight()) {
							finalMinEdge = minimumEdgeToCycle;
						}
					} else {
						queueOfEdgeRemoved.add(minimumEdgeToCycle);
					}
				}
				if (finalMinEdge != null) {
					shrunkenVertex.getPseudoVertex().addOriginalIncoming(finalMinEdge);
					Edge minIncomingEdgeToCycleFromOutsideVertex = new Edge(finalMinEdge.getFrom(), shrunkenVertex,
							finalMinEdge.getWeight());
					shrunkenVertex.getSortedRevEdges().add(minIncomingEdgeToCycleFromOutsideVertex);
					finalMinEdge.getFrom().getSortedEdges().add(minIncomingEdgeToCycleFromOutsideVertex);
				}
				/**
				 * Reinstate the queue.
				 */
				outsideVertex.getSortedEdges().addAll(queueOfEdgeRemoved);
			}

			List<Edge> cycleEdges = new ArrayList<Edge>();
			/**
			 * Add least incoming edge to the Cycle to edges list.
			 */
			Edge exactlyOneIncomingEdgeIntoMST = shrunkenVertex.getPseudoVertex().getOriginalIncoming().remove();
			cycleEdges.add(exactlyOneIncomingEdgeIntoMST);
			/**
			 * Add complete cycle to the MST - except the incoming edge. This
			 * will be traced as it is.
			 */
			for (Edge edge : cycle) {
				if (!edge.getTo().equals(exactlyOneIncomingEdgeIntoMST.getTo())) {
					cycleEdges.add(edge);
				}
			}
			cycleEdges.addAll(shrunkenVertex.getPseudoVertex().getOriginalOutgoing());
			shrunkenVertex.getPseudoVertex().setCycleEdges(cycleEdges);

			/**
			 * Remove all the cycles from both the nodes. Cycles have already
			 * been associated with pseudonode.
			 */
			for (Edge edge : cycle) {
				edge.getFrom().setSortedEdges(Graph.getNewEdgeSortedPriorityQueue());
				edge.getTo().setSortedRevEdges(Graph.getNewEdgeSortedPriorityQueue());

			}
		}
		/**
		 * Recursive call until we get WMST.
		 */
		return minimumSpanningTree(directedGraph, new Pair<List<Edge>, Long>(edges, wMST));

	}

	/**
	 * This method accepts Zero Tree Directed Graph and Edges List contains all minimal edges that contributed towards incoming Minimum Delta for the node.
	 * @param directedGraph - Directed Graph
	 * @param edges - Minimal incoming edges.
	 * @return
	 */
	private static List<Edge> getMSTOfZeroTree(Graph directedGraph, List<Edge> edges) {
		List<Edge> allEdges = new ArrayList<>();

		for (Vertex vertex : directedGraph) {
			if (vertex.getPseudoVertex() != null) {
				allEdges.addAll(vertex.getPseudoVertex().getCycleEdges());
			}
		}
		for (Edge edge : edges) {
			if (!edge.getTo().isPartOfCycle() && !edge.getFrom().isPartOfCycle()) {
				allEdges.add(edge);
			}
		}
		return allEdges;
	}

	/**
	 * This method traverses through zero weight graph and starts from given
	 * root. Sets seen flag on the vertices visited.
	 * 
	 * @param root
	 */
	private static void traverse0WeightGraph(Vertex root) {
		Queue<Vertex> reachableVertices = new ArrayDeque<Vertex>();
		reachableVertices.add(root);
		/**
		 * Invaritants:
		 * 
		 * reachableVertices - Queue for traversal 
		 * u - current Vertex
		 */
		while (!reachableVertices.isEmpty()) {
			Vertex u = reachableVertices.remove();
			if (!u.isSeen()) {
				u.setSeen(true);
				for (Edge edge : u.getSortedEdges()) {
					/**
					 * add only edges to the queue that are at zero weight
					 * length.
					 */
					if (edge.getWeight() == 0 && !edge.getTo().isSeen()) {
						reachableVertices.add(edge.getTo());
					}
				}
			}
			u = null;
		}
		reachableVertices = null;
	}

	/**
	 * This method starts from unreachable Node "z" and starts walking backward
	 * until it finds and returns the stack.
	 * 
	 * @param unreachableNodeZ
	 *            - Unreachable node Z
	 * @return
	 */
	private static Pair<Vertex, Stack<Edge>> walkBackwardsFromZAndReturnRepeatingVertexAlongWithPath(
			Vertex unreachableNodeZ) {
		Vertex u = null;
		/**
		 * Loop Invariants: u - Ultimately contains the Repeating Vertex
		 * reachableVertices - Queue for Traversal. backwardsWalkStack - stack
		 * that contains the backward walk - the same needs to be forward
		 * "walked" to get the complete cycle that contains "u"
		 */
		Stack<Edge> backwardsWalkStack = new Stack<Edge>();
		Queue<Vertex> reachableVertices = new ArrayDeque<Vertex>();
		reachableVertices.add(unreachableNodeZ);
		u = reachableVertices.remove();
		do {
			u.setSeen(true);
			for (Edge edge : u.getSortedRevEdges()) {
				/**
				 * add only edges to the queue that are at zero weight length.
				 */
				if (edge.getWeight() == 0 && edge.getFrom().isEnabled()) {
					reachableVertices.add(edge.getFrom());
					backwardsWalkStack.push(edge);
					break;
				}
			}

			u = reachableVertices.remove();
		} while (!u.isSeen());
		reachableVertices = null;
		return new Pair<Vertex, Stack<Edge>>(u, backwardsWalkStack);
	}
}
