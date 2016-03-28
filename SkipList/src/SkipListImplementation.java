import java.util.Random;

/**
 * skip list is a data structure that allows fast search within an ordered
 * sequence of elements. Fast search is made possible by maintaining a linked
 * hierarchy of subsequences, each skipping over fewer elements. Searching
 * starts in the sparsest subsequence until two consecutive elements have been
 * found, one smaller and one larger than or equal to the element searched for.
 * Via the linked hierarchy, these two elements link to elements of the next
 * sparsest subsequence, where searching is continued until finally we are
 * searching in the full sequence. The elements that are skipped over may be
 * chosen probabilistically [2] or deterministically,[3] with the former being
 * more common.
 * 
 * The class implements the following operations. 1. Add a node to a skip list
 * 2. Remove node from the Skip List.
 * 
 * 
 * @author M Krishna Kavya
 * 
 */
public class SkipListImplementation {
	/**
	 * The size is the maximum number of nodes in the skip list. Max Level is
	 * the maximum number of pointers that a node can have . The head, and dummy
	 * tail is initialized
	 */
	public int size, maxLevel;
	Node head, tail;

	SkipListImplementation(int size) {
		this.size = size;
		this.maxLevel = (int) Math.log(size);
		this.head = new Node(0, maxLevel);
		this.tail = new Node(Integer.MAX_VALUE, maxLevel);
		for (int i = maxLevel - 1; i >= 0; i--) {
			head.next[i] = tail;
		}
	}

	/**
	 * Each skip list entry has an array of next pointers, where next[i] points
	 * to an element that is roughly 2i nodes away from it. The next array at
	 * each entry has random size between 1 and maxLevel, the maximum number of
	 * levels in the current skip list.
	 * 
	 * The max levels is determined in the SkipLevelImplementation class.
	 * 
	 * @author M Krishna Kavya
	 * 
	 */
	public class Node {
		public int value;
		public Node[] next;

		Node(int value, int level) {
			this.value = value;
			this.next = new Node[level];
		}
	}

	/**
	 * 
	 * The choice method generates the random number between 1 and max level.
	 * 
	 * @param maxLevel
	 * @return
	 */
	public int choice(int maxLevel) {
		int levelCount = 0;
		boolean b = false;
		Random random = new Random();
		for (int i = maxLevel - 1; i >= 0; i--) {
			b = random.nextBoolean();
			if (b) {
				levelCount++;
			}
		}
		int j = (levelCount == 0) ? 1 : levelCount;
		return j;
	}

	/**
	 * The find method returns an array of nodes(prev[0..maxLevel]) at which
	 * search went down one level, looking for x.
	 * 
	 * @param x
	 * @return
	 */
	public Node[] find(int x) {
		Node p = head;
		Node prev[] = new Node[maxLevel];
		for (int i = maxLevel - 1; i >= 0; i--) {
			while (p.next[i].value < x) {
				p = p.next[i];
			}
			prev[i] = p;
		}
		return prev;
	}

	/**
	 * method to add element to the skip List. The find method return prev where
	 * we can add the next element in the next position in the skip list. The
	 * prev<newElement<=prev.next.
	 * 
	 * case 1: The element already existing if (prev.next)is equal to the new
	 * input. In this case next node value is replaced with the new input. case
	 * 2: In the other case . we insert a new Node. Step 1: generate the number
	 * of levels in the new node using the choice method. Step 2: assign prev
	 * pointers to the new node and the new node to the consecutive once.
	 * 
	 * @param x
	 */
	public void add(int x) {

		Node[] prev = find(x);
		if (prev[0].next[0].value == x) {
			prev[0].next[0].value = x;
		}
		int lev = choice(maxLevel);
		Node n = new Node(x, lev);
		for (int i = 0; i < lev; i++) {
			n.next[i] = prev[i].next[i];
			prev[i].next[i] = n;
		}

	}

	/**
	 * The remove method is used to remove the element from Skip List. The Find
	 * method returns the prev[] where the next element is the element which we
	 * want to delete. once the element is found. set the previous pointers to
	 * the next pointers of the node we wish to delete.
	 * 
	 * @param x
	 * @return
	 */
	public Node remove(int x) {
		Node[] prev = find(x);
		Node n = prev[0].next[0];
		if (!(n.value == x)) {
			return null;
		} else {
			for (int i = 0; i < maxLevel - 1; i++) {
				if (prev[i].next[i] == n) {
					prev[i].next[i] = n.next[i];
				} else {
					break;
				}
			}
			return n;
		}
	}

	/**
	 * Method to test the skip list has an element.
	 * 
	 * @param x
	 * @return
	 */
	public boolean contains(int x) {
		Node[] prev = find(x);
		boolean b = (prev[0].next[0].value == x) ? true : false;
		return b;
	}

	public static void main(String[] args) {
		int size = 1000, del = 100;
		SkipListImplementation obj = new SkipListImplementation(size);

		/*
		 * For the analysis of skip list The add and remove operations are
		 * performed and time and memory occupied is assessed.
		 * 
		 * 1. The number of Nodes added is equal to the size of the skip list (
		 * initialised). 2. The number of nodes deleted are 5; The values can be
		 * changed for analysis.
		 */
		System.out.println("Time taken and memory occupied for inserting "
				+ size + " nodes into a Skip List");
		Timer.timer();
		for (int i = 1; i < size; i++) {
			obj.add(i);
		}
		Timer.timer();
		Timer.timer();
		System.out.println("Time taken and memory occupied for removing "
				+ del + " nodes is ");
		for (int i = 1; i < del; i++) {
			obj.remove(i);
		}
		Timer.timer();
	}

}
