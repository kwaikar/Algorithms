/**
 * Generic DTO class for returning inputs
 * @author kanchan
 *
 * @param <T>
 * @param <G>
 */
public class Pair<T, G> {

	T first;
	G second;

	public Pair(T first, G second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public T getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public G getSecond() {
		return second;
	}

}
