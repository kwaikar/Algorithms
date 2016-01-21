import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class HeapSort<T extends Comparable<T>> {

	PriorityQueue<T> queue;

	/**
	 * This method loads list in the PriorityQueue data structure.
	 * 
	 * @param count
	 */
	public void loadPriorityQueue(List<T> array) {
		queue = new PriorityQueue<>(array);
	}

	/**
	 * This method calls the remove function function of the queue in order to
	 * retrieve the sorted array
	 */
	public List<T> sort() {
		List<T> tempList = new LinkedList<>();
		while (!queue.isEmpty()) {
			//tempList.add(queue.remove());
			// Please comment above line and uncomment follwing line for statistics without the return statement.
			 queue.remove();
		}
		return tempList;
	}

}