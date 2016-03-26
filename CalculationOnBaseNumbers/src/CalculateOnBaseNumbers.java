import java.util.Scanner;

 
public class CalculateOnBaseNumbers {

 	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		try {
			System.out.println("Please enter number of Integers you would like to Mult-unzip:");
			int numEntriesInList = in.nextInt();
			System.out.println("Please enter the value for 'k' you would like for Multi-unzip:");
			int kValue = in.nextInt();
			if (kValue > 0) {
		/*		MultiUnzipSinglyLinkedList<Integer> lst = new MultiUnzipSinglyLinkedList<>();
				for (int i = 1; i <= numEntriesInList; i++) {
					lst.add(new Integer(i));
				}
				lst.printTop50();
				Statistics stats = new Statistics();
				stats.timer();
				lst.unzip(kValue);
				stats.timer("Time Taken for MultiUnzip operation");
				lst.printTop50();
*/			} else {
				System.out.println("Multiunzip does not support zero value for k");
			}
		} finally {
			in.close();
		}
	}
}
