import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implement Merge Sort (say, from Cormen's book) in Java using generics.
   See http://www.utdallas.edu/~rbk/teach/2016s/notes/mergesort.pdf for pseudocode of merge sort.
   Compare its running time on n > 1 million elements with another
   O(nlogn) algorithm, say for example, sorting using a priority queue:

   Create a priority queue from an array list (which is a collection)
   containing the input elements using the constructor in the Java Library. 
   From https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html: 

   PriorityQueue(Collection<? extends E> c)
   Creates a PriorityQueue containing the elements in the specified collection.

   Remove the elements from the Priority Queue until it gets empty.
 * @author kanchan
 *
 */
public class SortDriver {

	public static void main(String[] args) {
		try {
			Statistics stats = new Statistics();
			String numTrialsString = System.console()
					.readLine("Please provide number of trials you would like to execute:");
			int numTrials = Integer.parseInt(numTrialsString.trim());
			
			String numEntriesString = System.console()
					.readLine("Please provide number of random integers you would like to load in the input data:");
			int numEntries = Integer.parseInt(numEntriesString.trim());
			
			System.out.println("Please select Sorting technique");
			System.out.println("1: Merge Sort");
			System.out.println("2: Sorting using Priority Queue");

			String algorithmChoiceString = System.console().readLine("Enter your choice:");
			int algorithmChoice = Integer.parseInt(algorithmChoiceString.trim());

			for (int i = 1; i <= numTrials; i++) {

				int count = numEntries;
				switch (algorithmChoice) {
				case 1:
					/**
					 * Prepare Input Data
					 */
					Integer[] array = prepareMergeSortData(count);
					System.out.println();
					stats.printFirstTenElements(array,"List/Array before sorting ");

					/**
					 * Sort and log timings
					 */
					stats.timer();
					MergeSort.sort(array);
					stats.timer();
					stats.printFirstTenElements(array,"List/Array after sorting ");
					array = null;
					break;
				case 2:
					/**
					 * Prepare Input Data
					 */
					HeapSort<Integer> hpq = new HeapSort<>();
					List<Integer> arrayList = preparePriorityQueueData(count);
					hpq.loadPriorityQueue(arrayList);
					stats.printFirstTenElements(arrayList,"List/Array before sorting ");

					/**
					 * Sort data
					 */
					stats.timer();
					arrayList = hpq.sort();
					stats.printFirstTenElements(arrayList,"List/Array after sorting ");
					stats.timer();
					array = null;
					break;
				default:
					System.out.println("Invalid choice.");
					break;
				}

				System.out.println("After Sorting ");
				Runtime.getRuntime().gc();
			}
			System.out.println("================================================================================");
			stats.printAverageElapsedTimeAndMemory();

		} catch (NumberFormatException e) {
			System.out.println("Error occured while parsing user input. Please make sure that you type valid integers");
		}

	}

	/**
	 * This method populates random integers in an List
	 * @param count
	 * @return
	 */
	public static List<Integer> preparePriorityQueueData(int count) {
		List<Integer> arrayList = new ArrayList<>(count);
		for (int j = 0; j < count; j++) {
			arrayList.add(j, (int) ((Math.random() * 1000000)));
		}
		return arrayList;
	} 

	/**
	 * This method populates random integers in an Array
	 * @param count
	 * @return
	 */
	public static Integer[] prepareMergeSortData(int count) {
		Integer[] array = new Integer[count];
		for (int j = 0; j < count; j++) {
			array[j] = (int) ((Math.random() * 1000000));
		}
		return array;
	}

}
