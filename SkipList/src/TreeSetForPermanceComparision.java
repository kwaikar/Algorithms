import java.util.TreeSet;

/**
 * The class uses the java Tree Set to perform insertion and deletion
 * operations.
 * 
 * @author M Krishna Kavya
 * 
 */

public class TreeSetForPermanceComparision {
	TreeSet<Integer> set = new TreeSet<Integer>();

	public void insert(int size) {
		for (int i = 1; i < size; i++) {
			set.add(i);
		}
	}

	public void delete(int num) {
		for (int i = 10; i < num; i++) {
			set.remove(i);
		}

	}

	public static void main(String[] args) {
		int size = 1000000, del = 100;
		TreeSetForPermanceComparision obj = new TreeSetForPermanceComparision();
		System.out.println("Statistics of Java Tree Set. Insert " + size
				+ " delete " + del);
		Timer.timer();
		obj.insert(size);
		Timer.timer();

		Timer.timer();
		obj.delete(del);
		Timer.timer();

	}

}
