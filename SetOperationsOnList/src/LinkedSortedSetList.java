import java.util.Iterator;
import java.util.LinkedList;

/**
 * Implementation of LinkedList class with custom iterator.
 * @author kanchan
 * @param <T>
 */
public class LinkedSortedSetList  <T> extends LinkedList<T>implements Iterable<T> {

	private static final long serialVersionUID = 1L;
	Iterator<T> itr = null;
	@Override
	public Iterator<T> iterator() {
		itr = super.iterator();
		return new ListIterator<T>();
	}


	/**
	 * Iterator that returns null whenever list has been exhausted instead of throwing exception
	 * @author kanchan
	 *
	 * @param <T>
	 */
	private class ListIterator  <T>  implements Iterator<T>  {

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public T next() {
			if(itr.hasNext()){
				return (T)itr.next();
			}
			else
			{
				return null;
			}
		}
	}
}
