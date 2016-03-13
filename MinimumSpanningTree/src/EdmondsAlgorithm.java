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
 * 
 * @author Kanchan Waikar Date Created : Mar 12, 2016 - 8:49:52 PM
 *
 */
public class EdmondsAlgorithm {

	/**
	 * Driver Program for Edmunds Algorithm.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Statistics stats = new Statistics();
		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, GraphType.DIRECTED_EDGE_SORTED);
		stats.timer("Read input graph");
		stats.timer();
		Pair<List<Edge>, Long> spanningTreeDetails = minimumSpanningTree(graph, null);
		stats.timer("Edmonds : Minimum Spanning Tree retrieval Statistics");
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
			for (int i = 2; i < (intialGraphSize ); i++) {
				Vertex currentVertex = directedGraph.getVerts().get(i);
				if (currentVertex.isEnabled()) {
					// System.out.println("CurrentVertex: " + currentVertex+ " :
					// "+currentVertex.getSortedRevEdges()); 
					Edge minEdge = currentVertex.getSortedRevEdges().peek();
					int deltaU = minEdge.getWeight();
					if (deltaU > 0) {
						for (Edge edge : currentVertex.getSortedRevEdges()) {
							edge.reduceWeight(deltaU);
						}
						wMST += deltaU;
						edges.add(minEdge);
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
			for (Vertex vertex : directedGraph) {
				if (vertex.isSeen()) {
					throw new NullPointerException();
				}
			}
			/**
			 * Graph found is an MST. Expand Edges, return path - wMST does not change since expanded edges are indeed zero length cycles.
			 */
			List<Edge> pathTraversed = expandEdges(edges);
			return new Pair<List<Edge>, Long>(pathTraversed, wMST);
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

			} while (/* !backwardsWalkStack.isEmpty() || */ !previousTo.equals(repeatingVertex));
			System.out.println("cycle extracted: " + cycle + " : " + cycle.size());

			/**
			 * Now that the cycle has been found, we shrink the cycle into a
			 * single node.
			 */
			Vertex shrunkenVertex = new Vertex((intialGraphSize + 1), cycle);
			directedGraph.getVerts().add((intialGraphSize ), shrunkenVertex);
		//	System.out.println("Vertex created" + shrunkenVertex);

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
						//		System.out.println("Outgoing edge created : " + minOutgoingEdgeFromCycleToOutsideVertex);
								finalMinEdge.getTo().getSortedRevEdges().add(minOutgoingEdgeFromCycleToOutsideVertex);
					//			System.out.println("Original=>" + finalMinEdge);
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
			Queue<Edge> sortedIncomingEdges = Graph.getNewEdgeSortedPriorityQueue();
			/*if (shrunkenVertex.getName() == 54) {
				System.out.println("reached 54");
			}*/
			for (Edge cycleEntry : cycle) {
				Vertex vertex = cycleEntry.getFrom();
				//System.out.println("finding incomng edges for : " + vertex);

				if (vertex.getSortedRevEdges() != null && vertex.getSortedRevEdges().size() != 0) {
					for (Edge edge : vertex.getSortedRevEdges()) {
						Vertex outsideVertex = edge.getFrom();

						if (!outsideVertex.isIncomingSeen() && !outsideVertex.isPartOfCycle()
								&& outsideVertex.getSortedEdges().size() != 0) {
						//	System.out.println("outside vertex found :" + outsideVertex);
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
				}

				/*
				 * for (Edge edge : vertex.getSortedRevEdges()) {
				 * edge.getFrom().setIncomingSeen(false); }
				 */
			}
			// System.out.println("outsideVertexes=>"+outsideVertexes);
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
					// System.out.println("==>" + minimumEdgeToCycle);
					if (minimumEdgeToCycle.getTo().isPartOfCycle()) {
						if (weight > minimumEdgeToCycle.getWeight()) {
							finalMinEdge = minimumEdgeToCycle;
						}
					} else {
						queueOfEdgeRemoved.add(minimumEdgeToCycle);
					}
				}
				if (finalMinEdge != null) {
			//		System.out.println("Minimum Edge to the cycle : " + finalMinEdge);
					sortedIncomingEdges.add(finalMinEdge);
				}
				/**
				 * Reinstate the queue.
				 */
				outsideVertex.getSortedEdges().addAll(queueOfEdgeRemoved);
			}
			/**
			 * Add the minimum edge to the adjacency list.
			 */
			// System.out.println("=>" + sortedIncomingEdges);
			if (!sortedIncomingEdges.isEmpty()) {
				/**
				 * If its empty it means that the incoming edge
				 */
				Edge minimumEdgeToCycle = sortedIncomingEdges.remove();
			//	System.out.println("::::::" + sortedIncomingEdges + " =>" + minimumEdgeToCycle);
				shrunkenVertex.getPseudoVertex().setOriginalIncoming(minimumEdgeToCycle);
				Edge minIncomingEdgeToCycleFromOutsideVertex = new Edge(minimumEdgeToCycle.getFrom(), shrunkenVertex,
						minimumEdgeToCycle.getWeight());
			//	System.out.println("Incoming edge created : " + minIncomingEdgeToCycleFromOutsideVertex);
				shrunkenVertex.getSortedRevEdges().add(minIncomingEdgeToCycleFromOutsideVertex);
				minimumEdgeToCycle.getFrom().getSortedEdges().add(minIncomingEdgeToCycleFromOutsideVertex);
			}
			/**
			 * Remove all the cycles from both the nodes. Cycles have already
			 * been associated with pseudonode.
			 */
			for (Edge edge : cycle)

			{
				Collection<Edge> edgesTobeReplacedInQueue = new ArrayList<>();
				Edge edgeFromCycle = !edge.getFrom().getSortedEdges().isEmpty()
						? edge.getFrom().getSortedEdges().remove() : null;
				// Remove all outgoing edges
				while (!edge.getFrom().getSortedEdges().isEmpty() && edge.getWeight() == 0) {
					/*
					 * if (!edgeFromCycle.getTo().isPartOfCycle()) {
					 * edgesTobeReplacedInQueue.add(edgeFromCycle); }
					 */
					edgeFromCycle = edge.getFrom().getSortedEdges().remove();
				}
				// edge.getFrom().getSortedEdges().addAll(edgesTobeReplacedInQueue);
				edgesTobeReplacedInQueue = new ArrayList<>();

				while (!edge.getTo().getSortedRevEdges().isEmpty() && edge.getWeight() == 0) {
					/*
					 * if (!edgeFromCycle.getFrom().isPartOfCycle()) {
					 * edgesTobeReplacedInQueue.add(edgeFromCycle); }
					 */
					edgeFromCycle = edge.getTo().getSortedRevEdges().remove();
				}
				// edge.getTo().getSortedRevEdges().addAll(edgesTobeReplacedInQueue);

			}
		}
		// System.out.println();
		/**
		 * Recursive call until we get WMST.
		 */
		for (

		Vertex vertex : directedGraph)

		{
		/*	System.out.println(vertex.getName() + " : " + vertex.isEnabled() + "=" + vertex.getSortedEdges() + " R{"
					+ vertex.getSortedRevEdges() + "}");
		*/}
		return

		minimumSpanningTree(directedGraph, new Pair<List<Edge>, Long>(edges, wMST));

		// vertex.setPartOfCycle(false);
	}

	/**
	 * This method expands pseudoVertices from the Edges provided and returns
	 * complete MST.
	 * 
	 * @param edges
	 * @return
	 */
	public static List<Edge> expandEdges(List<Edge> edges) {
		/**
		 * MST found : Expand all edges
		 */
		List<Edge> pathTraversed = new LinkedList<>();
		System.out.println(edges);
		for (Edge edge : edges) {
			if (!(!edge.getFrom().isEnabled() && !edge.getTo().isEnabled())) {
				if (edge.getFrom().getPseudoVertex() != null && edge.getTo().getPseudoVertex() != null) {
					addPathAndmarkSeen(pathTraversed, edge.getTo());
					addPathAndmarkSeen(pathTraversed, edge.getFrom());
				} else if (edge.getFrom().getPseudoVertex() != null && edge.getFrom().isSeen()) {
					addPathAndmarkSeen(pathTraversed, edge.getFrom());
				} else if (edge.getTo().getPseudoVertex() != null) {
					addPathAndmarkSeen(pathTraversed, edge.getTo());
				} else if (edge.getTo().isEnabled() && edge.getFrom().isEnabled()) {
					pathTraversed.add(edge);
				}
			}
		}
		return pathTraversed;
	}

	/**
	 * @param pathTraversed
	 * @param pseudoVertex
	 */
	public static void addPathAndmarkSeen(List<Edge> pathTraversed, Vertex pseudoVertex) {
		pathTraversed.add(pseudoVertex.getPseudoVertex().getOriginalIncoming());
		pathTraversed.addAll(pseudoVertex.getPseudoVertex().getOriginalOutgoing());
		for (int i = 0; i < pseudoVertex.getPseudoVertex().getCycle().size() - 1; i++) {
			Edge edgeToBeAdded = pseudoVertex.getPseudoVertex().getCycle().get(i);
			pathTraversed.add(edgeToBeAdded);
		}
		pseudoVertex.setSeen(true);
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

	private static Pair<Vertex, Stack<Edge>> walkBackwardsFromZAndReturnRepeatingVertexAlongWithPath(
			Vertex unreachableNodeZ) {
		Vertex u = null;
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
				}
			}

			u = reachableVertices.remove();
		} while (!u.isSeen());
		reachableVertices = null;
		return new Pair<Vertex, Stack<Edge>>(u, backwardsWalkStack);
	}
}
