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
					/**
					 * Detailed comparator that compares complete key only when
					 * length and sum of keys are same - this is crucial for
					 * samesame method performance.
					 */
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
	TreeMap<Long, Set<Item>> mapByPriceAndItem = new TreeMap<Long, Set<Item>>();

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
	int insert(Long id, Double priceDouble, long[] desc, int size) {

		/**
		 * Invariants: descChanged - flag suggesting change of description
		 * priceChanged - flag suggesting change of price description -
		 * processed input array in Wrapper format price - processed input that
		 * is in cents instead of dollars. isNew - flag for new Item instead of
		 * item update.
		 */
		boolean descChanged = false;
		boolean priceChanged = false;
		Long[] description = new Long[desc.length];
		for (int i = 0; i < desc.length; i++) {
			description[i] = desc[i];
		}
		long price = 0;
		price = convertToCents(priceDouble);

		if (id != null) {
			Item item = mapById.get(id);
			boolean isNew = false;
			if (item == null) {

				item = new Item(id, price, description);
				isNew = true;
				mapById.put(item.getId(), item);
			} else {
				/**
				 * Since item already exists, we need to clean it up from all
				 * maps.
				 */
				if (description.length != 0 && !Arrays.deepEquals(description, item.getDescription())) {
					descChanged = true;
					cleanupSameSameMapForItem(item);
				}
				if (item.getPrice() != price) {
					priceChanged = true;
					removeFromItemBasedTreeset(mapByPriceAndItem, item.getPrice(), item);
				}
				if (descChanged || priceChanged) {
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
			 * Make new insertions corresponding to new as well as modified
			 * state
			 */
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
			return isNew ? 1 : 0;

		} else {
			return 0;
		}
	}

	/**
	 * Accepts a double representation of currency and converts it into cents
	 * based value.
	 * 
	 * @param priceDouble
	 * @return
	 */
	public static long convertToCents(double priceDouble) {

		String[] priceString = ((priceDouble == (long) priceDouble) ? String.format("%d", (long) priceDouble)
				: String.format("%s", priceDouble)).toString().split("\\.");
		return Long.parseLong(priceString[0]) * 100 + (priceString.length > 1 ? ((priceString[1].length() == 1
				? Integer.parseInt(priceString[1]) * 10 : Integer.parseInt(priceString[1]))) : 0);
	}

	/**
	 * updates mapForSameSame with given item.
	 * 
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
	 * @return - empty TreeSet with comparator that sorts data based on price
	 *         and then on id.
	 */
	private Set<Item> priceBasedEmptyTreeSet() {
		return new TreeSet<>(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				int temp = (o1.getPrice()).compareTo(o2.getPrice());
				return (temp == 0) ? o1.getId().compareTo(o2.getId()) : temp;
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
		return item == null ? 0 : ((double) item.getPrice() / 100);
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
	 * Cleans up item from mapForSameSame
	 * 
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

	/**
	 * Returns price of minimum item containing des substring
	 * 
	 * @param des
	 *            - description subString to be used for fetching.
	 * @return - minimum price of the element found.
	 */
	double findMinPrice(long des) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			return (double) set.first().getPrice() / 100;
		}
		return 0;
	}

	/**
	 * Returns max price of item containing des substring
	 * 
	 * @param des
	 *            - description subString to be used for fetching.
	 * @return - max price found in dollars
	 */
	double findMaxPrice(long des) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			return (double) set.last().getPrice() / 100;
		}
		return 0;
	}

	/**
	 * Returns price within the range and only includes entries with des
	 * substring in description
	 * 
	 * @param des
	 * @param lowPrice
	 * @param highPrice
	 * @return
	 */
	int findPriceRange(long des, double lowPrice, double highPrice) {
		 
		Set<Item> set = extractSubSetForDescription(des, convertToCents(lowPrice), convertToCents(highPrice));
		if (set == null) {
			return 0;
		} else {
			return set.size();
		}
	}

	/**
	 * This method returns set of items that fall in the range of lowPrice and
	 * highPrice and have des value in their description
	 * 
	 * @param des
	 * @param lowPrice
	 * @param highPrice
	 */
	private Set<Item> extractSubSetForDescription(long des, long lowPrice, long highPrice) {
		TreeSet<Item> set = (TreeSet<Item>) mapOfDescriptionSubStringAndPrice.get(des);
		if (set != null && set.size() != 0) {
			Item lowItem = new Item(1L, lowPrice, null);
			Item highItem = new Item(1L, highPrice+1, null);
			/**
			 * True flag needs to be sent to subset for inclusivity.
			 */
			return set.subSet(lowItem, false, highItem, false);
		}
		return EMPTY_SET;
	}

	/**
	 * hikes price of all elements in the given range of ids.
	 * 
	 * @param minid
	 *            - start id of the range
	 * @param maxid
	 *            - end id of the range
	 * @param rate
	 *            - percentage rate to be hiked on all elements whose ids fall
	 *            in the given range.
	 * @return
	 */
	double priceHike(long minid, long maxid, double rate) {
		long netIncrease = 0L;

		for (Item item : mapById.subMap(minid, true, maxid, true).values()) {

			/**
			 * remove entry from price index and description based index.
			 */
			removeFromItemBasedTreeset(mapByPriceAndItem, item.getPrice(), item);
			for (long subDescriptionKey : item.getDescription()) {
				removeFromItemBasedTreeset(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item);
			}

			/**
			 * netIncrease - total hike oldPrice - existing price of the item
			 * percentage - rate converted into percentage for calculation incr
			 * - total increment in the value item - pointer to current item to
			 * be iterated on.
			 */
			/**
			 * Calculate new price and increment compounding netIncrease
			 * variable.
			 */
			long oldPrice = item.getPrice();
			double percentage = rate / 100;
			long incr = (long) (oldPrice * percentage);
			item.setPrice(oldPrice + incr);

			/**
			 * calculate net difference between new and old price.
			 */
			netIncrease += incr;
			/**
			 * put entry into price index
			 */

			for (long subDescriptionKey : item.getDescription()) {
				putItemInTreeSetOfItemsMap(mapOfDescriptionSubStringAndPrice, subDescriptionKey, item,
						priceBasedEmptyTreeSet());
			}
			putItemInTreeSetOfItemsMap(mapByPriceAndItem, item.getPrice(), item, EMPTY_SET);
		}
		return ((double) netIncrease / 100);
	}

	/**
	 * This method returns number of elements present in the map range selected
	 * 
	 * @param lowPrice
	 * @param highPrice
	 * @return
	 */
	int range(double lowPrice, double highPrice) {
		/**
		 * true flag for inclusivity. Multiply by hundred since maps store
		 * values in cents and not in dollars.
		 */
		return mapByPriceAndItem.subMap(convertToCents(lowPrice), false, convertToCents(highPrice), false).values()
				.size();
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
		/**
		 * counter for counting number of entries that follow the criteria.
		 */
		int counter = 0;
		for (Set<Item> value : mapForSameSame.values()) {
			int size = value.size();
			if (size > 1) {
				counter += size;
			}
		}
		return counter;
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
		/**
		 * Invariant: treeSet - reference to map's inner TreeSet.
		 */
		Set<Item> treeSet = mapOfTreeSetOfItems.get(outerKey);
		if (treeSet == null) {
			treeSet = emptyHashSet;
			mapOfTreeSetOfItems.put(outerKey, treeSet);
		}
		treeSet.add(itemToBePut);
	}

	/**
	 * This method removes the entry by inner id
	 * 
	 * @param mapOfItemBasedTreeSet
	 * @param innerKeyToBeRemoved
	 * @param outerKey
	 */
	private <T> void removeFromItemBasedTreeset(Map<T, Set<Item>> mapOfItemBasedTreeSet, T outerKey,
			Item innerKeyToBeRemoved) {
		mapOfItemBasedTreeSet.get(outerKey).remove(innerKeyToBeRemoved);
	}

}
