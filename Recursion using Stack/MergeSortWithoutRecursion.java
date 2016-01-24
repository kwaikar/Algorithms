import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * This class implements Merge Sort implementation without recursion. Merge
 * Method uses a single
 * 
 * @author kanchan
 *
 */
public class MergeSortWithoutRecursion {

	/**
	 * Public static method exposed for execution of
	 * 
	 * @param array
	 */
	public static <T extends Comparable<T>> void sort(T[] array) {
		mergeSort(array, 0, array.length - 1);
	}

	public static void main(String[] args) {
		Integer[] input = { 10,1 };
		Integer[] sortedInput = { 1, 10 };
		MergeSortWithoutRecursion.sort(input);
		System.out.println();
		System.out.println("Sanity Test case execution success status: " + Arrays.deepEquals(input, sortedInput));
System.out.println("-----------------------------------------------------------------------------------------------------");
		Statistics stats =new Statistics();
		Integer[] array = DataPreparationHelper.acceptIntegerArrayFromUser(args);//DataPreparationHelper.prepareRandomIntegerArray(1000000);
		stats.printFirstTenElements(array,"Top 10 entries from input before sorting ");
		stats.timer();
		MergeSortWithoutRecursion.sort(array);		
		stats.timer();
		stats.printFirstTenElements(array,"Top 10 entries from input after sorting ");

		array = null;

	}

	/**
	 * This method has recursive mergeSort calls that implement merge sorting
	 * algorithm.
	 * 
	 * @param array
	 * @param left
	 * @param right
	 */
	private static <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
		Stack<ExecutableItem> items = new Stack<>();
		items.push(new ExecutableItem(left, right, false));

		while (!items.isEmpty()) {

			ExecutableItem item = items.pop();
			if (item.isMerge()) {
				/**
				 * All Left and right sub-parts have been sorted already. Range
				 * is ready for merge.
				 */
				merge(array, item);
			} else if (item.getLeft() < item.getRight()) 
			{
				int q = (item.getLeft() + item.getRight()) / 2;
				/**
				 * Mark Item ready for merging
				 */
				items.push(new ExecutableItem(item.getLeft(), item.getRight(), true));
				/**
				 * Add left portion of the pivot to stack.
				 */
				if (item.getLeft() != q)
					items.push(new ExecutableItem(item.getLeft(), q, false));
				/**
				 * Add right portion of the pivot for sorting
				 */
				if ((q + 1) != item.getRight())
					items.push(new ExecutableItem(q + 1, item.getRight(), false));
			}
		}
	}

	/**
	 * This method merges p-q partition with q+1 - r partition.
	 * 
	 * @param array
	 *            : Array on which merge needs to be performed.
	 * @param item
	 *            : contains range of array to be merged q being the middle
	 *            item.
	 */
	private static <T extends Comparable<T>> void merge(T[] array, ExecutableItem item) {
		int p = item.getLeft();
		int r = item.getRight();
		int q = (p + r) / 2;

		/**
		 * Create a temporary array that contains exactly data that needs to be
		 * merged.
		 */
		T[] temp = Arrays.copyOfRange(array, p, r + 1);

		int k = p;
		q = q - p;
		r = r - p;
		p = 0;

		/**
		 * Merge both the portions from temporary array in place.
		 */
		int i = p, j = q + 1;
		while (i <= q && j <= r) {
			if ((temp[i]).compareTo(temp[j]) > 0) {
				array[k] = temp[j++];
			} else {
				array[k] = temp[i++];
			}
			k++;
		}
		/**
		 * Copy remaining items from first section, if any
		 */
		while (j <= r) {
			array[k++] = temp[j++];
		}
		/**
		 * Copy remaining items from second section, if any
		 */
		while (i <= q) {
			array[k++] = temp[i++];
		}
		temp = null;
	}

}
