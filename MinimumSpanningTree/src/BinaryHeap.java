import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Binary Heap is heap data Structure. It is a binary tree with additional
 * constraints. we represent Binary heap with array.
 * 
 * It satisfies the complete binary tree property and Heap property(min and max
 * heap) This program implements the Binary heap data structure satisfying the
 * property of a Min Heap.
 * 
 * 
 * @author M Krishna Kavya
 * 
 */
public class BinaryHeap<T extends Comparable<? super T>> {

	protected List<T> heap = null;
	Comparable<T> comparator = null;
	
	public String toString() {
		return (heap.toString());
	};

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
		heap = new ArrayList<T>(size+1);
	}
	
	/**
	 * 
	 * @return flag - whether heap is empty or not.
	 */
	boolean isEmpty()
	{
		if (heap==null || heap.size()==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Adds x in to the heap.
	 * 
	 * @param x
	 */
	public void add(T x) {
		insert(x);
	}

	/**
	 * The value is inserted at the end of the Binary Heap and perform the
	 * percolate up operation to maintain the heap property.
	 * 
	 * @param value
	 *            - value to be inserted.
	 */
	public void insert(T value) {
		heap.add(value);
		percolateUp(heap.size()-1);

	}

	/**
	 * The percolate up method allocate appropriate position for the new node
	 * inserted. In this program follows the Max heap property. The parent node
	 * should always have a value more than its child nodes. The locations of
	 * the nodes are changed and the new node is placed in a position that
	 * satisfies Max Heap Property.
	 */
	public void percolateUp(int i) {
		T temp = heap.get(i);
		while (temp.compareTo(heap.get(((i-1)/ 2))) < 0) {
			heap.set(i,heap.get((i-1) / 2));
			i = (i-1) / 2;
		}
		heap.set(i,temp);
	}

	/**
	 * The method removes the element from the top. In a Min Heap . The root has
	 * the highest value and is the first one to be deleted.
	 * 
	 * The root is replaced with last element in the element and percolate Down
	 * operation is performed to restore Heap property.
	 * 
	 */
	public void deleteMin() {
		remove();
	}

	public T remove() {  
		T minimum = heap.get(0);
		percolateDown(heap.size()-1);
		return minimum;
	}

	/**
	 * Returns the minumum element.
	 * 
	 * @return
	 */
	public T min() {
		return peek();
	}

	/**
	 * This returns the top element.
	 * 
	 * @return
	 */
	public T peek() {
		return heap.get(0);
	}

	 
	/**
	 * The method checks the heap property. The operation starts from the root
	 * node and is performed till the last level. The parent element should
	 * always have a greater value than child. First condition if the element
	 * has two or only one child Case 1: The greatest among the two replaces the
	 * parent node if any. Case 2: Child replaces the parent node if it is
	 * greater.
	 * 
	 * @param i-
	 *            Index of the root
	 */
	public void percolateDown(int j) {
			T itemToBePercolated = heap.get(j);
		int i=0;
		heap.set(0,itemToBePercolated);
		heap.remove(j);
		int index= -1;
		T smallerChild=null;
		while(i!=index && heap.size()>(2*i+1)){
			
		if( (2*i+2)<heap.size() && heap.get(2*i+1).compareTo(heap.get(2*i+2))>0)
		{
			smallerChild = heap.get(2*i+2);
			index =2*i+2;
		}
		else 
		{	
			smallerChild =heap.get(2*i+1);
			index =2*i+1;
		}
		if(smallerChild.compareTo(itemToBePercolated)<0)
		{
			heap.set(index,itemToBePercolated);
			heap.set(i,smallerChild);
			i=index;
			index=-1;
		}
		else
		{
			i=index;
		}
		}
	}
	public static void main(String[] args) {
		BinaryHeap obj = new BinaryHeap(1000000);
		Timer time = new Timer();
   		time.timer();
   		for(int j=0;j<1000000;j++){
			 obj.insert((int) ((Math.random()+2)*10));
			 }
   		System.out.println("Inserted 1000000 nodes");
   		System.out.println("The deleted root:"+obj.remove());
		
		time.timer();
	}



}
