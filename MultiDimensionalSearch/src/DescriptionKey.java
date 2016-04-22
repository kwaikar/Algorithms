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
	private final Integer lengthOfKey;
	private final Long sumOfKeys;
	private Long[] key;
	private Integer hashCode=null;

	public DescriptionKey(Long[] key) {
		super();
		this.lengthOfKey = key.length;
		this.key = new Long[key.length];
		this.key = key;
		Long sum = 0L;
		for (long singleKey : key) {
			sum += singleKey;
		}
		this.sumOfKeys = sum;
	}

	/**
	 * @return the sumOfKeys
	 */
	public Long getSumOfKeys() {
		return sumOfKeys;
	}

	/**
	 * @return the lengthOfKey
	 */
	public Integer getLengthOfKey() {
		return lengthOfKey;
	}

	/**
	 * @return the key
	 */
	public Long[] getKey() {
		return key;
	}
 
	 
	@Override
	public int hashCode() {
		if(hashCode==null){
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(key);
		hashCode=result;
		return result;
		}
		else
		{
			return hashCode;
		}
		
	}

	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DescriptionKey other = (DescriptionKey) obj;
	
		if (lengthOfKey == null) {
			if (other.lengthOfKey != null)
				return false;
		} else if (!lengthOfKey.equals(other.lengthOfKey))
			return false;
		if (sumOfKeys == null) {
			if (other.sumOfKeys != null)
				return false;
		} else if (!sumOfKeys.equals(other.sumOfKeys))
			return false;
		if (!Arrays.equals(key, other.key))
			return false;
		return true;
	}

	 

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DescriptionKey [key=" + Arrays.toString(key) + "]";
	}
 
}
