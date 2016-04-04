
/**
 * This is a pojo for Item class.
 * 
 * @author Kanchan Waikar Date Created : Apr 3, 2016 - 10:27:15 PM
 *
 */
public class Item {
	private long id;
	private double price;
	private long[] description;

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
	public double getPrice() {
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
	public long[] getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(long[] description) {
		this.description = description;
	}

}
