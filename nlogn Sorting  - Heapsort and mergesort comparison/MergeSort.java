import java.util.Arrays;

/**
 * This class implements Merge Sort implementation using Java - Generics.
 * Merge Method uses a single  
 * @author kanchan
 *
 */
public class MergeSort {

	/**
	 * Public static method exposed for execution of 
	 * @param array
	 */
	public static <T extends Comparable<T>>  void sort(T[] array)
	{
		mergeSort(array,0,array.length-1);
	}
	
	/**
	 * This method has recursive mergeSort calls that implement merge sorting algorithm.
	 * @param array
	 * @param p
	 * @param r
	 */
	private static <T extends Comparable<T>> void mergeSort(T[] array, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(array, p, q);
			mergeSort(array, q + 1, r);
			merge(array, p, q, r);
		}
	}
	

	/**
	 * This method merges p-q partition with q+1 - r partition.
	 * @param array
	 * @param p
	 * @param q
	 * @param r
	 */
	private static  <T extends Comparable<T>> void merge(T[] array, int p, int q, int r)
	{
	
		/**
		 * Create a temporary array that contains exactly data that needs to be merged.
		 */
		T[] temp =Arrays.copyOfRange(array,p,r+1);
		
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
		temp=null;
	}

}
