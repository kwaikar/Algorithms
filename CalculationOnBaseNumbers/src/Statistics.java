import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is a helper class that is responsible for Gathering and maintaining statistics. 
 * 
 * @author kanchan
 *
 */
public class Statistics {

	/**
	 * Used for storing status of the timer - start is depicted by phase "true"
	 * and end is depicted by phase "false"
	 */
	private boolean phase = false;
	private long startTime, endTime;
	private List<Long> elapsedTimes = new ArrayList<>();
	private List<Long> elapsedMemory = new ArrayList<>();

	/**
	 * This method starts timer, stops timer and logs the time elapsed between
	 * executions
	 */
	public void timer(String... title) {
		if (!phase) {
			startTime = System.currentTimeMillis();
			phase = true;
		} else {
			endTime = System.currentTimeMillis();
			elapsedTimes.add((endTime - startTime));
			if (title.length > 0 && title[0] != null)
				System.out.println("Statistics for Step " + title[0] + " : ");
			System.out.println("Time: " + (endTime - startTime) + " msec.");
			memory();
			System.out.println("-----------------------------------------");
			phase = false;
		}
	}


	/**
	 * This method logs statistics related to memory available as well as memory
	 * used
	 */
	private void memory() {
		long memAvailable = Runtime.getRuntime().totalMemory();
		long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		elapsedMemory.add(memUsed / 1000000);
		System.out.println("Memory: " + memUsed / 1000000 + " MB / " + memAvailable / 1000000 + " MB.");
	}

	/**
	 * This method prints the average elapsed time for
	 */
	public void printAverageElapsedTimeAndMemory() {

		Long sum = getAvg(elapsedTimes);
		System.out.println(
				"Average Time taken by " + elapsedTimes.size() + " Trials = " + sum / elapsedTimes.size() + " msec");

		sum = getAvg(elapsedMemory);
		System.out.println(
				"Average Memory taken by " + elapsedMemory.size() + " Trials = " + sum / elapsedMemory.size() + " MB");

	}

	/**
	 * This method returns average of values
	 * 
	 * @return
	 */
	public Long getAvg(List<Long> values) {
		Long sum = 0L;
		for (Long singleVal : values) {
			sum += singleVal;
		}
		return sum;
	}

	/**
	 * This method prints first 10 elements of the given structure
	 * @param dataStructureToBePrinted
	 * @param prefix
	 */
	<T> void printFirstTenElements(T[] dataStructureToBePrinted, String prefix) {
		int n = Math.min(dataStructureToBePrinted.length, 10);
		System.out.print(prefix + " : ");
		for (int i = 0; i < n; i++) {
			System.out.print(dataStructureToBePrinted[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * This method prints first 10 elements of the given structure
	 * @param dataStructureToBePrinted
	 * @param prefix
	 */
	<T> void printFirstTenElements(List<T> dataStructureToBePrinted, String prefix) {
		System.out.print(prefix + " : ");
		int n = Math.min(dataStructureToBePrinted.size(), 10);
		for (int i = 0; i < n; i++) {
			System.out.print(dataStructureToBePrinted.get(i) + " ");
		}
		System.out.println();
	}

}
