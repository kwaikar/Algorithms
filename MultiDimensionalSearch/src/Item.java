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
	private Long id;
	/**
	 * Converted into cents before string
	 */
	private Long price;
	/**
	 * Description is sorted before storing
	 */
	private Long[] sortedDescription = new Long[0];
	private Integer hashCode = null;

	public Item(Long id, Long price, Long[] sortedDescription) {
		super();
		this.id = id;
		this.price = price;
		this.setDescription(sortedDescription);
		this.hashCode();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the price
	 */
	public Long getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Long price) {
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
	public void setDescription(Long[] description) {
		if (description != null) {
			List<Long> temp = new ArrayList<Long>();
			for (Long Long1 : description) {
				if (Long1 != null) {
					temp.add(Long1);
				}
			}
			Arrays.sort(description);
			sortedDescription = description;
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", price=" + price + ", sortedDescription=" + Arrays.toString(sortedDescription)
				+ ", hashCode=" + hashCode + "]";
	}

}
