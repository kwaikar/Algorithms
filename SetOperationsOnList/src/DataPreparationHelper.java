import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class DataPreparationHelper {

	/**
	 * This method populates random integers in an List
	 * 
	 * @param size
	 *            - Size of list to be populated
	 * @return
	 */
	public static List<Integer> prepareRandomIntegeArrayList(int size) {
		List<Integer> arrayList = new ArrayList<>(size);
		for (int j = 0; j < size; j++) {
			arrayList.add(j, (int) ((Math.random() * 1000000)));
		}
		return arrayList;
	}

	/**
	 * This method populates random integers in an Array
	 * 
	 * @param size
	 *            - Size of Array to be populated
	 * @return
	 */
	public static Integer[] prepareRandomIntegerArray(int size) {
		Integer[] array = new Integer[size];
		for (int j = 0; j < size; j++) {
			array[j] = (int) ((Math.random() * 1000000));
		}
		return array;
	}

	public static void main(String[] args) throws Exception {

		FileWriter fr = new FileWriter(new File("input1.txt"));
		FileWriter fr2 = new FileWriter(new File("input2.txt"));
		Integer[] arr = prepareRandomIntegerArray(10000000);
		Integer[] arr2 = prepareRandomIntegerArray(10000000);
		HashSet<Integer> arrSet = new HashSet<Integer>(Arrays.asList(arr));
		HashSet<Integer> arrSet2 = new HashSet<Integer>(Arrays.asList(arr2));
		for (Integer integer : arrSet2) {
			fr.write(integer + " ");
		}
		for (Integer integer : arrSet) {
			fr2.write(integer + " ");
		}
		fr.close();
		fr2.close();
	}

	/**
	 * This method accepts Integer array either through file or through console
	 * from the user.
	 * 
	 * @param args
	 *            : Program arguments for handling the data file Input
	 * @return
	 */
	public static Integer[] acceptIntegerArrayFromUser(String[] args) {

		Integer[] array = null;
		Scanner in = null;
		try {
			if (args.length > 0) {
				array = readInputArrayFromFile(array, args[0]);
			}
			if (array == null || array.length == 0) {
				array = readInputFromUser(in);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return array;

	}

	/**
	 * This method reads input from user, creates an Integer array and returns
	 * the same.
	 * 
	 * @return
	 */
	private static Integer[] readInputFromUser(Scanner in) {
		Integer[] array = null;
		/**
		 * Accept the quantity of the data along with actual data.
		 */
		System.out.print("Please enter number of integers you want to input:");
		int s = in.nextInt();
		array = new Integer[s];
		System.out.print("Please enter Integers:");

		for (int i = 0; i < s; i++) {
			array[i] = in.nextInt();
		}
		System.out.println("Input Array: " + Arrays.toString(array));
		return array;
	}

	/**
	 * This method reads input array from file
	 * 
	 * @param array
	 * @param ar
	 * @return
	 */
	public static Integer[] readInputArrayFromFile(Integer[] array, String fileName) {

		List<Integer> ints = readInputArrayFromFile(fileName);

		array = new Integer[ints.size()];
		for (int i = 0; i < ints.size(); i++) {
			array[i] = ints.get(i);
		}
		return array;
	}

	private static List<Integer> readInputArrayFromFile(String fileName) {
		List<Integer> ints = new ArrayList<>();

		File inputFile = new File(fileName);
		if (inputFile.exists()) {
			try {
				Scanner in = null;
				/**
				 * Assume that file contains entire data to be provided as an
				 * input to the program to be executed.
				 */
				try {
					in = new Scanner(inputFile);
					while (in.hasNext()) {
						ints.add(in.nextInt());
					}
				} finally {
					in.close();
				}

			} catch (FileNotFoundException e) {
				System.out.println("File Not found at given path");
			}
		}
		return ints;
	}

	/**
	 * This method accepts Integer array either through file or through console
	 * from the user.
	 * 
	 * @param args
	 *            : Program arguments for handling the data file Input
	 * @return
	 */
	public static Pair<LinkedSortedSetList<Integer>, LinkedSortedSetList<Integer>> acceptTwoIntegerLinkedSortedSetsFromUser(
			String[] args) {
		Scanner in = null;
		LinkedSortedSetList<Integer> l1 = null;
		LinkedSortedSetList<Integer> l2 = null;
		try {
			in = new Scanner(System.in);

			if (args.length > 0) {
				l1 = new LinkedSortedSetList<Integer>(readInputArrayFromFile(args[0]));
			}
			if (l1 == null || l1.size() == 0) {
				System.out.println("Please enter details of list 1");
				l1 = new LinkedSortedSetList<Integer>(readInputFromUser(in));
			}
			if (args.length > 1) {
				l2 = new LinkedSortedSetList<Integer>(readInputArrayFromFile(args[1]));
			}
			if (l2 == null || l2.size() == 0) {
				System.out.println("Please enter details of list 2");
				l2 = new LinkedSortedSetList<Integer>(readInputFromUser(in));
			}
		} finally {
			in.close();
		}
		Pair<LinkedSortedSetList<Integer>, LinkedSortedSetList<Integer>> pair = new Pair<LinkedSortedSetList<Integer>, LinkedSortedSetList<Integer>>(
				l1, l2);
		return pair;
	}
}
