import java.util.Arrays;
import java.util.Collections;
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
	private Double price;
	private Long[] sortedDescription;

	public Item(long id, Double price, Long[] sortedDescription) {
		super();
		this.id = id;
		this.price = price;
		this.sortedDescription = sortedDescription;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	public void setDescription(Long[] description) {
		  Collections.sort(Arrays.asList(description));
		  
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
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
