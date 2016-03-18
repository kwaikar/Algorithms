/**
 * Generic Pair Class for exchanging data generically between modules/functions.
 * 
 * @author Kanchan Waikar Date Created : Mar 12, 2016 - 8:56:26 PM
 *
 * @param <K>
 * @param <W>
 */
public class Pair<K, W> {

	private   K first;
	private   W second;

	/**
	 * Constructor for constructing Pair Object
	 * 
	 * @param first
	 *            -First value to be sent
	 * @param second
	 *            - Second value to be sent.
	 */
	public Pair(K first, W second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public K getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public W getSecond() {
		return second;
	}

}
