import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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

	Map<Long, TreeSet<Item>> mapOfDescriptionSubStringAndPrice = new TreeMap<Long, TreeSet<Item>>();
	Map<Long, Item> mapById = new HashMap<Long, Item>();

	int insert(Long id, double price, Long[] description, int size) {
		// Description of item is in description[0..size-1].
		// Copy them into your data structure.
		Item item = mapById.get(id);
		boolean isNew = false;

		Long[] oldDescription = null;

		if (item == null) {
			item = new Item(id, price, description);
			isNew = true;
		} else {
			oldDescription = item.getDescription();
			if (description.length == 0) {
				description = item.getDescription();
				item.setPrice(price);
			} else {
				item.setDescription(description);
				item.setPrice(price);
			}
		}
		mapById.put(item.getId(), item);

		for (long subString : description) {
			TreeSet<Item> treeSet = mapOfDescriptionSubStringAndPrice.get(description[0]);
			if (treeSet == null) {
				treeSet = new TreeSet<>(new Comparator<Item>() {
					@Override
					public int compare(Item o1, Item o2) {
						return o1.getPrice().compareTo(o2.getPrice());
					}
				});
			}
			treeSet.add(item);
			mapOfDescriptionSubStringAndPrice.put(subString, treeSet);
		}
		if(oldDescription!=null)
		{
			for (long subString : description) {
				TreeSet<Item> treeSet = mapOfDescriptionSubStringAndPrice.get(subString);
				if (treeSet != null) {
					 treeSet.remove(item);
				}
			}	
		}
		 
		if (isNew) {
			return 1;
		} else {
			return 0;
		}
	}

	double find(long id) {
		Item item = mapById.get(id);
		if (item == null) {
			return 0;
		} else {
			return item.getPrice();
		}
	}

	long delete(long id) {
		Item item = mapById.get(id);

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

		return 0;
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

		return 0;
	}

	int range(double lowPrice, double highPrice) {
		return 0;
	}

	int samesame() {
		return 0;
	}

	public static void main(String[] args) {
		Statistics stats = new Statistics();
		stats.timer();

		stats.timer("Time Taken");
	}
}
