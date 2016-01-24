import java.util.ArrayList;
import java.util.List;

public class RandomDataPreparationManager {

	/**
	 * This method populates random integers in an List
	 * @param count
	 * @return
	 */
	public static List<Integer> prepareRandomIntegeArrayList(int count) {
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
	public static Integer[] prepareRandomIntegerArray(int count) {
		Integer[] array = new Integer[count];
		for (int j = 0; j < count; j++) {
			array[j] = (int) ((Math.random() * 1000000));
		}
		return array;
	}

}
