import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Extend the "unzip" algorithm discussed in class on Thu, Jan 21 to
   "multiUnzip" on the SinglyLinkedList class:

   void multiUnzip(int k) {
   	// Rearrange elements of a singly linked list by chaining
   	// together elements that are k apart.  k=2 is the unzip
   	// function discussed in class.  If the list has elements
	// 1..10 in order, after multiUnzip(3), the elements will be
   	// rearranged as: 1 4 7 10 2 5 8 3 6 9.  Instead if we call
	// multiUnzip(4), the list 1..10 will become 1 5 9 2 6 10 3 7 4 8.
   }
 * @author kanchan
 *
 * @param <T>
 */
public class MultiUnzipSinglyLinkedList<T> extends SinglyLinkedList<T> {

	void unzip(int k) {

		if (size < k) { // Too few elements. No change.
			return;
		}
		/**
		 * Invariants: subLists - holds "k" current pointers - one per sublist
		 * heads - "k" pointers that points to each of the last k sublists.
		 * 			This is required for combining all sublists 
		 * currentPointer - Pointer that traverses through original list 
		 * temp - temporary pointer used for clearing the items that are no more required to be pointed to.
		 */
		List<Entry<T>> subLists = new ArrayList<Entry<T>>(k);
		List<Entry<T>> heads = new ArrayList<>(k);

		/**
		 * Load First "k" pointers in heads list as well as subLists
		 */
		Entry<T> currentPointer = header.next;
		for (int i = 0; i < k && currentPointer != null; i++) {
			subLists.add(i, currentPointer);
			heads.add(i,currentPointer);
			currentPointer = currentPointer.next;
		}

		/**
		 * fill subList with individual sublists.
		 */
		Entry<T> temp;
		while (currentPointer != null) {
			for (int i = 0; i < k && currentPointer != null; i++) {
				/**
				 * Get i'th sublist, increment its pointer to the
				 * "currentPointer" item and set the same in sublist, Also set
				 * it's next reference as null.
				 */
				Entry<T> current = subLists.get(i);
				current.next = (currentPointer);
				temp = currentPointer;
				currentPointer = currentPointer.next;
				temp.next = null;
				subLists.set(i, current.next);
			}
		}
		/**
		 * subLists is now pointing to last elements applicable, join them with
		 * subsequent heads, so that a chain forms.
		 */
		for (int i = 0; i < k; i++) {
			Entry<T> current = subLists.get(i);
			if (i < subLists.size() - 1) {
				current.next = heads.get(i+1);
			}
		}

	}

	void printTop50() {
		System.out.println("Top 50 elements of the list");
		int counter = 0;
		Entry<T> x = header.next;
		while (x != null && counter < 50) {
			System.out.print(x.element + " ");
			x = x.next;
			counter++;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Please enter number of Integers you would like to Mult-unzip:");
			int numEntriesInList = in.nextInt();
			System.out.println("Please enter the value for 'k' you would like for Multi-unzip:");
			int kValue = in.nextInt();
			if (kValue > 0) {
				MultiUnzipSinglyLinkedList<Integer> lst = new MultiUnzipSinglyLinkedList<>();
				for (int i = 1; i <= numEntriesInList; i++) {
					lst.add(new Integer(i));
				}
				lst.printTop50();
				Statistics stats = new Statistics();
				stats.timer();
				lst.unzip(kValue);
				stats.timer("Time Taken for MultiUnzip operation");
				lst.printTop50();
			} else {
				System.out.println("Multiunzip does not support zero value for k");
			}
		} finally {
			in.close();
		}
	}
}
