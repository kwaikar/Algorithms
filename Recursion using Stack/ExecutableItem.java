/**
 * 
 */

/**
 * @author kanchan
 *
 */
public class ExecutableItem {

	int left;
	int mid;
	int right;
	boolean merge;
	/**
	 * field based constructor
	 * @param p
	 * @param mid
	 * @param r
	 */
	public ExecutableItem(int p, int r, boolean merge ) {
		super();
		this.left = p;
		this.right = r;
		this.mid=((p+r)/2);
		this.merge=merge;
	}
	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @return the mid
	 */
	public int getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(int mid) {
		this.mid = mid;
	}
	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @return the merge
	 */
	public boolean isMerge() {
		return merge;
	}
	/**
	 * @param merge the merge to set
	 */
	public void setMerge(boolean merge) {
		this.merge = merge;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExecutableItem [left=" + left + ", mid=" + mid + ", right=" + right + ", merge=" + merge + "]";
	} 
	
	
}
