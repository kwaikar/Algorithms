import java.util.Arrays;

/**
 * This class implements Multi-dimensional Search
 * 
 * @author Kavya Krishna Maringanty, Kanchan Waikar, Sruti Paku
 * 
 *         Date Created : Apr 3, 2016 - 7:08:23 PM
 *
 */
public class DescriptionKey {

	/**
	 * Make description Key immutable
	 */
	private final int lengthOfKey;
	private final Long[] key;
	private Integer hashCode = null;

	public DescriptionKey(Long[] key) {
		super();
		this.lengthOfKey = key.length;
		this.key = key;
	}

	/**
	 * @return the lengthOfKey
	 */
	public int getLengthOfKey() {
		return lengthOfKey;
	}

	/**
	 * @return the key
	 */
	public Long[] getKey() {
		return key;
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
			result = prime * result + Arrays.hashCode(key);
			result = prime * result + lengthOfKey;
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
		DescriptionKey other = (DescriptionKey) obj;
		if (!Arrays.equals(key, other.key))
			return false;
		if (lengthOfKey != other.lengthOfKey)
			return false;
		return true;
	}

}
