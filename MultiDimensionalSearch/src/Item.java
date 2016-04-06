import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This is a pojo for Item class.
 * 
 * @author Kavya Krishna Maringanty, Kanchan Waikar, Sruti Paku
 * 
 *         Date Created : Apr 3, 2016 - 10:27:15 PM
 *
 */
public class Item {
	private long id;
	private double price;
	private Long[] sortedDescription=new Long[0];
	private Integer hashCode = null;

	public Item(long id, Double price, long[] sortedDescription) {
		super();
		this.id = id;
		this.price = price;
		this.setDescription(sortedDescription);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the description
	 */
	public Long[] getDescription() {
		return sortedDescription;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(long[] description) {
		List<Long> temp = new ArrayList<Long>();
		for (Long long1 : description) {
			if (long1 != null) {
				temp.add(long1);
			}
		}
		Collections.sort(temp);
		System.out.println("seting" + temp);
		sortedDescription = temp.toArray(sortedDescription);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
			hashCode = result;
			return result;
		} else {
			return hashCode;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
