import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

/**
 * This class implements Multi-dimensional Search
 * 
 * @author Kavya Krishna Maringanty, Kanchan Waikar, Sruti Paku
 * 
 *         Date Created : Apr 3, 2016 - 7:08:23 PM
 *
 */
public class MultiDimensionalSearch {

	private final Set EMPTY_SET = new HashSet();

	Map<DescriptionKey, Set<Item>> mapForSameSame = new HashMap<DescriptionKey, Set<Item>>();
	Map<Long, TreeSet<Item>> mapOfDescriptionSubStringAndPrice = new TreeMap<Long, TreeSet<Item>>();
	TreeMap<Long, Item> mapById = new TreeMap<Long, Item>();

	int insert(Long id, double price, Long[] description, int size) {

		Item item = mapById.get(id);
		boolean isNew = false;
		if (item == null) {
			item = new Item(id, price, description);
			isNew = true;
		} else {
			delete(id);
			if (description.length == 0) {
				description = item.getDescription();
				item.setPrice(price);
			} else {
				item.setDescription(description);
				item.setPrice(price);
			}
		}
		/**
		 * id field does not change hence need not be revisited.
		 */
		mapById.put(item.getId(), item);

		if (description.length > 8) {
			DescriptionKey key = new DescriptionKey(description);
			Set<Item> commonItems = mapForSameSame.get(key);
			if (commonItems == null) {
				commonItems = new HashSet<Item>();
			}
			commonItems.add(item);
			mapForSameSame.put(key, commonItems);
		}

		for (long subString : description) {
			TreeSet<Item> treeSet = mapOfDescriptionSubStringAndPrice.get(description[0]);
			if (treeSet == null) {
				treeSet = priceBasedEmptyTreeSet();
			}
			treeSet.add(item);
			mapOfDescriptionSubStringAndPrice.put(subString, treeSet);
		}

		if (isNew) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * This method returns a price comparator based empty Treeset
	 * 
	 * @return
	 */
	private TreeSet<Item> priceBasedEmptyTreeSet() {
		return new TreeSet<>(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return o1.getPrice().compareTo(o2.getPrice());
			}
		});
	}

	double find(long id) {
		Item item = mapById.get(id);
		if (item == null) {
			return 0;
		} else {
			return item.getPrice();
		}
	}

	/**
	 * This method removes Item from indexes by ID.
	 * 
	 * @param id
	 * @return
	 */
	long delete(long id) {
		Item item = mapById.remove(id);
		if ((item.getDescription().length > 0)) {
			DescriptionKey descKey = new DescriptionKey(item.getDescription());
			Set<Item> hashSet = mapForSameSame.get(descKey);
			hashSet.remove(item);
			mapForSameSame.put(descKey, hashSet);
		}
		removeFromMapByDesc(id, item);
		return 0;
	}

	/**
	 * @param id
	 * @param item
	 */
	private void removeFromMapByDesc(long id, Item item) {
		for (long subString : item.getDescription()) {
			TreeSet<Item> set = mapOfDescriptionSubStringAndPrice.get(subString);
			Item itemTobeDeleted = null;
			for (Item item2 : set) {
				if (item2.getId() == id) {
					itemTobeDeleted = item2;
				}
			}
			set.remove(itemTobeDeleted);
		}
	}

	double findMinPrice(long des) {
		TreeSet<Item> set = mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			set.first().getPrice();
		}
		return Double.MIN_VALUE;
	}

	double findMaxPrice(long des) {
		TreeSet<Item> set = mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {

			set.last().getPrice();
		}
		return Double.MIN_VALUE;
	}

	int findPriceRange(long des, double lowPrice, double highPrice) {
		return extractSubSetForDescription(des, lowPrice, highPrice).size();
	}

	/**
	 * @param des
	 * @param lowPrice
	 * @param highPrice
	 */
	private Set<Item> extractSubSetForDescription(long des, double lowPrice, double highPrice) {
		TreeSet<Item> set = mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			Item lowItem = new Item(-1L, lowPrice, null);
			Item highItem = new Item(-1L, highPrice, null);
			return set.subSet(lowItem, true, highItem, true);
		}
		return EMPTY_SET;
	}

	double priceHike(long minid, long maxid, double rate) {
		double netIncrease =0;
		Map<Long, Item> items = mapById.subMap(minid, maxid);
		for (Item item : items.values()) {
			double newPrice =item.getPrice()*((double)1+(rate/100));
			double diff=newPrice-item.getPrice();
			item.setPrice(diff);
			netIncrease+=diff;
		}
		return netIncrease;
	}

	int range(double lowPrice, double highPrice) {
		return 0;
	}

	/**
	 * 
	 * SameSame(): Find the number of items that satisfy all of the following
	 * conditions: The description of the item contains 8 or more numbers, and,
	 * The description of the item contains exactly the same set of numbers as
	 * another item.
	 * 
	 * @return - number of items that satisfy both conditions.
	 */
	int samesame() {
		int counter = 0;
		for (Map.Entry<DescriptionKey, Set<Item>> entry : mapForSameSame.entrySet()) {
			int size = entry.getValue().size();
			if (size > 1) {
				counter += size;
			}
		}
		return counter;
	}

	public static void main(String[] args) {
		Statistics stats = new Statistics();
		stats.timer();

		stats.timer("Time Taken");
	}
}
