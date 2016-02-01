import java.util.Iterator;
import java.util.List;

/*
 * Given two linked lists implementing sorted sets, write functions for
   union, intersection, and set difference of the sets.
 *
 * @author kanchan
 */
public class SetOperationsOnList {
	public static void main(String[] args) {
		Pair<LinkedSortedSetList<Integer>, LinkedSortedSetList<Integer>> pair = DataPreparationHelper
				.acceptTwoIntegerLinkedSortedSetsFromUser(args);
		List<Integer> intersectionList = new LinkedSortedSetList<Integer>();
		List<Integer> unionList = new LinkedSortedSetList<Integer>();
		List<Integer> diffList = new LinkedSortedSetList<Integer>();

		LinkedSortedSetList<Integer> l1 = pair.getFirst();
		LinkedSortedSetList<Integer> l2 = pair.getSecond();

		Statistics stats = new Statistics();
		stats.timer();
		SetOperationsOnList.intersect(l1, l2, intersectionList);
		stats.timer("Intersect Operation");
		stats.printFirstTenElements(intersectionList, "Top 10 items from Intersection:");

		stats.timer();
		SetOperationsOnList.union(l1, l2, unionList);
		stats.timer("Union Operation");
		stats.printFirstTenElements(unionList, "Top 10 items from Union:");

		stats.timer();
		SetOperationsOnList.difference(l1, l2, diffList);
		stats.timer("Set Difference Operatin");
		stats.printFirstTenElements(diffList, "Top 10 items from Difference:");
	}

	/**
	 * This method iterates through sorted lists and returns the intersection of
	 * the same
	 * 
	 * @param list1
	 * @param list2
	 * @param intersectionList
	 */
	public static <T extends Comparable<? super T>> void intersect(List<T> list1, List<T> list2,
			List<T> intersectionList) {

		if (intersectionList != null && list1.size() > 0 && list2.size() > 0) {
			Iterator<T> itr1 = list1.iterator();
			Iterator<T> itr2 = list2.iterator();

			T item1 = itr1.next();
			T item2 = itr2.next();
			int compare;

			/**
			 * item1 - Pointer pointing to current entry of list1
			 * item2 - Pointer pointing to current entry of list2
			 * compare - holds the comparison result.
			 */

			while (true) {
				if ((compare = item1.compareTo(item2)) == 0) {
					intersectionList.add(item1);
					/**
					 * Increment both items
					 */

					if (itr2.hasNext()) {
						item2 = itr2.next();
					} else {
						break;
					}
					if (itr1.hasNext()) {
						item1 = itr1.next();
					} else {
						break;
					}

				} else if (compare < 0) {
					/**
					 * Left item iterator is pointing to item that is is smaller
					 * than right item, increment the same
					 */
					if (itr1.hasNext()) {
						item1 = itr1.next();
					} else {
						break;
					}
				} else {
					/**
					 * Right item is smaller than left, increment right iterator
					 */
					if (itr2.hasNext()) {
						item2 = itr2.next();
					} else {
						break;
					}
				}
			}
		}
	}

	/**
	 * This method returns the union of both the lists.
	 * 
	 * @param list1
	 * @param list2
	 * @param unionList
	 */
	public static <T extends Comparable<? super T>> void union(List<T> list1, List<T> list2, List<T> unionList) {

		if ((list1 == null || list1.size() == 0) && (list2 != null && list2.size() != 0)) {
			unionList = list2;
		} else if ((list2 == null || list2.size() == 0) && (list1 != null && list1.size() != 0)) {
			unionList = list1;
		} else if (unionList != null) {
			Iterator<T> itr1 = list1.iterator();
			Iterator<T> itr2 = list2.iterator();

			/**
			 * item1 - Pointer pointing to current entry of list1
			 * item2 - Pointer pointing to current entry of list2
			 * compare - holds the comparison result.
			 */
			T item1 = itr1.next();
			T item2 = itr2.next();
			int compare;

			while (true) {
				if (item1 == null && item2 == null) {
					break;
				} else if (item1 != null && item2 == null) {
					/**
					 * Add all item1 entries to the list
					 */
					unionList.add(item1);
					while (itr1.hasNext()) {
						unionList.add(itr1.next());
					}
					break;
				} else if (item2 != null && item1 == null) {
					/**
					 * Add all item2 entries to the list
					 */
					unionList.add(item2);
					while (itr2.hasNext()) {
						unionList.add(itr2.next());
					}
					break;
				} else {
					/**
					 * Both lists are not empty, add only unique entries to the
					 * list
					 */
					if ((compare = item1.compareTo(item2)) == 0) {
						unionList.add(item1);
						item2 = itr2.next();
						item1 = itr1.next();

					} else if (compare < 0) {
						/**
						 * Left item iterator is pointing to item that is is
						 * smaller than right item, increment the same
						 */
						unionList.add(item1);
						item1 = itr1.next();

					} else {
						/**
						 * Right item is smaller than left, increment right
						 * iterator
						 */
						unionList.add(item2);
						item2 = itr2.next();
					}
				}
			}
		}
	}

	/**
	 * This method returns the difference between two linked lists.
	 * 
	 * @param list1
	 * @param list2
	 * @param diffList
	 */
	public static <T extends Comparable<? super T>> void difference(List<T> list1, List<T> list2, List<T> diffList) {
		if (list2 == null || list2.size() == 0 || list1 == null || list1.size() == 0) {
			diffList = list1;
		} else {
			Iterator<T> itr1 = list1.iterator();
			Iterator<T> itr2 = list2.iterator();
			/**
			 * item1 - Pointer pointing to current entry of list1
			 * item2 - Pointer pointing to current entry of list2
			 * compare - holds the comparison result.
			 */
			T item1 = itr1.next();
			T item2 = itr2.next();
			int compare;
			while (true) {
				/**
				 * No elements left in first list, diffList population is
				 * complete
				 */
				if (item1 == null) {
					break;
				} else if (item2 == null) {
					/**
					 * No items in second list, populate all items from first
					 * list
					 */
					diffList.add(item1);
					while (itr1.hasNext()) {
						diffList.add(itr1.next());
					}
					break;
				} else if ((compare = item1.compareTo(item2)) == 0) {
					/**
					 * Common item - do not return.
					 */
					item1 = itr1.next();
					item2 = itr2.next();
				} else if (compare < 0) {
					/**
					 * Item 1 is smaller, add and increment the iterator to next
					 * item.
					 */
					diffList.add(item1);
					item1 = itr1.next();
				} else {
					/**
					 * Item 2 is smaller than item 1, dont include it and move
					 * pointer to next item.
					 */
					item2 = itr2.next();
				}
			}
		}
	}
}
