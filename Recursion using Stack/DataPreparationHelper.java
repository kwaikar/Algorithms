import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * This method accepts Integer array either through file or through console
	 * from the user.
	 * @param args : Program arguments for handling the data file Input
	 * @return
	 */
	public static Integer[] acceptIntegerArrayFromUser(String[] args ) {
		Scanner in = null;
		Integer[] array=null;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			if (inputFile.exists()) {
				try {
					/**
					 * Assume that file contains entire data to be provided as an input to the program to be executed.
					 */
					List<Integer> ints = new ArrayList<>();
					try {
						in = new Scanner(inputFile);
						while (in.hasNext()) {
							ints.add(in.nextInt());
						}
					} finally {
						in.close();
					}
					array = new Integer[ints.size()];
					for (int i = 0; i < ints.size(); i++) {
						array[i] = ints.get(i);
					}
					return array;
				} catch (FileNotFoundException e) {
					System.out.println("File Not found at given path");
				}
			}
		}
		if (in == null) {
			try {
				/**
				 * Accept the quantity of the data along with actual data.
				 */
				in = new Scanner(System.in);
				System.out.print("Please enter number of integers you want to Sort:");
				int s = in.nextInt();
				array = new Integer[s];
				System.out.print("Please enter Integers:");

				for (int i = 0; i < s; i++) {
					array[i] = in.nextInt();
				}
				System.out.println("Input Array: " + Arrays.toString(array));
			} finally {
				in.close();
			}
		}
		return array;

	}

	
	
}
