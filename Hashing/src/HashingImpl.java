

import java.util.*;
/**
 * This class implements the Closed hashing technique
 * @author Kanchan Waikar
 * Date Created : Feb 25, 2016 - 11:28:31 PM
 *
 * @param <T>
 */
public class HashingImpl<T> {
	static int initialSize = 16;

	static class HashEntry {
		Object element;
		boolean occupied;

		HashEntry(Object x) {
			element = x;
			occupied = true;
		}
	}

	HashEntry[] table;
	int size, maxSize;
	Set<T> overflow;

	HashingImpl() {
		table = new HashEntry[initialSize];
		size = 0;
		maxSize = initialSize;
		overflow = new HashSet<>();
	}

	HashingImpl(int n) {
		table = new HashEntry[n];
		size = 0;
		maxSize = n;
		overflow = new HashSet<>();
	}

	int find(T x) {
		// find x in hash table and return index of the element if found,
		// otherwise return the index where it stopped
		return 0;
	}

	public void add(T x) {
	}

	public T get(T x) {
		return null;
	}

	public T remove(T x) {
		// If x exists, remove it from the hash table and return it.
		// Otherwise return null;
		return null;
	}

	public boolean contains(T x) {
		return false;
	}

	void resize() {
	}
}