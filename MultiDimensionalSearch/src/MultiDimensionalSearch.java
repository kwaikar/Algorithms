import java.util.Arrays;
import java.util.Comparator;
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

	private Set<Item> EMPTY_SET = new HashSet<Item>();

	/**
	 * mapForSameSame is a dedicated map created for samesame function and only
	 * Structure <Item.desc.substring, Treeset<Item>>
	 */
	Map<DescriptionKey, Set<Item>> mapForSameSame = new TreeMap<DescriptionKey, Set<Item>>(
			new Comparator<DescriptionKey>() {
				@Override
				public int compare(DescriptionKey o1, DescriptionKey o2) {
					int temp = o1.getLengthOfKey().compareTo(o2.getLengthOfKey().intValue());
					if (temp == 0) {
						temp = o1.getSumOfKeys().compareTo(o2.getSumOfKeys());
						if (temp == 0) {
							boolean equals = Arrays.equals(o1.getKey(), o2.getKey());
							if (equals) {
								return 0;
							} else {
								return -1;
							}
						} else {
							return temp;
						}
					} else {
						return temp;
					}

				}
			});

	/**
	 * description sub-part based look-up
	 */
	Map<Long, Set<Item>> mapOfDescriptionSubStringAndPrice = new TreeMap<Long, Set<Item>>();

	/**
	 * price based lookup - item reference is stored inside treeset Structure
	 * <Item.price, Treeset<Item>>
	 */
	TreeMap<Double, Set<Item>> mapByPriceAndItem = new TreeMap<Double, Set<Item>>();

	/**
	 * Id based map - can be used for look-up, CRUD on id
	 */
	TreeMap<Long, Item> mapById = new TreeMap<Long, Item>();

	/**
	 * Inserts an item into all indexes.
	 * 
	 * @param id
	 * @param price
	 * @param description
	 * @param size
	 * @return
	 */
	int insert(Long id, double price, long[] desc, int size) {

		boolean descChanged = false;
		boolean priceChanged = false;
		Long[] description = new Long[desc.length];
		for (int i = 0; i < desc.length; i++) {
			description[i] = desc[i];
		}
		if (id != null) {
			Item item = mapById.get(id);
			boolean isNew = false;
			if (item == null) {
				item = new Item(id, price, description);
				isNew = true;
			} else {
				if (description.length != 0 && !Arrays.deepEquals(description, item.getDescription())) {
					descChanged = true;
					cleanupSameSameMapForItem(item);
				}
				if (item.getPrice() != price) {
					priceChanged = true;
					removeFromItemBasedTreeset(mapByPriceAndItem, item.getPrice(), item);
				}
				if (descChanged || priceChanged) {
					// System.out.println("===" + item.getDescription());
					for (long subDescriptionKey : item.getDescription()) {
						removeFromItemBasedTreeset(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item);
					}
				}
				if (description.length == 0) {
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

			if (isNew || descChanged) {
				updateSameSameMap(item);
			}

			if (isNew || descChanged || priceChanged) {
				for (Long subString : item.getDescription()) {
					putItemInTreeSetOfItemsMap(mapOfDescriptionSubStringAndPrice, subString, item,
							priceBasedEmptyTreeSet());
				}
			}
			if (isNew || priceChanged) {
				putItemInTreeSetOfItemsMap(mapByPriceAndItem, item.getPrice(), item, EMPTY_SET);
			}
			if (isNew) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * @param item
	 */
	public void updateSameSameMap(Item item) {
		if ((item.getDescription().length >= 8)) {
			DescriptionKey key = new DescriptionKey(item.getDescription());
			Set<Item> commonItems = mapForSameSame.get(key);
			if (commonItems == null) {
				commonItems = new HashSet<Item>();
			}
			commonItems.add(item);
			mapForSameSame.put(key, commonItems);
		}
	}

	/**
	 * This method returns a price comparator based empty Treeset
	 * 
	 * @return
	 */
	private Set<Item> priceBasedEmptyTreeSet() {
		return new TreeSet<>(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				// System.out.println(o1+":"+o2);
				return (o1.getPrice()).compareTo(o2.getPrice());
			}
		});
	}

	/**
	 * look up by id, returns price
	 * 
	 * @param id
	 * @return - returns price of the item
	 */
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
		long counter = 0;
		Item item = mapById.remove(id);
		for (Long descriptionSubSequence : item.getDescription()) {
			counter += descriptionSubSequence;
		}
		cleanupSameSameMapForItem(item);
		for (long subDescriptionKey : item.getDescription()) {
			removeFromItemBasedTreeset(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item);
		}
		removeFromItemBasedTreeset(mapByPriceAndItem, item.getPrice(), item);
		return counter;
	}

	/**
	 * @param item
	 */
	public void cleanupSameSameMapForItem(Item item) {
		if ((item.getDescription().length >= 8)) {
			DescriptionKey descKey = new DescriptionKey(item.getDescription());
			Set<Item> hashSet = mapForSameSame.remove(descKey);
			if (hashSet != null) {
				hashSet.remove(item);
				if (hashSet.size() != 0) {
					mapForSameSame.put(descKey, hashSet);
				}
			}
		}
	}

	double findMinPrice(long des) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			return set.first().getPrice();
		}
		return Double.MIN_VALUE;
	}

	double findMaxPrice(long des) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {

			// System.out.println(set.last().getPrice());
			return set.last().getPrice();
		}
		return Double.MIN_VALUE;
	}

	int findPriceRange(long des, double lowPrice, double highPrice) {
		Set<Item> set = extractSubSetForDescription(des, lowPrice, highPrice);
		if (set == null) {
			return 0;
		} else {
			return set.size();
		}
	}

	/**
	 * @param des
	 * @param lowPrice
	 * @param highPrice
	 */
	private Set<Item> extractSubSetForDescription(long des, double lowPrice, double highPrice) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			Item lowItem = new Item(-1L, lowPrice, null);
			Item highItem = new Item(-1L, highPrice, null);
			 System.out.println("__"+set+" ::"+set.size());
			Set<Item> subSet = set.subSet(lowItem, true, highItem, true);
			System.out.println("==>"+subSet);
			return subSet;
		}
		return EMPTY_SET;
	}

	/**
	 * hikes price of all elements in the given range of ids.
	 * 
	 * @param minid
	 * @param maxid
	 * @param rate
	 * @return
	 */
	double priceHike(long minid, long maxid, double rate) {
		double netIncrease = 0;
		Map<Long, Item> items = mapById.subMap(minid, true, maxid, true);
		for (Item item : items.values()) {

			/**
			 * remove entry from price index
			 */
			removeFromItemBasedTreeset(mapByPriceAndItem, item.getPrice(), item);

			double newPrice = item.getPrice() * ((double) 1 + ((double) rate / 100));
			newPrice = truncateToTwoDecimalPlaces(newPrice);

			double diff =  (newPrice - item.getPrice());
			item.setPrice(newPrice);

			/**
			 * calculate net difference between new and old price.
			 */
			netIncrease += diff;
			/**
			 * put entry into price index
			 */
			putItemInTreeSetOfItemsMap(mapByPriceAndItem, item.getPrice(), item, EMPTY_SET);

			for (long subDescriptionKey : item.getDescription()) {
				removeFromItemBasedTreeset(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item);
				putItemInTreeSetOfItemsMap(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item,
						priceBasedEmptyTreeSet());
			}
		}
		return truncateToTwoDecimalPlaces(netIncrease);
	}

	/**
	 * This method truncates input number.
	 * 
	 * @param num
	 *            - number to be truncated
	 * @return
	 */
	private double truncateToTwoDecimalPlaces(double num) {
		String str = num + "";
		int dotIndex = str.indexOf('.');
		if (str.length() > dotIndex + 2) {
			return Double.parseDouble(str.substring(0, (dotIndex + 3)));
		} else if (str.length() > dotIndex + 2) {
			return Double.parseDouble(str.substring(0, dotIndex + 2));
		} else {
			return num;
		}
	}

	/**
	 * This method returns number of elements present in the map range selected
	 * 
	 * @param lowPrice
	 * @param highPrice
	 * @return
	 */
	int range(double lowPrice, double highPrice) {
		return mapByPriceAndItem.subMap(lowPrice, true, highPrice, true).values().size();
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
		// System.out.println(mapForSameSame);
		int counter = 0;
		for (Map.Entry<DescriptionKey, Set<Item>> entry : mapForSameSame.entrySet()) {
			int size = entry.getValue().size();
			// System.out.println(size);
			if (size > 1) {
				counter += size;
			}
		}
		// System.out.println("Counter" + counter);
		return counter;
	}

	public static void main(String[] args) {
		Statistics stats = new Statistics();
		stats.timer();

		stats.timer("Time Taken");
	}

	/**
	 * This method iterates through the map and puts the item inside the inner
	 * treeset.
	 * 
	 * @param itemToBePut
	 * @param mapOfTreeSetOfItems
	 * @param outerKey
	 */
	private static <T> void putItemInTreeSetOfItemsMap(Map<T, Set<Item>> mapOfTreeSetOfItems, T outerKey,
			Item itemToBePut, Set<Item> emptyHashSet) {
		Set<Item> treeSet = mapOfTreeSetOfItems.get(outerKey);
		if (treeSet == null) {
			treeSet = emptyHashSet;
		}
		treeSet.add(itemToBePut);
		mapOfTreeSetOfItems.put(outerKey, treeSet);
	}

	/**
	 * This method removes the entry by iner id
	 * 
	 * @param mapOfItemBasedTreeSet
	 * @param innerKeyToBeRemoved
	 * @param outerKey
	 */
	private <T> void removeFromItemBasedTreeset(Map<T, Set<Item>> mapOfItemBasedTreeSet, T outerKey,
			Item innerKeyToBeRemoved) {
		// System.out.println(innerKeyToBeRemoved + "==>" + outerKey + ":=>" +
		// mapOfItemBasedTreeSet);
		Set<Item> set = mapOfItemBasedTreeSet.get(outerKey);
		/*
		 * Item itemTobeDeleted = null; for (Item item2 : set) { if
		 * (item2.getId().longValue() == innerKeyToBeRemoved) { itemTobeDeleted
		 * = item2; } }
		 */
		// if (itemTobeDeleted != null) {
		// System.out.println(set.remove(innerKeyToBeRemoved));
		// }
		mapOfItemBasedTreeSet.put(outerKey, set);
	}

}
