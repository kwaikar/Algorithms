/**
 * 
 * Binary Heap is heap data Structure. It is a binary tree with additional
 * constraints. we represent Binary heap with array.
 * 
 * It satisfies the complete binary tree property and Heap property(min and max
 * heap) This program implements the Binary heap data structure satisfying the
 * property of a Max Heap.
 * 
 * 
 * @author M Krishna Kavya
 * 
 */
public class BinaryHeap {

	public static int lastIndex;
	public static int[] heap = new int[0];

	/**
	 * initialize the integer array with the maximum size. initialize few nodes
	 * in the binary heap in the appropriate location. assign the first or the
	 * zero'th location element to be zero. The lastIndex keeps track of the
	 * index of the last element in the heap.
	 * 
	 * @param size
	 *            -size of the input array.
	 */
	BinaryHeap(int size) {
		// max Heap.
		heap = new int[size];
		heap[0] = 0;
		heap[1] = 100;
		heap[2] = 79;
		heap[3] = 90;
		heap[4] = 68;
		heap[5] = 72;
		heap[6] = 89;
		lastIndex = 6;
	}

	/**
	 * The value is inserted at the end of the Binary Heap and perform the
	 * percolate up operation to maintain the heap property.
	 * 
	 * @param value
	 *            - value to be inserted.
	 */
	public void insert(int value) {
		lastIndex = lastIndex + 1;
		heap[lastIndex] = value;
		percolateUp();

	}

	/**
	 * The percolate up method allocate appropriate position for the new node
	 * inserted. In this program follows the Max heap property. The parent node
	 * should always have a value more than its child nodes. The locations of
	 * the nodes are changed and the new node is placed in a position that
	 * satisfies Max Heap Property.
	 */
	public static void percolateUp() {
		System.out.println("The element inserted now is " + heap[lastIndex]);
		heap[0] = heap[lastIndex];
		int i = lastIndex;
		while (heap[i / 2] < heap[0]) {
			heap[i] = heap[i / 2];
			i = i / 2;
		}
		heap[i] = heap[0];
	}

	/**
	 * The method removes the element from the top. In a Max Heap . The root has
	 * the highest value and is the first one to be deleted.
	 * 
	 * The root is replaced with last element in the element and percolate Down
	 * operation is performed to restore Heap property.
	 * 
	 */
	public void delete() {
		int maximum = heap[1];
		heap[1] = heap[lastIndex];
		System.out.println("last element =" + heap[1]);
		percolateDown(1);

	}
	
	/**
	 * The method checks the heap property.
	 * The operation starts from the root node and is performed till the last level. 
	 * The parent element should always have a greater value than child.
	 * First condition if the element has two or only one child 
	 * Case 1:   The greatest among the two replaces the parent node if any. 
	 * Case 2:   Child  replaces the parent node if it is greater.
	 * @param i- Index of the root 
	 */
	public void percolateDown(int i) {
		int x = heap[i];
		int size = lastIndex - 1;
		int index = 0;
		while ((2 * i) <= size) {
			if ((2 * i) == size) {
				if (x < heap[size]) {
					heap[i] = heap[size];
					i = size;
				} else {
					break;
				}

			} else {
				if (heap[(2 * i)] >= heap[(2 * i) + 1]) {
					index = 2 * i;
				} else {
					index = (2 * i) + 1;
				}

				if (x < heap[index]) {
					heap[i] = heap[index];
					i = index;
				} else {
					break;
				}

			}
		}
		heap[i] = x;

	}

	public static void main(String[] args) {
		BinaryHeap obj = new BinaryHeap(10);
		obj.insert(102);
		obj.delete();
		System.out.println(heap[1]);
	}

}
