import java.util.List;

// Ver 1.0:  Wed, Feb 3.  Initial description.
// Ver 1.1:  Thu, Feb 11.  Simplified Index interface

public class IndexedHeap<T extends Comparable<? super T> & Index> extends BinaryHeap {

	/** Create an empty priority queue of given maximum size */
	IndexedHeap(int n) {
		super(n);
		for (int i = 0; i < super.heap.size(); i++) {
			((T) super.heap.get(i)).putIndex(i);
		}
	}

	/** restore heap order property after the priority of x has decreased */
	void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}

	public void percolateUp(int i) {
		T temp = (T) heap.get(i);
		while (temp.compareTo((T) heap.get(((i - 1) / 2))) < 0) {
			T current = (T) heap.get(i);
			T parent = (T) heap.get((i - 1) / 2); 
			heap.set(i, parent);
			heap.set(((i - 1) / 2), current);
			current.putIndex(((i - 1) / 2));
			parent.putIndex(i);
			i = (i - 1) / 2;
		}
		heap.set(i, temp);
		temp.putIndex(i);
	}

	@Override
	public void percolateDown(int j) {
		T itemToBePercolated = (T) heap.get(j);
		int i = 0;
		heap.set(0, itemToBePercolated);
		itemToBePercolated.putIndex(0);
		heap.remove(j);
		int index = -1;
		T smallerChild = null;
		while (i != index && heap.size() > (2 * i + 1)) {

			if ((2 * i + 2) < heap.size() && ((T) heap.get(2 * i + 1)).compareTo((T) heap.get(2 * i + 2)) > 0) {
				smallerChild = (T) heap.get(2 * i + 2);
				index = 2 * i + 2;
			} else {
				smallerChild = (T) heap.get(2 * i + 1);
				index = 2 * i + 1;
			}
			if (smallerChild.compareTo(itemToBePercolated) < 0) {
				heap.set(index, itemToBePercolated);
				itemToBePercolated.putIndex(index);
				heap.set(i, smallerChild);
				smallerChild.putIndex(i);
				i = index;
				index = -1;
			} else {
				i = index;
			}
		}
	}

	public static void main(String[] args) {

		Graph graph = Graph.acceptGraphInput(args.length > 0 ? args[0] : null, false);
		IndexedHeap heap = new IndexedHeap(11);
		for (Vertex vertex : graph) {
			heap.insert(vertex);
			System.out.println(heap);
		}
		System.out.println("============================");
		for (Vertex vertex : graph) {
			heap.deleteMin();
			System.out.println(heap);
		}
	}
}
