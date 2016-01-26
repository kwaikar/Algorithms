import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of LinkedList class with custom iterator.
 * 
 * @author kanchan
 * @param <T>
 */
public class LinkedSortedSetList<T> extends LinkedList<T>implements Iterable<T>, Set<T> {

	/**
	 * Constructor that accepts list
	 * 
	 * @param t
	 */
	public LinkedSortedSetList(List<T> t) {
		for (T t2 : t) {
			super.add(t2);
		}
	}

	/**
	 * Default constructor
	 */
	public LinkedSortedSetList() {
	}

	/**
	 * Constructor that accepts array
	 * 
	 * @param t
	 */
	public LinkedSortedSetList(T[] t) {
		for (T t2 : t) {
			super.add(t2);
		}
	}

	private static final long serialVersionUID = 1L;
	Iterator<T> itr = null;

	@Override
	public Iterator<T> iterator() {
		itr = super.iterator();
		return new ListIterator();
	}

	/**
	 * Iterator that returns null whenever list has been exhausted instead of
	 * throwing exception
	 * 
	 * @author kanchan
	 *
	 * @param <T>
	 */
	private class ListIterator implements Iterator<T> {

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public T next() {
			if (itr.hasNext()) {
				return (T) itr.next();
			} else {
				return null;
			}
		}
	}
}
