/*
 * Implemented BinaryHeap - MaxBinaryHeap
 * ITs left child is 2*i
 * right child is 2*i+1
 * Parent is i/2
 * 
 * implemented maxHeapify method to percolate up the node
 * In maxheap the parent node is larger than the child nodes.
 */

public class MaxBinaryHeap
{
    private int[] maxHeap;
    private int size;
    private int maxsize;
 
    private static final int FRONT = 1;
 
    public  MaxBinaryHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        maxHeap = new int[this.maxsize + 1];
        maxHeap[0] = Integer.MAX_VALUE;
    }
 
    //Parent of the node
    private int parent(int pos)
    {
        return pos / 2;
    }
 //Leftchild index 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
//Right child index 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 //Checking if the node is leaf or not
    private boolean isLeaf(int pos)
    {
        if (pos >=  (size / 2)  &&  pos <= size)
        {
            return true;
        }
        return false;
    }
 //Swapping function
    private void swap(int fpos,int spos)
    {
        int tmp;
        tmp = maxHeap[fpos];
        maxHeap[fpos] = maxHeap[spos];
        maxHeap[spos] = tmp;
    }
 
    private void maxHeapify(int pos)
    {
        if (!isLeaf(pos))
        { 
            if ( maxHeap[pos] < maxHeap[leftChild(pos)]  || maxHeap[pos] < maxHeap[rightChild(pos)])
            {
                if (maxHeap[leftChild(pos)] > maxHeap[rightChild(pos)])
                {
                    swap(pos, leftChild(pos));
                    maxHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    maxHeapify(rightChild(pos));
                }
            }
        }
    }
 //Inserting Element
    public void insert(int element)
    {
        maxHeap[++size] = element;
        int current = size;
 
        while(maxHeap[current] > maxHeap[parent(current)])
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
  
    public void print()
    {
        for (int i = 1; i <= size / 2; i++ )
        {
            System.out.print(" PARENT : " + maxHeap[i] + " LEFT CHILD : " + maxHeap[2*i]
                  + " RIGHT CHILD :" + maxHeap[2 * i  + 1]);
            System.out.println();
        }
    }
 
    public void maxHeap()
    {
        for (int pos = (size / 2); pos >= 1; pos--)
        {
            maxHeapify(pos);
        }
    }
 
    public int delete()
    {
        int popped = maxHeap[FRONT];
        maxHeap[FRONT] = maxHeap[size--]; 
        maxHeapify(FRONT);
        return popped;
    }
 
 

    	public static void main(String[] args) {
    		MaxBinaryHeap obj = new MaxBinaryHeap(1000000);
    		Timer time = new Timer();
       		time.timer();
       		for(int j=0;j<1000000;j++){
				 obj.insert((int) ((Math.random()+2)*10));
				 }
       		System.out.println("Inserted 1000000 nodes");
       		System.out.println("The deleted root:"+obj.delete());
    		
    		time.timer();
    	}

    
}
